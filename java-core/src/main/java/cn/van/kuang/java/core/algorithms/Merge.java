package cn.van.kuang.java.core.algorithms;

import cn.van.kuang.java.core.algorithms.utils.Checker;
import cn.van.kuang.java.core.algorithms.utils.Printer;

public class Merge implements Algorithm {

    private static final String NAME = "Merge";

    @Override
    public String name() {
        return NAME;
    }

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        sort(integers, 0, integers.length - 1);

        Printer.print(NAME, integers);
    }

    public static void sort1(int[] integers) {
        Checker.isNotNull(integers);

        sort1(integers, 0, integers.length - 1);

        Printer.print(NAME + "-1", integers);
    }

    private static void sort(int[] integers, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        int mid = (lo + hi) / 2;

        sort(integers, lo, mid);
        sort(integers, mid + 1, hi);

        merge(integers, lo, mid, hi);
    }

    private static void sort1(int[] integers, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        int mid = (lo + hi) / 2;

        sort(integers, lo, mid);
        sort(integers, mid + 1, hi);

        merge1(integers, lo, mid, hi);
    }

    private static void merge(int[] data, int lo, int mid, int hi) {
        int[] tmpData = new int[hi - lo + 1];
        int originalLo = lo;
        int midNext = mid + 1;

        int tmpDataIndex = 0;

        // copy the smaller number to the temp array
        while (lo <= mid && midNext <= hi) {
            if (data[lo] <= data[midNext]) {
                tmpData[tmpDataIndex++] = data[lo++];
            } else {
                tmpData[tmpDataIndex++] = data[midNext++];
            }
        }

        // copy left number from left range to the temp array
        while (lo <= mid) {
            tmpData[tmpDataIndex++] = data[lo++];
        }

        // copy left number from right range to the temp array
        while (midNext <= hi) {
            tmpData[tmpDataIndex++] = data[midNext++];
        }

        // copy the temp array back to the original array
        for (int integer : tmpData) {
            data[originalLo++] = integer;
        }
    }

    private static void merge1(int[] data, int lo, int mid, int hi) {
        int[] tmpData = new int[data.length];
        System.arraycopy(data, lo, tmpData, lo, hi - lo + 1);

        int midNext = mid + 1;

        for (int i = lo; i <= hi; i++) {
            if (lo > mid) { // left range fully consume
                data[i] = tmpData[midNext++];
            } else if (midNext > hi) { // right range fully consume
                data[i] = tmpData[lo++];
            } else if (tmpData[lo] > tmpData[midNext]) {
                data[i] = tmpData[midNext++];
            } else {
                data[i] = tmpData[lo++];
            }
        }
    }

    private Merge() {
    }
}
