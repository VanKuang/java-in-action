package cn.van.kuang.java.core.design.pattern.command.parameter;

import java.math.BigDecimal;

public class Product {

    private final String name;

    private BigDecimal price;
    private BigDecimal weight;
    private String from;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", from='" + from + '\'' +
                '}';
    }
}
