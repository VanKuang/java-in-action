package cn.van.kuang.netty5x.client.request.sync;

import cn.van.kuang.netty5x.client.request.Requester;
import cn.van.kuang.netty5x.client.request.ResponseObserver;
import cn.van.kuang.netty5x.model.Request;
import cn.van.kuang.netty5x.model.Response;
import cn.van.kuang.netty5x.model.TraceableRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@ChannelHandler.Sharable
public class SyncRequester extends ChannelHandlerAdapter implements Requester {

    private final static Logger logger = LoggerFactory.getLogger(TraceableRequest.class);

    private Channel channel;

    private CountDownLatch isChannelInitialised = new CountDownLatch(1);

    private Map<Long, ResponseObserver> observerMap = new ConcurrentHashMap<>();

    public <T> Response<T> request(Request request) throws Exception {

        checkChannelStatus();

        TraceableRequest traceableRequest;
        if (request instanceof TraceableRequest) {
            traceableRequest = (TraceableRequest) request;
        } else {
            traceableRequest = new TraceableRequest(request);
        }

        ResponseObserver<T> responseObserver = registerResponseObserver(traceableRequest.getId());

        channel.writeAndFlush(traceableRequest);
        logger.debug("Wrote quest: {}", traceableRequest);

        try {
            return responseObserver.getResponse();
        } catch (TimeoutException te) {
            unregisterResponseObserver(traceableRequest.getId());
            throw te;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Response) {
            Response response = (Response) msg;
            long requestId = response.getRequestId();

            ResponseObserver responseObserver = unregisterResponseObserver(requestId);
            if (responseObserver != null) {
                responseObserver.onResponse(response);
            } else {
                logger.warn("Can't find matched responseObserver");
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        channel = ctx.channel();
        isChannelInitialised.countDown();
    }

    private void checkChannelStatus() throws InterruptedException, TimeoutException {
        if (!isChannelInitialised.await(10, TimeUnit.SECONDS)) {
            throw new TimeoutException("Channel not initialised yet");
        }

        if (!channel.isWritable()) {
            throw new RuntimeException("Channel is not writable");
        }
    }

    private <T> ResponseObserver<T> registerResponseObserver(long requestId) {
        ResponseObserver<T> responseObserver = new ResponseObserver<>();
        observerMap.put(requestId, responseObserver);

        return responseObserver;
    }

    private ResponseObserver unregisterResponseObserver(long requestId) {
        return observerMap.remove(requestId);
    }

}
