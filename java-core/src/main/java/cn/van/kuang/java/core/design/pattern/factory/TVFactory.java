package cn.van.kuang.java.core.design.pattern.factory;

import cn.van.kuang.java.core.design.pattern.factory.vo.Cable;
import cn.van.kuang.java.core.design.pattern.factory.vo.Product;
import cn.van.kuang.java.core.design.pattern.factory.vo.Screen;
import cn.van.kuang.java.core.design.pattern.factory.vo.TV;

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
