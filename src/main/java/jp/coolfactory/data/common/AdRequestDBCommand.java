package jp.coolfactory.data.common;

import com.google.common.collect.Queues;
import jp.coolfactory.data.Constants;
import jp.coolfactory.data.Version;
import jp.coolfactory.data.db.DBUtil;
import jp.coolfactory.data.module.AdRequest;
import jp.coolfactory.data.server.DBJobManager;
import jp.coolfactory.data.util.StringUtil;
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

    public AdRequestDBCommand() {
    }

    @Override
    public CommandStatus handle(AdRequest adRequest) {
        try {
            DBJobManager manager = (DBJobManager)Version.CONTEXT.get(Constants.DB_JOB_MANAGER);
            manager.submitRequest(adRequest);
        } catch (Exception e) {
            LOGGER.warn("handle is interrupted.");
            return CommandStatus.Fail;
        }
        return CommandStatus.Continue;
    }

}
