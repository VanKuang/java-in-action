package cn.van.kuang.java.core.algorithms;

import cn.van.kuang.java.core.algorithms.utils.MillsWatcher;
import cn.van.kuang.java.core.algorithms.utils.NanoWatcher;
import cn.van.kuang.java.core.algorithms.utils.Watcher;

import java.util.Arrays;

public class AlgorithmRunner {

    public static void main(String[] args) {
        System.out.println("Original array: " + Arrays.toString(createArray()));
        System.out.println("===================================================================");

        Selection.sort(createArray());
        Insertion.sort(createArray());
        Insertion.sort1(createArray());
        Insertion.sort2(createArray());
        Shell.sort(createArray());
        Merge.sort(createArray());

        runWithBigArray();
    }

    private static void runWithBigArray() {
        System.out.println("===================================================================");
        System.out.println("===================================================================");
        System.out.println("===================================================================");

        int[] bigArray = createBigArray();
        int[] originalBigArray = Arrays.copyOf(bigArray, bigArray.length);

        Watcher millsWatcher = new MillsWatcher();
        Watcher nanoWatcher = new NanoWatcher();
        Insertion.sort(bigArray);
        System.out.println("Elapse: " + millsWatcher.elapse() + "ms");
        System.out.println("Elapse: " + nanoWatcher.elapse() + "ns");

        millsWatcher = new MillsWatcher();
        nanoWatcher = new NanoWatcher();
        Shell.sort(originalBigArray);
        System.out.println("Elapse: " + millsWatcher.elapse() + "ms");
        System.out.println("Elapse: " + nanoWatcher.elapse() + "ns");
    }

    private static int[] createArray() {
        return new int[]{1, 3, 2, 5, 10, 9, 4, 7, 8, 6};
    }

    private static int[] createBigArray() {
        int size = 10000;
        int[] array = new int[size];

        int index = size;
        while (index > 0) {
            array[--index] = (int) (Math.random() * size);
        }

        return array;
    }

}
