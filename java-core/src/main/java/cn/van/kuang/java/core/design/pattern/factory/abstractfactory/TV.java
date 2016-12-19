package cn.van.kuang.java.core.design.pattern.factory.abstractfactory;

import java.math.BigDecimal;

public class TV implements Product {

    private final Screen screen;
    private final Cable cable;

    public TV(Screen screen, Cable cable) {
        this.screen = screen;
        this.cable = cable;
    }

    @Override
    public String name() {
        return "TV";
    }

    @Override
    public BigDecimal price() {
        return this.screen.price().add(this.cable.price());
    }

    @Override
    public String toString() {
        return "TV{" +
                "name=" + name() +
                ", prize=" + price() +
                ", including[" + screen +
                ", " + cable +
                "]}";
    }
}
