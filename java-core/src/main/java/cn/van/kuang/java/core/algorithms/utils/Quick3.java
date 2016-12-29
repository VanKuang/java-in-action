package cn.van.kuang.java.core.algorithms.utils;

import cn.van.kuang.java.core.algorithms.Algorithm;

public class Quick3 implements Algorithm {

    private static final String NAME = "Quick3";

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        sort(integers, 0, integers.length - 1);

        Printer.print(NAME, integers);
    }

    private static void sort(int[] data, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        int lt = lo;
        int gt = hi;
        int index = lo + 1;
        int pivot = data[lo];

        while (index <= gt) {
            if (data[index] < pivot) {
                ArrayHelper.swap(data, lt++, index++);
            } else if (data[index] > pivot) {
                ArrayHelper.swap(data, index, gt--);
            } else {
                index++;
            }
        }

        sort(data, lo, lt - 1);
        sort(data, gt + 1, hi);
    }

    @Override
    public String name() {
        return NAME;
    }

    private Quick3() {
    }
}
