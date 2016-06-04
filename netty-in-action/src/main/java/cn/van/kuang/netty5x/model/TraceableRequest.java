package cn.van.kuang.netty5x.model;

import cn.van.kuang.netty5x.util.ID;

public class TraceableRequest implements Request {

    private final long id = ID.generate();
    private final Request request;

    public TraceableRequest(Request request) {
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public Type getType() {
        return request.getType();
    }

    @Override
    public String toString() {
        return "TraceableRequest{" +
                "id=" + id +
                ", request=" + request +
                '}';
    }
}
