package cn.van.kuang.java.core.algorithms;

import cn.van.kuang.java.core.algorithms.utils.ArrayHelper;
import cn.van.kuang.java.core.algorithms.utils.Checker;
import cn.van.kuang.java.core.algorithms.utils.Printer;

public final class Selection implements Algorithm {

    private static final String NAME = "Selection";

    @Override
    public String name() {
        return NAME;
    }

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        for (int i = 0, length = integers.length; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (integers[i] > integers[j]) {
                    ArrayHelper.swap(integers, i, j);
                }
            }
        }

        Printer.print(NAME, integers);
    }

    private Selection() {
    }
}
