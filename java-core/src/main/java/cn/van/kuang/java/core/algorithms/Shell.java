package cn.van.kuang.java.core.algorithms;

import cn.van.kuang.java.core.algorithms.utils.ArrayHelper;
import cn.van.kuang.java.core.algorithms.utils.Checker;
import cn.van.kuang.java.core.algorithms.utils.Printer;

public final class Shell implements Algorithm {

    private static final String NAME = "Shell";

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        int length = integers.length;
        int h = 1;

        while (h < length / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && integers[j] < integers[j - h]; j -= h) {
                    ArrayHelper.swap(integers, j, j - h);
                }
            }
            h = h / 3;
        }

        Printer.print(NAME, integers);
    }

    @Override
    public String name() {
        return NAME;
    }
}
