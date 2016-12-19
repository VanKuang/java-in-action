package cn.van.kuang.java.core.design.pattern.factory;

import cn.van.kuang.java.core.design.pattern.factory.vo.Car;
import cn.van.kuang.java.core.design.pattern.factory.vo.Product;

import java.math.BigDecimal;

public class CarFactory {

    public static Product create() {
        return new Car("Tesla", new BigDecimal(1000000));
    }

    private CarFactory() throws IllegalAccessException {
        throw new IllegalAccessException("");
    }

    public static void main(String[] args) {
        Product car = CarFactory.create();
        System.out.println(car);
    }

}
