package jp.coolfactory.data.common;

import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.util.ConfigUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 12/4/2017.
 */
public class AdRequestIPCommandTest {

    @Before
    public void setUp() throws Exception {
        System.setProperty("mode", "test");
        ConfigUtil.initGlobalConfig();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void handle() throws Exception {
        AdRequest req = AdRequestCreator.createdRequest();
        req.setInstall_ip("49.96.23.163");
        AdRequestIPCommand command = new AdRequestIPCommand();
        command.handle(req);
        assertEquals("Kushima", req.getCity_code());
    }

}