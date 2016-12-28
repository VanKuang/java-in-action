package cn.van.kuang.java.core.algorithms.utils;

public final class MillsWatcher implements Watcher {

    private final long start;

    public MillsWatcher() {
        this.start = System.currentTimeMillis();
    }

    public long elapse() {
        return System.currentTimeMillis() - start;
    }
}
