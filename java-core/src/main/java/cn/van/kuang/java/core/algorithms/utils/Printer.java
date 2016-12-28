package cn.van.kuang.java.core.algorithms.utils;

import java.util.Arrays;

public final class Printer {

    public static void print(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void print(String algorithmName, int[] array) {
        System.out.println("[" + algorithmName + "]--" + Arrays.toString(array));
    }

    private Printer() {
    }
}
