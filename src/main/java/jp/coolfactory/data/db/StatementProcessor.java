package jp.coolfactory.data.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by wangqi on 22/2/2017.
 */
@FunctionalInterface
public interface StatementProcessor<T> {

    public String process(T t) throws SQLException;

}
