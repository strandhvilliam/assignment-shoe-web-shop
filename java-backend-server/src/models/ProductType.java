package models;

import java.util.List;

public class ProductType {

    private final int id;
    private final String name;
    private final Brand brand;
    private final int price;

    private final List<Color> colors;
    private final List<Category> categories;

    public ProductType(int id, String name, Brand brand, int price, List<Color> colors, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.colors = colors;
        this.categories = categories;
    }

    public ProductType(String name, Brand brand, int price, List<Color> colors, List<Category> categories) {
        this.id = -1;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.colors = colors;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Brand getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", price=" + price +
                ", colors=" + colors +
                ", categories=" + categories +
                '}';
    }
}
