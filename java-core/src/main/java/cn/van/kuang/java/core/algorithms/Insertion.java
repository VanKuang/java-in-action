package cn.van.kuang.java.core.algorithms;

public final class Insertion implements Algorithm {

    private static final String NAME = "Insertion";

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        int i, j, target;
        int length = integers.length;

        for (i = 1; i < length; i++) {
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

        Printer.print(NAME, integers);
    }

    @Override
    public String name() {
        return NAME;
    }

    private Insertion() {
    }
}
