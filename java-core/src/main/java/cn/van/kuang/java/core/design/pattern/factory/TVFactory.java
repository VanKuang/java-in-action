package cn.van.kuang.java.core.design.pattern.factory;

import cn.van.kuang.java.core.design.pattern.factory.domain.Cable;
import cn.van.kuang.java.core.design.pattern.factory.domain.Product;
import cn.van.kuang.java.core.design.pattern.factory.domain.Screen;
import cn.van.kuang.java.core.design.pattern.factory.domain.TV;

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
