package jp.coolfactory.data.common;

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * For 'purchase' event, turn the local revenue into revenue_usd by latest currency ratio.
 *
 * API: http://api.fixer.io/latest?base=USD&symbols=CNY
 *      {"base":"USD","date":"2017-02-28","rates":{"CNY":6.868}}
 *
 * Get All: http://api.fixer.io/latest?base=USD
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestUSDCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestUSDCommand.class);

    //Refresh the foreign currency rate per day.
    private static final int DEFAULT_WAIT_SECOND = 86400;

    //The cache that contains foreign currency rate. It's based on USD
    private Map<String, Double> currencyMap = new HashMap<>();

    private static final String SQL = "replace into ad_currency (created, currency_code, rate) values (?, ?, ?)";

    private static final String API_URL = "http://api.fixer.io/latest?base=USD";

    public AdRequestUSDCommand() {
        currencyMap.put("cny", 7.0);
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            if ( adRequest != null ) {
                String currency_code = adRequest.getCurrency_code();
                Double rateDouble = currencyMap.get(currency_code);
                if ( rateDouble != null ) {
                    double rate = rateDouble.doubleValue();
                    double revenue = adRequest.getRevenue();
                    if (Constants.SOURCE_TalkingData.equals(adRequest.getSource())) {
                        revenue = revenue / 100;
                        adRequest.setRevenue(revenue);
                    }
                    adRequest.setRevenue_usd(revenue/rate);
                } else {
                    LOGGER.warn("Currency Code: " + currency_code + " is not found");
                }

            } else {
                LOGGER.warn("adRequest is null.");
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to process stat_id", e);
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

    /**
     * Start the worker and run forever
     */
    public final void runWorker() {
        LOGGER.info("Currency ScheduleWorker is activated.");
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Resty r = new Resty();
                    JSONResource jsonRes = r.json(API_URL);
                    String date = (String)jsonRes.get("date");
                    JSONObject rates = (JSONObject)jsonRes.get("rates");
                    Iterator<String> keys = rates.keys();
                    Map<String, String> maps = new HashMap<>();
                    while ( keys.hasNext() ) {
                        String key = keys.next();
                        String value = rates.get(key).toString();
                        maps.put(key, value);
                    }
                    System.out.println(maps);
                } catch (Exception e) {
                    LOGGER.warn("DBCommand worker runs into an error", e);
                }
            }
        }, 0, DEFAULT_WAIT_SECOND, TimeUnit.SECONDS);
    }

    /**
     * Add the parameters into PreparedStatement as batch.
     */
    public static String insert(String sql) {
        try {
            return sql;
        } catch (Exception e) {
            LOGGER.warn("Failed to generate SQL", e);
        }
        return null;
    }
}
