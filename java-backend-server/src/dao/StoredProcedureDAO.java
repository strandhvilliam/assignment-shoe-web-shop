package dao;

import models.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StoredProcedureDAO {

    public static final String ADD_PRODUCT_TO_CART = "CALL AddToCart(?, ?, ?, ?)";
    public static final String GET_DETAILS_BY_ORDER_ID = "CALL GetDetailsByOrderID(?)";


//    DAOManager daoManager = new DAOManager();

    private final Properties properties;

    public StoredProcedureDAO() {
        properties = new Properties();
        try {
            properties.load(DAOManager.class.getResourceAsStream("db.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addToCart(int customerId, int orderId, int productId, int sizeId) {
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             CallableStatement stmt = con.prepareCall(ADD_PRODUCT_TO_CART);
        ) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, orderId);
            stmt.setInt(3, productId);
            stmt.setInt(4, sizeId);
            ResultSet res = stmt.executeQuery();
            System.out.println(res);

            return "SUCCESS + Added to cart";

        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR + Failed to add to cart";
        }

    }

    public List<OrderDetail> getDetailsByOrderID(int orderId, ResultSetOperation<OrderDetail> odOperation) {
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
             CallableStatement stmt = con.prepareCall(GET_DETAILS_BY_ORDER_ID);
        ) {
            stmt.setInt(1, orderId);
            ResultSet res = stmt.executeQuery();
            List<OrderDetail> list = new ArrayList<>();
            while (res.next()) {
                list.add(odOperation.process(res));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

}
