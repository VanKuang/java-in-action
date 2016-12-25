package cn.van.kuang.java.core.design.pattern.factory.domain;

import java.math.BigDecimal;

public class Car implements Product {

    private final String name;
    private final BigDecimal price;

    public Car(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public BigDecimal price() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
