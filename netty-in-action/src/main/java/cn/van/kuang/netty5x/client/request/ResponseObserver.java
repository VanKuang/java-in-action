package cn.van.kuang.netty5x.client.request;

import cn.van.kuang.netty5x.model.Response;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ResponseObserver<T> {

    private final long timeout;
    private final TimeUnit timeUnit;

    private CountDownLatch latch = new CountDownLatch(1);

    private Response<T> response;

    public ResponseObserver() {
        this(30, TimeUnit.SECONDS);
    }

    public ResponseObserver(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public Response<T> getResponse() throws InterruptedException, TimeoutException {
        if (!latch.await(timeout, timeUnit)) {
            throw new TimeoutException("Timeout to wait response");
        }

        return response;
    }

    public void onResponse(Response<T> response) {
        this.response = response;

        latch.countDown();
    }
}
