package cn.van.kuang.java.core.design.pattern.strategy;

import java.math.BigDecimal;

public class MultiplyStrategy implements Strategy<BigDecimal, BigDecimal> {

    @Override
    public BigDecimal execute(BigDecimal[] numbers) {
        if (numbers == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ONE;
        for (BigDecimal number : numbers) {
            result = result.multiply(number);
        }

        return result;
    }

}
