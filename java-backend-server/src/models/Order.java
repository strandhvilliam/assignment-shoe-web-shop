package models;


import java.time.LocalDate;
import java.util.List;

public class Order {
    private final int id;
    private final LocalDate date;
    private final Customer customer;

    private final List<OrderDetail> orderDetails;
    private OrderStatus status;




    public Order(int id, LocalDate date, Customer customer, OrderStatus status, List<OrderDetail> orderDetails) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.status = status;
        this.orderDetails = orderDetails;
    }

    public Order(LocalDate date, Customer customer, OrderStatus status, List<OrderDetail> orderDetails) {
        this.id = -1;
        this.date = date;
        this.customer = customer;
        this.status = status;
        this.orderDetails = orderDetails;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
    }


    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
