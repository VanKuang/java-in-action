package cn.van.kuang.java.core.design.pattern.strategy;

import java.math.BigDecimal;

public class Context {

    private final Strategy<Integer, Long> addStrategy;
    private final Strategy<BigDecimal, BigDecimal> multiplyStrategy;

    public Context() {
        this.addStrategy = new AddStrategy();
        this.multiplyStrategy = new MultiplyStrategy();
    }

    public Long add(Integer... numbers) {
        return addStrategy.execute(numbers);
    }

    public BigDecimal multiply(BigDecimal... numbers) {
        return multiplyStrategy.execute(numbers);
    }

    public static void main(String[] args) {
        Context context = new Context();
        Long sum = context.add(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(sum);

        BigDecimal multiplyResult = context.multiply(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(4),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(6),
                BigDecimal.valueOf(7),
                BigDecimal.valueOf(8),
                BigDecimal.valueOf(9),
                BigDecimal.valueOf(10));
        System.out.println(multiplyResult);
    }

}
