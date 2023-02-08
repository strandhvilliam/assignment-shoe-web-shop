package models;

public class ColorMap {
    private final int productId;
    private final int colorId;

    public ColorMap(int productId, int colorId) {
        this.productId = productId;
        this.colorId = colorId;
    }

    public int getProductId() {
        return productId;
    }

    public int getColorId() {
        return colorId;
    }
}
