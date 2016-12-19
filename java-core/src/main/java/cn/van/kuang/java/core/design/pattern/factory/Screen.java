package cn.van.kuang.java.core.design.pattern.factory;

import java.math.BigDecimal;

public class Screen implements Product {

    private final BigDecimal price;

    public Screen(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String name() {
        return "Screen";
    }

    @Override
    public BigDecimal price() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Screen{" +
                "name=" + name() +
                ", price=" + price +
                '}';
    }
}
