package cn.van.kuang.netty5x.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ID {

    private static Lock lock = new ReentrantLock();
    private static long SEED = System.currentTimeMillis();

    public static long generate() {
        long temp = 0;
        try {
            lock.lock();
            temp = SEED++;
        } finally {
            lock.unlock();
        }

        return temp;
    }

    private ID() {
    }

}

