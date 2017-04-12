package jp.coolfactory.data.controller;

import jp.coolfactory.anti_fraud.command.*;
import jp.coolfactory.anti_fraud.controller.AntiFraudController;
import jp.coolfactory.anti_fraud.module.Status;
import jp.coolfactory.data.common.AdRequestCreator;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.common.Handler;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.DateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 11/4/2017.
 */
public class AntiFraudChainTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AntiFraudChainTest.class);

    private static AdCommandController instance = new AdCommandController();

    private LinkedList<Handler> commandChain= new LinkedList<>();

    public AntiFraudChainTest() {
        System.setProperty("mode", "test");
        AntiFraudController.getInstance().init();
    }

    @Before
    public void setUp() throws Exception {
        //AntiFraud
        AfMATCommand afMatCommand = new AfMATCommand();
        AfClickInstallIntervalCommand afClickInstallIntervalCommand = new AfClickInstallIntervalCommand();
        AfIPFilterCommand afIPFilterCommand = new AfIPFilterCommand();
        AfCampaignCommand afCampaignCommand = new AfCampaignCommand();
        AfPostbackCommand afPostbackCommand = new AfPostbackCommand();

        commandChain.add(afMatCommand);
        commandChain.add(afClickInstallIntervalCommand);
        commandChain.add(afIPFilterCommand);
        commandChain.add(afCampaignCommand);
        commandChain.add(afPostbackCommand);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAFLang() throws Exception {
        AdRequest req = AdRequestCreator.createdRequest();
        req.setLang("en");
        req = processAntiFraud(req);
        assertEquals("Language is forbidden", req.getStatus());
        assertEquals(String.valueOf(Status.FORBIDDEN_LANG.getStatus()), req.getStatus_code());
    }

    public AdRequest processAntiFraud(AdRequest req) throws Exception {
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

}