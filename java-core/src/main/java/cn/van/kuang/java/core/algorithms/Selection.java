package cn.van.kuang.java.core.algorithms;

public final class Selection implements Algorithm {

    private static final String NAME = "Selection";

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        for (int i = 0, length = integers.length; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (integers[i] > integers[j]) {
                    Swapper.swap(integers, i, j);
                }
            }
        }

        Printer.print(NAME, integers);
    }

    @Override
    public String name() {
        return NAME;
    }

    private Selection() {
    }
}
