package cn.van.kuang.java.core.design.pattern.factory.abstractfactory;

import java.math.BigDecimal;

public enum CarFactory implements Factory {

    INSTANCE {
        @Override
        public Product create() {
            return new Car("Tesla", new BigDecimal(1000000));
        }
    }

}
