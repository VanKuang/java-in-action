package cn.van.kuang.java.core.design.pattern.factory.abstractfactory;

public interface Factory {

    Product create();

    static void main(String[] args) {
        Product tv = TVFactory.INSTANCE.create();
        System.out.println(tv);

        Product car = CarFactory.INSTANCE.create();
        System.out.println(car);
    }

}
