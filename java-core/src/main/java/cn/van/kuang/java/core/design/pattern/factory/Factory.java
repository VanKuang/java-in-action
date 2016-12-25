package cn.van.kuang.java.core.design.pattern.factory;

import cn.van.kuang.java.core.design.pattern.factory.domain.Product;

public interface Factory {

    Product create();

    static void main(String[] args) {
        Product tv = TVFactory.INSTANCE.create();
        System.out.println(tv);
    }

}
