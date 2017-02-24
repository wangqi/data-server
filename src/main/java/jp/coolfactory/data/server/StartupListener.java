package jp.coolfactory.data.server;

import jp.coolfactory.data.Version;
import jp.coolfactory.data.controller.AdAppController;
import jp.coolfactory.data.controller.AdCommandController;
import jp.coolfactory.data.controller.AdParamMapController;
import jp.coolfactory.data.db.DBUtil;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by wangqi on 2/12/2016.
 */
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String log4jFile = sce.getServletContext().getInitParameter("log4jConfiguration");
        DOMConfigurator.configure(sce.getServletContext().getRealPath(log4jFile));
        System.out.println("================================================================================");
        System.out.println("    Data Server ("+ Version.VERSION+") builds at: " + Version.BUILD_DATE);
        System.out.println("================================================================================");

        //Initialize the controller here.
        AdParamMapController.getInstance().init();
        System.out.println("AdParamMapController initialize.");
        AdAppController.getInstance().init();
        System.out.println("AdAppControler initialize.");
        AdCommandController.getInstance().init();
        System.out.println("AdCommandController initialize.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
