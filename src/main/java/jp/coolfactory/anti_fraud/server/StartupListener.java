package jp.coolfactory.anti_fraud.server;

import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by wangqi on 2/12/2016.
 */
public class StartupListener implements ServletContextListener {

    public static final String VERSION = "master.19.e2f39cb";
    public static final String BUILD_DATE = "2017-01-31 22:50:00:000 CST";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String log4jFile = sce.getServletContext().getInitParameter("log4jConfiguration");
        DOMConfigurator.configure(sce.getServletContext().getRealPath(log4jFile));
        System.out.println("================================================================================");
        System.out.println("    AntiFraud Server ("+VERSION+") builds at: " + BUILD_DATE);
        System.out.println("================================================================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
