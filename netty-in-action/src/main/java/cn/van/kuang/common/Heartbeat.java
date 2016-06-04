package cn.van.kuang.common;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Heartbeat implements Serializable {

    private final String from;
    private final long timestamp;

    public Heartbeat() {
        String host;
        try {
            host = InetAddress.getLocalHost().getHostName() + "/" + InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            host = "UNKNOWN-HOST";
        }
        this.from = host;
        this.timestamp = System.currentTimeMillis();
    }

    public String getFrom() {
        return from;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "from='" + from + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
