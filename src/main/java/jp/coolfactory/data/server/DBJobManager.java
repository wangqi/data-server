package jp.coolfactory.data.server; /**
 * Created by wangqi on 6/3/2017.
 */

import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.common.AdRequestDBCommand;
import jp.coolfactory.data.common.CommandStatus;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.module.SQLRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.ArrayList;
import java.util.concurrent.*;

@WebListener()
public class DBJobManager implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBJobManager.class);
    private static final int DEFAULT_DRAIN_SIZE = 10;
    private static final int DEFAULT_WAIT_SECOND = 5;
    private BlockingQueue<SQLRequest> queue = new LinkedBlockingQueue<>();
    private ExecutorService service;

    // Public constructor is required by servlet spec
    public DBJobManager() {
    }

    /**
     * Add the parameters into PreparedStatement as batch.
     *
     * @param req
     */
    public static String insert(SQLRequest req) {
        try {
            return req.toSQL();
        } catch (Exception e) {
            LOGGER.warn("Failed to generate SQL", e);
        }
        return null;
    }

    /**
     * Put a request into queue.
     *
     * @param req
     */
    public void submitRequest(SQLRequest req) {
        try {
            if ( req != null )
                queue.put(req);
        } catch (InterruptedException e) {
            LOGGER.warn("submitRequest is interrupted.");
        }
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        service = Executors.newCachedThreadPool();
        LOGGER.info("Worker is activated.");
        service.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (service.isShutdown()) {
                            LOGGER.info("DBJobManager is shutdown.");
                            break;
                        } else {
                            ArrayList<SQLRequest> list = new ArrayList<>();
                            //If no objects avaiable, then wait
                            list.add(queue.take());
                            queue.drainTo(list, DEFAULT_DRAIN_SIZE);
                            DBUtil.sqlBatch(DBJobManager::<SQLRequest>insert, list);
                            //TODO Add CouldWatch here if necessary.
                            LOGGER.info("Save " + list.size() + " requests into database. ");
                        }
                    } catch (Exception e) {
                        LOGGER.warn("DBCommand worker runs into an error", e);
                    }
                }
            }
        });
        Version.CONTEXT.put(Constants.DB_JOB_MANAGER, this);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        service.shutdownNow();
    }

}
