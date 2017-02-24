package jp.coolfactory.data.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wangqi on 22/2/2017.
 */
@FunctionalInterface
public interface ResultSetProcessor {

    public void process(ResultSet resultSet) throws SQLException;

}
