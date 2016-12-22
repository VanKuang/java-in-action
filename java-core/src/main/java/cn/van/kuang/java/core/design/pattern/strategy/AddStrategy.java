package cn.van.kuang.java.core.design.pattern.strategy;

public class AddStrategy implements Strategy<Integer, Long> {

    @Override
    public Long execute(Integer[] integers) {
        Long sum = 0L;

        if (integers == null) {
            return sum;
        }

        for (Integer integer : integers) {
            sum += integer;
        }

        return sum;
    }

}
