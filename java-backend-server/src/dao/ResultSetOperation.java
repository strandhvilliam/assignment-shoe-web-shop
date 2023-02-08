package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetOperation<T> {
    T process(ResultSet rs) throws SQLException;
}

