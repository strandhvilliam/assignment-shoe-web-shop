package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dao.DAOManager;
import dao.DataType;
import dao.RequestProcessor;
import dao.StoredProcedureDAO;
import models.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Controller {


    private final DAOManager daoManager;
    private final StoredProcedureDAO storedProcedureDAO;
    private final Handler handler;
    private final ObjectMapper mapper;

    public Controller(Handler handler) {
        this.handler = handler;
        this.daoManager = new DAOManager();
        this.storedProcedureDAO = new StoredProcedureDAO();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public synchronized void handleRequest(Request request, String jsonReq) {

        switch (request) {
            case ADD_TO_CART -> processReq(request, jsonReq, (String data) -> {
                int customerId = Integer.parseInt(data.substring(1, data.indexOf(",")));
                data = data.substring(data.indexOf(",") + 1);
                int orderId = Integer.parseInt(data.substring(0, data.indexOf(",")));
                data = data.substring(data.indexOf(",") + 1);
                int productId = Integer.parseInt(data.substring(0, data.indexOf(",")));
                data = data.substring(data.indexOf(",") + 1);
                int sizeId = Integer.parseInt(data.substring(0, data.length() - 1));

                return storedProcedureDAO.addToCart(customerId, orderId, productId, sizeId);

            });
            case GET_ALL_ITEMS ->
                    processReq(request, jsonReq, (String data) -> daoManager.getDAO(DataType.ITEM).readAll());
            case GET_ALL_PRODUCT_TYPES ->
                    processReq(request, jsonReq, (String data) -> daoManager.getDAO(DataType.PRODUCT_TYPE).readAll());
            case GET_ALL_CATEGORIES ->
                    processReq(request, jsonReq, (String data) -> daoManager.getDAO(DataType.CATEGORY).readAll());
            case GET_ALL_COLORS ->
                    processReq(request, jsonReq, (String data) -> daoManager.getDAO(DataType.COLOR).readAll());
            case GET_SIZES_BY_PRODUCT_TYPE -> processReq(request, jsonReq, (String data) -> {
                final int productId = Integer.parseInt(data);
                return daoManager.getDAO(DataType.ITEM).readAll().stream()
                        .filter(item -> ((Item) item).getProductType().getId() == productId)
                        .distinct()
                        .map(item -> ((Item) item).getSize())
                        .toList();
            });
            case GET_CUSTOMER_BY_ID -> processReq(request, jsonReq, (String data) -> {
                final int customerId = Integer.parseInt(data);
                return daoManager.getDAO(DataType.CUSTOMER).read(customerId);
            });
            case GET_ORDER_BY_CUSTOMER_ID -> processReq(request, jsonReq, (String data) -> {
                final int customerId = Integer.parseInt(data);
                List<Order> orders = (List<Order>) daoManager.getDAO(DataType.ORDER).readAll();
                return orders.stream()
                        .filter(order -> order.getCustomer().getId() == customerId)
                        .filter(order -> order.getStatus() == OrderStatus.PENDING)
                        .findFirst()
                        .orElse(null);
            });
            case LOGIN_CUSTOMER -> processReq(request, jsonReq, (String data) -> {
                String email = data.substring(1, data.indexOf(","));
                data = data.substring(data.indexOf(",") + 1);
                String password = data.substring(0, data.length() - 1);
                List<Customer> customers = (List<Customer>) daoManager.getDAO(DataType.CUSTOMER).readAll();
                return customers.stream()
                        .filter(customer -> customer.getEmail().equals(email))
                        .filter(customer -> customer.getPassword().equals(password))
                        .findFirst()
                        .orElse(null);
            });
            case REPORT_TOP_TEN_PRODUCTS -> processReq(request, jsonReq, (String data) -> {
                List<OrderDetail> orderDetails = (List<OrderDetail>) daoManager.getDAO(DataType.ORDER_DETAIL).readAll();
                List<ProductType> products = (List<ProductType>) daoManager.getDAO(DataType.PRODUCT_TYPE).readAll();

                Map<ProductType, Integer> productTotals = new HashMap<>();

                orderDetails.forEach(orderDetail -> {
                    ProductType product = products.stream()
                            .filter(p -> p.getId() == orderDetail.getItem().getProductType().getId())
                            .findFirst()
                            .orElse(null);
                    if (product != null) {
                        int total = orderDetail.getQuantity();
                        if (productTotals.containsKey(product)) {
                            productTotals.put(product, productTotals.get(product) + total);
                        } else {
                            productTotals.put(product, total);
                        }
                    }
                });

                List<Map.Entry<ProductType, Integer>> topTen = productTotals.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(10)
                        .toList();


                return topTen.stream()
                        .map(entry -> new String[]{entry.getKey().getName(), String.valueOf(entry.getValue())})
                        .toArray(String[][]::new);

            });
            case REPORT_CITY_RANKING -> processReq(request, jsonReq, (String data) -> {
                List<Order> orders = (List<Order>) daoManager.getDAO(DataType.ORDER).readAll();
                List<City> cities = (List<City>) daoManager.getDAO(DataType.CITY).readAll();

                return cities.stream()
                        .map(city -> new String[]{city.getName(), String.valueOf(orders.stream()
                                .filter(order -> order.getCustomer().getCity().getId() == city.getId())
                                .mapToInt(order -> order.getOrderDetails().stream()
                                        .filter(orderDetail -> orderDetail.getOrderId() == order.getId())
                                        .mapToInt(orderDetail -> orderDetail.getItem().getProductType().getPrice()).sum())
                                .sum())})
                        .toArray(String[][]::new);

            });

            case REPORT_CUSTOMER_ORDER_RATE -> processReq(request, jsonReq, (String data) -> {

                //TODO: nu identifieras kunderna efter för och efternamn, fixa så att det funkar även om man skulle ha samma för och efternamn
                List<Order> orders = (List<Order>) daoManager.getDAO(DataType.ORDER).readAll();
                Map<String, Integer> customerTotals = orders.stream()
                        .collect(Collectors.groupingBy(order -> order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(), Collectors.summingInt(order -> 1)));

                return customerTotals.entrySet().stream()
                        .map(entry -> new String[]{entry.getKey(), String.valueOf(entry.getValue())})
                        .toArray(String[][]::new);


            });
            case REPORT_CUSTOMER_SPENDING -> processReq(request, jsonReq, (String data) -> {
                List<Order> orders = (List<Order>) daoManager.getDAO(DataType.ORDER).readAll();
                Map<String, Integer> customerTotals = orders.stream()
                        .collect(Collectors.groupingBy(order -> order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(), Collectors.summingInt(order -> order.getOrderDetails().stream()
                                .filter(orderDetail -> orderDetail.getOrderId() == order.getId())
                                .mapToInt(orderDetail -> orderDetail.getItem().getProductType().getPrice()).sum())));

                return customerTotals.entrySet().stream()
                        .map(entry -> new String[]{entry.getKey(), String.valueOf(entry.getValue())})
                        .toArray(String[][]::new);
            });
        }

    }

    public synchronized void processReq(Request request, String jsonReq, RequestProcessor<?> requestProcessor) {
        try {
            String jsonRes = mapper.writeValueAsString(requestProcessor.operation(jsonReq));
            handler.sendResponse(request, jsonRes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
