package cn.van.kuang.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class DisruptorMain {

    public static void main(String[] args) throws Exception {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        MessageEventFactory messageEventFactory = new MessageEventFactory();

        int bufferSize = 16;

        Disruptor<MessageEvent> disruptor = new Disruptor<>(
                messageEventFactory,
                bufferSize,
                threadFactory,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

        disruptor.handleEventsWith(new MessageEventHandler());

        disruptor.start();

        RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();

        final MessageEventProducer messageEventProducer = new MessageEventProducer(ringBuffer);

        Runnable task = () -> {
            int count = 0;
            while (Thread.currentThread().isAlive()) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                messageEventProducer.produce("Message " + ++count);

                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(task);
        executor.submit(task);
        executor.submit(task);
    }

}
