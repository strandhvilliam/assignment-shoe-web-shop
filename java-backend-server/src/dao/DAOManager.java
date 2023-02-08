package dao;

import models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DAOManager {

    private final Properties properties;

    private final Map<DataType, DAO<?>> map = new HashMap<>();

    private final StoredProcedureDAO storedProcedureDAO = new StoredProcedureDAO();;


    private final ResultSetOperation<Brand> getBrandFromResultSet = rs -> new Brand(
            rs.getInt("id"),
            rs.getString("name"));

    private final ResultSetOperation<Color> getColorFromResultSet = rs -> new Color(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("hex"));

    private final ResultSetOperation<City> getCityFromResultSet = rs -> new City(
            rs.getInt("id"),
            rs.getString("name"));

    private final ResultSetOperation<Customer> getCustomerFromResultSet = rs -> new Customer(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("address"),
            (City) map.get(DataType.CITY).read(rs.getInt("city_id")));

    private final ResultSetOperation<Size> getSizeFromResultSet = rs -> new Size(rs.getInt("id"),
            rs.getInt("eu_size"),
            rs.getInt("uk_size"));

    private final ResultSetOperation<Category> getCategoryFromResultSet = rs -> new Category(
            rs.getInt("id"),
            rs.getString("name"));

    private final ResultSetOperation<ProductType> getProductTypeFromResultSet = rs -> {
        int id = rs.getInt("id");
        return new ProductType(
                rs.getInt("id"), rs.getString("name"),
                (Brand) map.get(DataType.BRAND).read(rs.getInt("brand_id")),
                rs.getInt("price"),
                map.get(DataType.COLOR_MAP).readAll().stream()
                        .map(colorMap -> (ColorMap) colorMap)
                        .filter(colorMap -> colorMap.getProductId() == id)
                        .map(colorMap -> (Color) map.get(DataType.COLOR).read(colorMap.getColorId()))
                        .toList(),
                map.get(DataType.CATEGORY_MAP).readAll().stream()
                        .map(category_map -> (CategoryMap) category_map)
                        .filter(category_map -> category_map.getProductId() == id)
                        .map(category_map -> (Category) map.get(DataType.CATEGORY).read(category_map.getCategoryId()))
                        .toList());
    };

    private final ResultSetOperation<ColorMap> getColorMapFromResultSet = rs -> new ColorMap(
            rs.getInt("product_id"),
            rs.getInt("color_id"));

    private final ResultSetOperation<CategoryMap> getCategoryMapFromResultSet = rs -> new CategoryMap(
            rs.getInt("product_id"),
            rs.getInt("category_id"));

    private final ResultSetOperation<Item> getItemFromResultSet = rs -> new Item(
            rs.getInt("id"),
            (ProductType) map.get(DataType.PRODUCT_TYPE).read(rs.getInt("product_id")),
            (Size) map.get(DataType.SIZE).read(rs.getInt("size_id")));
    private final ResultSetOperation<OrderDetail> getODFromResultSet = rs -> new OrderDetail(
            rs.getInt("id"),
            rs.getInt("order_id"),
            (Item) map.get(DataType.ITEM).read(rs.getInt("item_id")),
            rs.getInt("quantity"));

    private final ResultSetOperation<Order> getOrderFromResultSet = rs -> new Order(
            rs.getInt("id"),
            rs.getDate("date").toLocalDate(),
            (Customer) map.get(DataType.CUSTOMER).read(rs.getInt("customer_id")),
            OrderStatus.valueOf(rs.getString("order_status").trim().toUpperCase()),
            storedProcedureDAO.getDetailsByOrderID(rs.getInt("id"), getODFromResultSet));

    public DAOManager() {
        properties = new Properties();
        try {
            properties.load(DAOManager.class.getResourceAsStream("db.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        populateMap();

    }

    public DAO<?> getDAO(DataType dataType) {
        return map.get(dataType);
    }

    private void populateMap() {
        this.initDAO(DataType.BRAND, getBrandFromResultSet);
        this.initDAO(DataType.CATEGORY, getCategoryFromResultSet);
        this.initDAO(DataType.CITY, getCityFromResultSet);
        this.initDAO(DataType.COLOR, getColorFromResultSet);
        this.initDAO(DataType.CUSTOMER, getCustomerFromResultSet);
        this.initDAO(DataType.ITEM, getItemFromResultSet);
        this.initDAO(DataType.ORDER_DETAIL, getODFromResultSet);
        this.initDAO(DataType.ORDER, getOrderFromResultSet);
        this.initDAO(DataType.PRODUCT_TYPE, getProductTypeFromResultSet);
        this.initDAO(DataType.SIZE, getSizeFromResultSet);
        this.initDAO(DataType.COLOR_MAP, getColorMapFromResultSet);
        this.initDAO(DataType.CATEGORY_MAP, getCategoryMapFromResultSet);
    }

    private void initDAO(DataType dataType, ResultSetOperation<?> func) {
        MySQLDAO<?> dao = new MySQLDAO<>(properties, dataType, func);
        map.put(dataType, dao);
    }

}
