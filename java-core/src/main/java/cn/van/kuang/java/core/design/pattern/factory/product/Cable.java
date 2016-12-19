package cn.van.kuang.java.core.design.pattern.factory.product;

import java.math.BigDecimal;

public class Cable implements Product {

    private final BigDecimal price;

    public Cable(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String name() {
        return "Cable";
    }

    @Override
    public BigDecimal price() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Cable{" +
                "name=" + name() +
                ", price=" + price +
                '}';
    }
}
