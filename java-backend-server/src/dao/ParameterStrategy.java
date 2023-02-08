package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ParameterStrategy<T> {
    void setParameters(PreparedStatement stmt, T entity) throws SQLException;
}
