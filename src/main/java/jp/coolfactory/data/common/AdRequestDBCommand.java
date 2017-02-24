package jp.coolfactory.data.common;

import com.google.common.collect.Queues;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Save the AdRequest to database.
 *
 * Created by wangqi on 22/2/2017.
 */
public class AdRequestDBCommand implements Handler<AdRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdRequestDBCommand.class);

    private static final int DEFAULT_DRAIN_SIZE = 10;
    private static final int DEFAULT_WAIT_SECOND = 5;

    private static final String SQL = "";

    private BlockingQueue<AdRequest> queue = new LinkedBlockingQueue<>();

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            queue.put(adRequest);
        } catch (InterruptedException e) {
            LOGGER.warn("handle is interrupted.");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

    /**
     * Start the worker and run forever
     */
    public final void runWorker() {
        LOGGER.info("ScheduleWorker is activated.");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<AdRequest> list = new ArrayList<>();
                    //If no objects avaiable, then wait
                    list.add(queue.take());
                    queue.drainTo(list, DEFAULT_DRAIN_SIZE);
                    DBUtil.insertBatch(SQL, AdRequestDBCommand::<AdRequest>insert, list);
                    //TODO Add CouldWatch here if necessary.
                    LOGGER.info("Save " + list.size() + " requests into database. ");
                } catch (Exception e) {
                    LOGGER.warn("DBCommand worker runs into an error", e);
                }
            }
        }, 0, DEFAULT_WAIT_SECOND, TimeUnit.SECONDS);
    }

    /**
     * Add the parameters into PreparedStatement as batch.
     * @param ps
     * @param req
     */
    public static void insert(PreparedStatement ps, AdRequest req) {
        try {
            ps.setString(1, req.getStat_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
