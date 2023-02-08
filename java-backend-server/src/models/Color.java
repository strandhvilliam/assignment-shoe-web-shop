package models;

public class Color {

    private final int id;
    private final String name;

    private final String hexCode;

    public Color(int id, String name, String hexCode) {
        this.id = id;
        this.name = name;
        this.hexCode = hexCode;
    }

    public Color(String name, String hexCode) {
        this.id = -1;
        this.name = name;
        this.hexCode = hexCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHexCode() {
        return hexCode;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
