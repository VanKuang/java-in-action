package cn.van.kuang.java.core.algorithms.utils;

public class NanoWatcher implements Watcher {

    private final long start;

    public NanoWatcher() {
        this.start = System.nanoTime();
    }

    @Override
    public long elapse() {
        return System.nanoTime() - start;
    }
}
