package cn.van.kuang.netty3x;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HandshakeCompleteListener implements ChannelFutureListener {
    private final static Logger logger = LoggerFactory.getLogger(HandshakeCompleteListener.class);

    private final Channel channel;

    public HandshakeCompleteListener(Channel channel) {
        this.channel = channel;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            logger.info("Ssl handshake successfully.");
            startSendPing();
        } else {
            logger.error("Fail to handshake, will close the channel", future.getCause());
            channel.close();
        }
    }

    private void startSendPing() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                (Runnable) () -> channel.write("PING"),
                1000,
                1000,
                TimeUnit.MILLISECONDS);
    }

}