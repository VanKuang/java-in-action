package cn.van.kuang.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEventHandler implements EventHandler<MessageEvent> {

    private final static Logger logger = LoggerFactory.getLogger(MessageEventHandler.class);

    public void onEvent(MessageEvent messageEvent,
                        long sequence,
                        boolean endOfBatch) throws Exception {
        logger.info("On Event: {}, sequence=[{}], endOfBatch=[{}]", messageEvent, sequence, endOfBatch);

        // mock process need 20ms
        Thread.sleep(20L);
    }
}
