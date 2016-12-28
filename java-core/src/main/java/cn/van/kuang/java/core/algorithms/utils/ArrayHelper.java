package cn.van.kuang.java.core.algorithms.utils;

public final class ArrayHelper {

    /**
     * Swap two elements' position
     *
     * @param array all elements
     * @param i     first position
     * @param j     second position
     */
    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private ArrayHelper() {
    }
}
