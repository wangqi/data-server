package jp.coolfactory.data.controller;

/**
 * Created by wangqi on 22/2/2017.
 */
public interface Controller {

    /**
     * Initialize the AdParamMap here.
     */
    public void init();

    /**
     * Reload the setting from database if necessary.
     */
    public void reload();

    /**
     * Clean the resources hold and shutdown the controller.
     */
    default public void shutdown() {
    }

}
