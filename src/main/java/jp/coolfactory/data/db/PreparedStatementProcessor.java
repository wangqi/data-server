package jp.coolfactory.data.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wangqi on 22/2/2017.
 */
@FunctionalInterface
public interface PreparedStatementProcessor<T> {

    public void process(PreparedStatement preparedStatement, T t) throws SQLException;

}
