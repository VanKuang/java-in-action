package cn.van.kuang.java.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();

        IntStream.rangeClosed(1, 100).forEach(i -> System.out.println(test.get()));
    }

    private String get() {
        List<String> products = new ArrayList<>();

        IntStream.rangeClosed(1, 10).forEach(i -> products.add("袜子"));
        IntStream.rangeClosed(1, 20).forEach(i -> products.add("鞋子"));
        IntStream.rangeClosed(1, 30).forEach(i -> products.add("拖鞋"));
        IntStream.rangeClosed(1, 40).forEach(i -> products.add("项链"));

        return products.get(new Random().nextInt(99));
    }

}
