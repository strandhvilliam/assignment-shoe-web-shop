package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class MySQLDAO<T> implements DAO<T> {


    private final Properties properties;
    private final Map<QueryType, String> queries;

    private final ResultSetOperation<T> getTFromResultSet;

    private final ParameterStrategy<T> strategy;

    public MySQLDAO(Properties properties, DataType dataType, ResultSetOperation<T> getTFromResultSet) {
        this.properties = properties;
        this.queries = dataType.getQueries();
        this.getTFromResultSet = getTFromResultSet;
        this.strategy = StrategyFactory.getStrategy(dataType);
    }

    @Override
    public int create(Object entity) {
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(queries.get(QueryType.CREATE));
        ) {
            strategy.setParameters(stmt, (T) entity);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create");
        }
    }


    @Override
    public T read(int id) {
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(queries.get(QueryType.READ));
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return getTFromResultSet.process(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read");
        }
        return null;
    }

    @Override
    public List<T> readAll() {
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(queries.get(QueryType.READ_ALL));
        ) {
            ResultSet rs = stmt.executeQuery();

            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(getTFromResultSet.process(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read all");
        }
    }

    @Override
    public int update(int id, T entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }


}
