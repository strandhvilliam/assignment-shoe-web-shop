package models;


public class Size {

    private final int id;
    private final int euSize;
    private final int ukSize;

    public Size(int id, int euSize, int ukSize) {
        this.id = id;
        this.euSize = euSize;
        this.ukSize = ukSize;
    }

    public Size(int euSize, int ukSize) {
        this.id = -1;
        this.euSize = euSize;
        this.ukSize = ukSize;
    }

    public int getId() {
        return id;
    }

    public int getEuSize() {
        return euSize;
    }

    public int getUkSize() {
        return ukSize;
    }


    @Override
    public String toString() {
        return "Size{" +
                "id=" + id +
                ", euSize=" + euSize +
                ", ukSize=" + ukSize +
                '}';
    }
}
