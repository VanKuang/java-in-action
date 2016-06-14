package cn.van.kuang.hello.world.java.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadExecutor {

    private final static Logger logger = LoggerFactory.getLogger(ThreadExecutor.class);

    private void tryGetResultFromThread() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> "Hi");
        logger.info(future.get());

        executorService.shutdown();
    }

    private void tryCancelRunningThread() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<?> schedule = scheduledExecutorService.schedule(
                (Runnable) () -> logger.info("DONE"),
                2,
                TimeUnit.SECONDS);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ignored) {
        }

        logger.info("{}", schedule.isDone());

        if (!schedule.isDone()) {
            schedule.cancel(true);
            logger.info("Cancelled");
        }

        scheduledExecutorService.shutdown();
    }

    private void tryHandleTonsOfTasksWithLimitedThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Future> futures = new ArrayList<>();

        List<Runnable> tasks = collectTasks();
        for (Runnable task : tasks) {
            futures.add(executorService.submit(task));
            logger.info("Submitted Task");
        }

        for (Future future : futures) {
            future.get();
        }

        executorService.shutdown();
    }

    private List<Runnable> collectTasks() {
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int count = i;
            tasks.add(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignored) {
                }
                logger.info("Task{} Done", count);
            });
        }
        return tasks;
    }

    private void tryThreadLocal() {
        final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        int count = 0;

        while (count < 10) {
            executorService.submit((Runnable) () -> {
                logger.info("Inside threadlocal: {}", threadLocal.get());

                Long aLong = System.nanoTime();
                logger.info("Set {} to threadlocal", aLong);
                threadLocal.set(aLong);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignored) {
                }
            });

            count++;
        }

    }

    public static void main(String[] args) throws Exception {
        ThreadExecutor threadExecutor = new ThreadExecutor();
        threadExecutor.tryGetResultFromThread();
        threadExecutor.tryCancelRunningThread();
        threadExecutor.tryHandleTonsOfTasksWithLimitedThreadPool();
        threadExecutor.tryThreadLocal();
    }

}
