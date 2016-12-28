package cn.van.kuang.java.core.algorithms;

import cn.van.kuang.java.core.algorithms.utils.Checker;
import cn.van.kuang.java.core.algorithms.utils.Printer;

public class Merge implements Algorithm {

    private static final String NAME = "Merge";

    public static void sort(int[] integers) {
        Checker.isNotNull(integers);

        sort(integers, 0, integers.length - 1);

        Printer.print(NAME, integers);
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

    @Override
    public String name() {
        return NAME;
    }
}
