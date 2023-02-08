package models;

public class ReportCityOrders {

    private final City city;
    private final int revenue;

    public ReportCityOrders(City city, int numOfOrders) {
        this.city = city;
        this.revenue = numOfOrders;
    }

    public City getCity() {
        return city;
    }

    public int getNumOfOrders() {
        return revenue;
    }
}
