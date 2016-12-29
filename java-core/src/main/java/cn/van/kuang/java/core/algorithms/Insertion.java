package cn.van.kuang.java.core.algorithms;

import cn.van.kuang.java.core.algorithms.utils.ArrayHelper;
import cn.van.kuang.java.core.algorithms.utils.Checker;
import cn.van.kuang.java.core.algorithms.utils.Printer;

public final class Insertion implements Algorithm {

    private static final String NAME = "Insertion";

    @Override
    public String name() {
        return NAME;
    }

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        int j;
        int target;

        for (int i = 1, length = integers.length; i < length; i++) {
            j = i;
            target = integers[j];

            while (j > 0 && target < integers[j - 1]) {
                integers[j] = integers[j - 1];
                j--;
            }

            integers[j] = target;
        }

        Printer.print(NAME, integers);
    }

    public static void sort1(int[] integers) {
        Checker.isNotNull(integers);

        for (int i = 1, length = integers.length; i < length; i++) {
            int j;
            int tmp = integers[i];
            for (j = i - 1; j >= 0; j--) {
                if (tmp < integers[j]) {
                    integers[j + 1] = integers[j];
                } else {
                    break;
                }
            }

            integers[j + 1] = tmp;
        }

        Printer.print(NAME + "-1", integers);
    }

    public static void sort2(int[] integers) {
        for (int i = 1, length = integers.length; i < length; i++) {
            for (int j = i; j > 0 && integers[j] < integers[j - 1]; j--) {
                ArrayHelper.swap(integers, j, j - 1);
            }
        }

        Printer.print(NAME + "-2", integers);
    }

    private Insertion() {
    }
}
