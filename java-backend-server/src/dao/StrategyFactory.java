package dao;

import models.*;

import java.sql.Date;

public class StrategyFactory {

    public static <T> ParameterStrategy<T> getStrategy(DataType dataType) {
        return switch (dataType) {
            case BRAND -> (stmt, entity) -> {
                Brand brand = (Brand) entity;
                stmt.setString(1, brand.getName());
            };

            case CATEGORY -> (stmt, entity) -> {
                Category category = (Category) entity;
                stmt.setString(1, category.getName());
            };

            case CATEGORY_MAP -> (stmt, entity) -> {
                CategoryMap categoryMap = (CategoryMap) entity;
                stmt.setInt(1, categoryMap.getProductId());
                stmt.setInt(2, categoryMap.getCategoryId());
            };

            case CITY -> (stmt, entity) -> {
                City city = (City) entity;
                stmt.setString(1, city.getName());
            };

            case COLOR -> (stmt, entity) -> {
                Color color = (Color) entity;
                stmt.setString(1, color.getName());
                stmt.setString(2, color.getHexCode());
            };

            case COLOR_MAP -> (stmt, entity) -> {
                ColorMap colorMap = (ColorMap) entity;
                stmt.setInt(1, colorMap.getProductId());
                stmt.setInt(2, colorMap.getColorId());
            };

            case CUSTOMER -> (stmt, entity) -> {
                Customer customer = (Customer) entity;
                stmt.setString(1, customer.getFirstName());
                stmt.setString(2, customer.getLastName());
                stmt.setString(3, customer.getEmail());
                stmt.setString(4, customer.getPassword());
                stmt.setString(5, customer.getAddress());
                stmt.setInt(6, customer.getCity().getId());
            };

            case ITEM -> (stmt, entity) -> {
                Item item = (Item) entity;
                stmt.setInt(1, item.getProductType().getId());
                stmt.setInt(2, item.getSize().getId());
                stmt.setInt(3, item.getQuantity());
            };

            case ORDER -> (stmt, entity) -> {
                Order order = (Order) entity;
                System.out.println(order.getCustomer().getId());
                stmt.setDate(1, Date.valueOf(order.getDate()));
                stmt.setInt(2, order.getCustomer().getId());
                stmt.setString(3, order.getStatus().toString());
            };

            case ORDER_DETAIL -> (stmt, entity) -> {
                OrderDetail orderDetail = (OrderDetail) entity;
                stmt.setInt(1, orderDetail.getOrderId());
                stmt.setInt(2, orderDetail.getItem().getId());
                stmt.setInt(3, orderDetail.getQuantity());
            };

            case PRODUCT_TYPE -> (stmt, entity) -> {
                ProductType productType = (ProductType) entity;
                stmt.setString(1, productType.getName());
                stmt.setInt(2, productType.getBrand().getId());
                stmt.setInt(3, productType.getPrice());
            };

            case SIZE -> (stmt, entity) -> {
                Size size = (Size) entity;
                stmt.setInt(1, size.getEuSize());
                stmt.setInt(2, size.getUkSize());
            };
        };
    }
}