package cn.van.kuang.java.core.algorithms;

public final class Swapper {

    public static void swap(int[] integers, int i, int j) {
        int tmp = integers[i];
        integers[i] = integers[j];
        integers[j] = tmp;
    }

    public static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private Swapper() {
    }
}
