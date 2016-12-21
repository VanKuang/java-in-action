package cn.van.kuang.java.core.design.pattern.iterator;

public class MyArrayList<T> implements Iterator<T> {

    private final T[] elements;
    private int index = 0;

    public MyArrayList(T[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("Elements can't be null");
        }

        this.elements = elements;
    }

    @Override
    public boolean hasNext() {
        return index < elements.length;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return elements[index++];
        }

        return null;
    }

}
