package cn.van.kuang.java.core.algorithms;

public final class Checker {

    public static void isNotNull(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Can't be null");
        }
    }

    private Checker() {
    }
}
