package jp.coolfactory.anti_fraud.controller;

import jp.coolfactory.anti_fraud.command.*;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.controller.AdCommandController;
import jp.coolfactory.data.controller.AdParamMapController;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.DateUtil;
import jp.coolfactory.data.util.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Please make sure the 'test' mode is linked to product server database.
 *
 *  Use the following SQL to check the result
     select site_id, site_name, status, count(*) n
     from app_data.ad_install i, ip.ip2location l
     where created>="2017-05-18" and created<"2017-05-24"
     and i.ip_from = l.ip_from
     and publisher_name = 'Appier'
     and site_name = '戦艦帝国-android-jp'
     group by 1, 2, 3;
 *
 *
 */
public class AntiFraudSimulateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AntiFraudSimulateTest.class);

    //    private Chain<AdRequest> chain = null;
    private static LinkedList<Handler> commandChain= new LinkedList<>();

    static {
        System.setProperty("mode", "test");

        //AntiFraud
        AfMATCommand afMatCommand = new AfMATCommand();
        AfClickInstallIntervalCommand afClickInstallIntervalCommand = new AfClickInstallIntervalCommand();
        AfIPFilterCommand afIPFilterCommand = new AfIPFilterCommand();
        AfCampaignCommand afCampaignCommand = new AfCampaignCommand();
        AfPostbackCommand afPostbackCommand = new AfPostbackCommand();
        AfIPSegmentCommand afIPSegmentCommand = new AfIPSegmentCommand();

        commandChain.add(afMatCommand);
        commandChain.add(afCampaignCommand);
        commandChain.add(afIPFilterCommand);
        commandChain.add(afClickInstallIntervalCommand);
        commandChain.add(afIPSegmentCommand);
        commandChain.add(afPostbackCommand);
    }

    /**
     * Execute the chain of responsibility here.
     * @param req
     */
    public AdRequest handle(AdRequest req) {
        for ( Handler handler : commandChain ) {
            CommandStatus status = handler.handle(req);
            if ( status == CommandStatus.End ) {
                LOGGER.debug("Command end the chain: " + handler.toString());
                break;
            } else if ( status == CommandStatus.Fail ) {
                LOGGER.debug("Command failed the chain: " + handler.toString());
                break;
            }
        }
        return req;
    }

    @Before
    public void setUp() throws Exception {
        AntiFraudController.getInstance().init();
    }

    @Test
    public void evaluateInstall() throws Exception {
        {
            System.out.println("--------- Appier -----------");
            List<AdRequest> records = readRecordsFromCSV("/appier_20170518-0523.csv", "\t");
            Map groupByStatus  = records.stream()
                    .map(record -> handle(record))
                    .collect(
                            Collectors.groupingBy(AdRequest::getStatus, Collectors.counting())
                    );

            System.out.println(groupByStatus);
        }
    }

    private List<AdRequest> readRecordsFromCSV(String fileName, String separator) {
        HashMap<String, Integer> headerMap = new HashMap<>();
        InputStream is = this.getClass().getResourceAsStream(fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String headerLine = br.readLine();
            if ( headerLine == null ) {
                System.out.println("CSV file is empty");
                return null;
            }
            String[] headers = headerLine.split(separator);
            for ( int i=0; i<headers.length; i++ ) {
                headerMap.put(headers[i], Integer.valueOf(i));
            }
//            System.out.println(headerMap);
            List<AdRequest> list = br.lines().map( (line) -> {
                String[] values = line.split(separator);
                AdRequest request = new AdRequest();
                request.setSite_id(getField("site_id", headerMap, values));
                request.setAf_site_id(getField("site_id", headerMap, values));
                request.setSite_name(getField("site_name", headerMap, values));
                request.setAction("install");
                request.setSource("mat");
                request.setAppKey("battleship-test");
                request.setStatus("approved");
                request.setInstall_time(getDateTimeParam(getField("install_date", headerMap, values)));
                request.setClick_time(getDateTimeParam(getField("click_date", headerMap, values)));
                request.setOs_version(getField("os_version", headerMap, values));
                request.setDevice_brand(getField("device_brand", headerMap, values));
                request.setDevice_carrier(getField("device_carrier", headerMap, values));
                request.setDevice_model(getField("device_model", headerMap, values));
                request.setLang(getField("lang", headerMap, values));
                request.setInstall_ip(getField("install_ip", headerMap, values));
                request.setClick_ip(getField("click_ip", headerMap, values));
                request.setIos_ifa(getField("ios_ifa", headerMap, values));
                request.setIos_ifv(getField("ios_ifv", headerMap, values));
                request.setGoogle_aid(getField("google_aid", headerMap, values));
                request.setPublisher_id(getField("publisher_id", headerMap, values));
                request.setPublisher_name(getField("publisher_name", headerMap, values));
                request.setRegion_name(getField("region", headerMap, values));
                request.setOs_jailbroke(getFieldAsBool("os_jailbroke", headerMap, values));
                request.setCountry_code(getField("country_code", headerMap, values));

                return request;

            }).collect(Collectors.toList());

            return list;
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    private String getField(String name, HashMap<String, Integer> headerMap, String[] row) {
        return row[headerMap.get(name)];
    }

    private ZonedDateTime getDateTimeParam(String value) {
        return DateUtil.convertIOS2Date(value, "Asia/Tokyo");
    }

    private boolean getFieldAsBool(String name, HashMap<String, Integer> headerMap, String[] row)  {
        boolean value = false;
        String temp = row[headerMap.get(name)];
        try {
            if ( StringUtil.isNotEmptyString(temp) ) {
                temp = temp.toLowerCase();
            }
            if ("1".equalsIgnoreCase(temp) || "yes".equalsIgnoreCase(temp) ||
                    "true".equalsIgnoreCase(temp) || "on".equalsIgnoreCase(temp))
                value = true;
        } catch (Exception e) {
            value = false;
        }
        return value;
    }
}