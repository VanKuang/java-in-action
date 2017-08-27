package cn.van.kuang.java.core.java8;

import java.util.function.Supplier;

public interface Factory {

    static Int create(Supplier<Int> supplier) {
        return supplier.get();
    }

    static void main(String[] args) {
        Int int1 = Factory.create(Impl1::new);
        int1.method();

        Int int2 = Factory.create(Impl2::new);
        int2.method();
    }

}
