package cn.van.kuang.netty5x.model;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private final long requestId;
    private final T content;

    public Response(long requestId, T content) {
        this.requestId = requestId;
        this.content = content;
    }

    public long getRequestId() {
        return requestId;
    }

    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Response{" +
                "requestId=" + requestId +
                ", content=" + content +
                '}';
    }
}
