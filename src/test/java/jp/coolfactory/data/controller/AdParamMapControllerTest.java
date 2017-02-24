package jp.coolfactory.data.controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 22/2/2017.
 */
public class AdParamMapControllerTest {

    @Before
    public void setUp() {
        AdParamMapController.getInstance().init();
    }

    /**
     * The standard param name 'install_time' is maps to 'install_time' for default and
     * 'created' for 'test' source. Let's test it here.
     *
     * @throws Exception
     */
    @Test
    public void translateStdName() throws Exception {
        String source = "test";
        String stdParamName = "install_time";
        AdParamMapController controller = AdParamMapController.getInstance();
        String param_name = controller.translateStdName(stdParamName);
        assertEquals("Default source", "install_time", param_name);
        param_name = controller.translateStdName(source, stdParamName);
        assertEquals("test source", "created", param_name);
    }

}