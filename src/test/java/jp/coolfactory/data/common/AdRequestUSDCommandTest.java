package jp.coolfactory.data.common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wangqi on 1/3/2017.
 */
public class AdRequestUSDCommandTest {

    @Test
    public void testWorker() throws Exception {
        AdRequestUSDCommand command = new AdRequestUSDCommand();
        command.runWorker();
        Thread.sleep(50000);
    }

}