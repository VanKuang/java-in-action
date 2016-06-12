package cn.van.kuang.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEventProducer {

    private final static Logger logger = LoggerFactory.getLogger(MessageEventProducer.class);

    private final RingBuffer<MessageEvent> ringBuffer;

    public MessageEventProducer(RingBuffer<MessageEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void produce(String message) {
        long sequence = ringBuffer.next();

        try {
            MessageEvent messageEvent = ringBuffer.get(sequence);
            messageEvent.setMessage(message);
        } finally {
            logger.debug("Going to publish message: {}, sequence=[{}]", message, sequence);

            ringBuffer.publish(sequence);
        }
    }
}
