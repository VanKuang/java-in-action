package cn.van.kuang.java.core.design.pattern.factory;

import cn.van.kuang.java.core.design.pattern.factory.product.Cable;
import cn.van.kuang.java.core.design.pattern.factory.product.Product;
import cn.van.kuang.java.core.design.pattern.factory.product.Screen;
import cn.van.kuang.java.core.design.pattern.factory.product.TV;

import java.math.BigDecimal;

public enum TVFactory implements Factory {

    INSTANCE {
        @Override
        public Product create() {
            Screen screen = new Screen(new BigDecimal(100));
            Cable cable = new Cable(new BigDecimal(10));

            return new TV(screen, cable);
        }
    }
}
