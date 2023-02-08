package models;

public class Item {

    private final int id;
    private final ProductType productType;
    private final Size size;

    public Item(int id, ProductType productType, Size size) {
        this.id = id;
        this.productType = productType;
        this.size = size;
    }

    public Item(ProductType productType, Size size) {
        this.id = -1;
        this.productType = productType;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Size getSize() {
        return size;
    }

    public int getQuantity() {
        return 1;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", productType=" + productType +
                ", size=" + size +
                '}';
    }
}
