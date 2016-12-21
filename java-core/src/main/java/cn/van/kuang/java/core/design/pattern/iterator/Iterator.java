package cn.van.kuang.java.core.design.pattern.iterator;

public interface Iterator<T> {

    boolean hasNext();

    T next();

    static void main(String[] args) {
        Iterator<Integer> iterator = new MyArrayList<>(new Integer[]{1, 2, 3});
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
