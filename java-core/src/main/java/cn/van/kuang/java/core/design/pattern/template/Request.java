package cn.van.kuang.java.core.design.pattern.template;

import java.util.Date;

public class Request {

    private final int id;
    private final String type;
    private final Date time;

    public Request(int id, String type) {
        this.id = id;
        this.type = type;
        this.time = new Date();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", time=" + time +
                '}';
    }
}
