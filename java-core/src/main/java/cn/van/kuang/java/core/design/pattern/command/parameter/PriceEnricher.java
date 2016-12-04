package cn.van.kuang.java.core.design.pattern.command.parameter;

import java.math.BigDecimal;

public class PriceEnricher implements Command<Product> {

    @Override
    public void execute(Product product) {
        System.out.println("In PriceEnricher.execute()");

        product.setPrice(new BigDecimal("10.2"));
    }

}
