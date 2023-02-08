package models;

public class ReportTopTen {

    private final ProductType productType;
    private final int quantity;

    public ReportTopTen(ProductType productType, int quantity) {
        this.productType = productType;
        this.quantity = quantity;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getQuantity() {
        return quantity;
    }
}
