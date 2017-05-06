package cn.van.kuang.open.tracing;

import java.util.HashMap;
import java.util.Map;

public class Deal {
    private String id;
    private Map<String, String> trace;

    public Deal() {
        this.id = "ID" + System.currentTimeMillis();
        this.trace = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getTrace() {
        return trace;
    }
}