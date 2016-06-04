package cn.van.kuang.netty5x.client.request.async;

import cn.van.kuang.netty5x.client.request.Requester;
import cn.van.kuang.netty5x.model.Request;
import cn.van.kuang.netty5x.model.Response;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncRequester {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final Requester requester;

    public AsyncRequester(Requester requester) {
        this.requester = requester;
    }

    public <T> Future<Response<T>> requestAsync(final Request request) throws Exception {
        return executorService.submit(new Callable<Response<T>>() {
            public Response<T> call() throws Exception {
                return requester.request(request);
            }
        });
    }
}
