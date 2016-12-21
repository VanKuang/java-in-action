package cn.van.kuang.java.core.design.pattern.observer;

import java.math.BigDecimal;

public class Bid implements Action {

    private final String ccyPair;
    private final BigDecimal price;

    public Bid(String ccyPair, BigDecimal price) {
        this.ccyPair = ccyPair;
        this.price = price;
    }

    public String ccyPair() {
        return ccyPair;
    }

    public BigDecimal price() {
        return this.price;
    }

    @Override
    public Type type() {
        return Type.BID;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "ccyPair=" + ccyPair() +
                ", price=" + price() +
                ", type=" + type() +
                '}';
    }
}
