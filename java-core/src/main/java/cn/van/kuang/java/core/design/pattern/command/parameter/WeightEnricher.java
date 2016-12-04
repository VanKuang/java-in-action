package cn.van.kuang.java.core.design.pattern.command.parameter;

import java.math.BigDecimal;

public class WeightEnricher implements Command<Product> {

    @Override
    public void execute(Product product) {
        System.out.println("In WeightEnricher.execute()");

        product.setWeight(new BigDecimal("300"));
    }

}
