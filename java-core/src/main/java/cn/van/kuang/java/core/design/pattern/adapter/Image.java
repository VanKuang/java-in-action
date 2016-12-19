package cn.van.kuang.java.core.design.pattern.adapter;

public class Image {

    private final int width;
    private final int height;
    private final String name;

    public Image(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Image{" +
                "width=" + width +
                ", height=" + height +
                ", name=" + name +
                '}';
    }
}
