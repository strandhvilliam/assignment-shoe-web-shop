package models;

public class ReportCustomerSpending {

    private final Customer customer;
    private final int totalSpending;

    public ReportCustomerSpending(Customer customer, int totalSpending) {
        this.customer = customer;
        this.totalSpending = totalSpending;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getTotalSpending() {
        return totalSpending;
    }
}
