package cn.van.kuang.java.core.data.structure.recursion;

public class BinarySearch {

    public int search(int[] data, int target) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException();
        }

        int low = 0;
        int high = data.length - 1;

        return doSearch(data, target, low, high);
    }

    private int doSearch(int[] data, int target, int low, int high) {
        if (low > high) {
            throw new RuntimeException("Can't find [" + target + "]");
        }

        int mid = (low + high) / 2;
        if (target == data[mid]) {
            return mid;
        }

        if (target < data[mid]) {
            return doSearch(data, target, low, mid - 1);
        }

        if (target > data[mid]) {
            return doSearch(data, target, mid + 1, high);
        }

        throw new RuntimeException("Can't find [" + target + "]");
    }

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();

        int[] data = new int[]{1, 2, 5, 10, 100, 999};

        System.out.println(binarySearch.search(data, 1));
        System.out.println(binarySearch.search(data, 10));
        System.out.println(binarySearch.search(data, 999));
        System.out.println(binarySearch.search(data, -1));
    }

}
