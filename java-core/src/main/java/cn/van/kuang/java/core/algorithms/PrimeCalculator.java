package cn.van.kuang.java.core.algorithms;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PrimeCalculator {

    private long sum(final int x) {
        return IntStream.rangeClosed(0, x).filter(this::isPrime).sum();
    }

    private long quickSum(final int x) {
        final boolean[] isPrime = new boolean[x];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }
        for (int i = 2; i < isPrime.length; i++) {
            if (isPrime[i]) {
                for (int j = 2; i * j < isPrime.length; j++) {
                    isPrime[i * j] = false;
                }
            }
        }
        return IntStream.range(0, x).filter(i -> isPrime[i]).sum();
    }

    private boolean isPrime(final int x) {
        if (x <= 0 || x == 1 || (x % 2 == 0 && x != 2)) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(x); i += 2) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        final PrimeCalculator primeCalculator = new PrimeCalculator();

        long start = System.nanoTime();
        System.out.println(primeCalculator.sum(20_000_000));
        System.out.println("time usage:" + TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS));

        start = System.nanoTime();
        System.out.println(primeCalculator.quickSum(20_000_000));
        System.out.println("time usage:" + TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS));
    }

}
