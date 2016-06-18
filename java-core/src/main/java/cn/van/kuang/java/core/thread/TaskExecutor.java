package cn.van.kuang.java.core.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskExecutor {

    private final ExecutorService executors = Executors.newFixedThreadPool(10);

    public void execute(List<SimpleTask> tasks) throws Exception {
        List<Future<String>> futures = new ArrayList<>(tasks.size());
        for (SimpleTask task : tasks) {
            Future<String> future = executors.submit(task);
            futures.add(future);
        }

        for (Future<String> future : futures) {
            System.out.println(future.get());
        }
    }

    public static void main(String[] args) throws Exception {

        List<SimpleTask> tasks = new ArrayList<>();
        tasks.add(new SimpleTask("Task1"));
        tasks.add(new SimpleTask("Task2"));
        tasks.add(new SimpleTask("Task3"));
        tasks.add(new SimpleTask("Task4"));
        tasks.add(new SimpleTask("Task5"));
        tasks.add(new SimpleTask("Task6"));
        tasks.add(new SimpleTask("Task7"));
        tasks.add(new SimpleTask("Task8"));
        tasks.add(new SimpleTask("Task9"));
        tasks.add(new SimpleTask("Task10"));

        long start = System.currentTimeMillis();
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.execute(tasks);

        System.out.println("Executed all tasks use " + (System.currentTimeMillis() - start) + " ms");

        taskExecutor.executors.shutdown();
    }

    static class SimpleTask implements Callable<String> {

        final String name;

        public SimpleTask(String name) {
            this.name = name;
        }

        public String call() throws Exception {
            Thread.sleep(1000L);
            return name + " DONE by " + Thread.currentThread().getName();
        }
    }

}
