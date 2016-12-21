package cn.van.kuang.java.core.design.pattern.chain.of.responsibility;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private final int id;
    private final Type type;
    private final Date time;

    public Request(int id, Type type) {
        this.id = id;
        this.type = type;
        this.time = new Date();
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", type=" + type +
                ", time=" + time +
                '}';
    }

    public enum Type {
        BUY,
        SELL,
        UNKNOWN
    }

}
