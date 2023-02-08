package models;

public class OrderDetail {

    private final int id;
    private final int orderId;
    private final Item item;
    private final int quantity;

    public OrderDetail(int id, int orderId, Item item, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
    }

    public OrderDetail(int orderId, Item item, int quantity) {
        this.id = -1;
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getOrderId() {
        return orderId;
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", item=" + item +
                '}';
    }


}
