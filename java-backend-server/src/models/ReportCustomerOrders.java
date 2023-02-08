package models;

public class ReportCustomerOrders {

    private final Customer customer;
    private final int numOfOrders;

    public ReportCustomerOrders(Customer customer, int numOfOrders) {
        this.customer = customer;
        this.numOfOrders = numOfOrders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }
}
