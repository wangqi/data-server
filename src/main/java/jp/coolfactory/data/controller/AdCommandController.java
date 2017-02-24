package jp.coolfactory.data.controller;

import jp.coolfactory.data.common.AdRequestDBCommand;
import jp.coolfactory.data.common.Chain;
import jp.coolfactory.data.common.ChainBuilder;
import jp.coolfactory.data.module.AdRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangqi on 23/2/2017.
 */
public class AdCommandController implements Controller{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdCommandController.class);

    private static AdCommandController instance = new AdCommandController();

    private Chain<AdRequest> chain = null;

    /**
     * Initialize the AdParamMap here.
     */
    @Override
    public void init() {
        ChainBuilder builder = ChainBuilder.chainBuilder();
        AdRequestDBCommand command = new AdRequestDBCommand();
        command.runWorker();
        chain = builder.first(command).build();
    }

    /**
     * Reload the setting from database if necessary.
     */
    @Override
    public void reload() {

    }

    public static AdCommandController getInstance() {
        return instance;
    }

    /**
     * Execute the chain of responsibility here.
     * @param req
     */
    public void handle(AdRequest req) {
        chain.handle(req);
    }
}
