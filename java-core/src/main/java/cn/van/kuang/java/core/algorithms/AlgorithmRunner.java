package cn.van.kuang.java.core.algorithms;

import java.util.Arrays;

public class AlgorithmRunner {

    public static void main(String[] args) {
        System.out.println("Original array: " + Arrays.toString(createArray()));

        Selection.sort(createArray());
        Insertion.sort(createArray());
        Insertion.sort1(createArray());
    }

    private static int[] createArray() {
        return new int[]{1, 3, 2, 5, 10, 9, 4, 7, 8, 6};
    }

}
