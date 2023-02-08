package dao;

import java.util.Map;

import static java.util.Map.entry;

public enum DataType {
    BRAND(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO brand (name) VALUES (?)"),
            entry(QueryType.READ, "SELECT * FROM brand WHERE id = ?"),
            entry(QueryType.READ_ALL, "SELECT * FROM brand"),
            entry(QueryType.UPDATE, "UPDATE brand SET name = ? WHERE id = ? "),
            entry(QueryType.DELETE, "DELETE FROM brand WHERE id = ?"))),

    CATEGORY(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO category (name) VALUES (?)"),
            entry(QueryType.READ, "SELECT * FROM category WHERE id = ?"),
            entry(QueryType.READ_ALL, "SELECT * FROM category"),
            entry(QueryType.UPDATE, "UPDATE category SET name = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM category WHERE id = ?"))),

    CITY(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO city (name) VALUES (?)"),
            entry(QueryType.READ,"SELECT * FROM city WHERE id = ?"),
            entry(QueryType.READ_ALL, "SELECT * FROM city"),
            entry(QueryType.UPDATE, "UPDATE city SET name= ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM city WHERE id = ?"))),

    COLOR(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO color (name, hex) VALUES (?, ?)"),
            entry(QueryType.READ,"SELECT * FROM color WHERE id = ?"),
            entry(QueryType.READ_ALL, "SELECT * FROM color"),
            entry(QueryType.UPDATE, "UPDATE color SET name = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM color WHERE id = ?"))),

    CUSTOMER(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO customer (first_name, last_name, email, password, address, city_id) VALUES (?, ?, ?, ?, ?, ?)"),
            entry(QueryType.READ,"SELECT * FROM customer WHERE id = ?"),
            entry(QueryType.READ_ALL, "SELECT * FROM customer"),
            entry(QueryType.UPDATE, "UPDATE customer SET first_name = ?, last_name = ?, email = ?, address = ?, city_id = ?, password = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM customer WHERE id = ?"))),

    ITEM(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO item (product_id, size_id, stock_quantity) VALUES (?, ?, ?)"),
            entry(QueryType.READ,"SELECT * FROM item WHERE id = ?"),
            entry(QueryType.READ_ALL, "SELECT * FROM item"),
            entry(QueryType.UPDATE, "UPDATE item SET product_id = ?, size_id = ?, stock_quantity = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM item WHERE id = ?"))),

    ORDER(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO `order` (date, customer_id, order_status) VALUES (?, ?, ?)"),
            entry(QueryType.READ,"SELECT * FROM `order` WHERE id = ?"),
            entry(QueryType.READ_ALL, "SELECT * FROM `order`"),
            entry(QueryType.UPDATE, "UPDATE `order` SET customer_id = ?, date = ?, order_status = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM `order` WHERE id = ? "))),

    ORDER_DETAIL(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO order_detail (order_id, item_id, quantity) VALUES (?, ?, ?)"),
            entry(QueryType.READ,"SELECT * FROM order_detail WHERE id= ? "),
            entry(QueryType.READ_ALL, "SELECT * FROM order_detail"),
            entry(QueryType.UPDATE, "UPDATE order_detail SET order_id = ?, item_id = ?, quantity = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM order_detail WHERE id= ? "))),

    PRODUCT_TYPE(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO product_type (name, brand_id, price) VALUES (?, ?, ?)"),
            entry(QueryType.READ,"SELECT * FROM product_type WHERE id= ? "),
            entry(QueryType.READ_ALL, "SELECT * FROM product_type"),
            entry(QueryType.UPDATE, "UPDATE product_type SET name = ?, brand_id = ? price = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM product_type WHERE id= ? "))),

    SIZE(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO size (eu_size, uk_size) VALUES (?, ?)"),
            entry(QueryType.READ,"SELECT * FROM size WHERE id= ? "),
            entry(QueryType.READ_ALL, "SELECT * FROM size"),
            entry(QueryType.UPDATE, "UPDATE size SET eu_size = ?, uk_size = ? WHERE id = ?"),
            entry(QueryType.DELETE, "DELETE FROM size WHERE id= ? "))),

    COLOR_MAP(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO color_map (product_id, color_id) VALUES (?, ?)"),
            entry(QueryType.READ,"SELECT * FROM category_map WHERE id= ? "),
            entry(QueryType.READ_ALL,"SELECT * FROM color_map"))),

    CATEGORY_MAP(Map.ofEntries(
            entry(QueryType.CREATE, "INSERT INTO category_map (product_id, category_id) VALUES (?, ?)"),
            entry(QueryType.READ,"SELECT * FROM category_map WHERE id= ? "),
            entry(QueryType.READ_ALL, "SELECT * FROM category_map")));

    private final Map<QueryType, String> queries;

    DataType(Map<QueryType, String> queries) {
        this.queries = queries;
    }

    public Map<QueryType, String> getQueries() {
        return queries;
    }

}
