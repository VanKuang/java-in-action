package cn.van.kuang.jersey.jetty;

public final class Constants {

    public final static String HOST = "http://localhost";

    public final static int PORT = 8080;

    public final static int SSL_PORT = 8443;

    public final static String PATH_ROOT = "hello-world/jersey";

    public final static String PATH_QUERY = PATH_ROOT + "/query";

    public final static String PATH_UPDATE = PATH_ROOT + "/update";

    public final static String PATH_ASYNC = PATH_ROOT + "/async";

    public final static String PATH_STREMING = PATH_ROOT + "/streaming";

    public final static String PATH_EVENTS = PATH_ROOT + "/sse";

    public final static String PATH_BROADCAST = PATH_ROOT + "/broadcast";

    public final static String PATH_MONITOR = PATH_ROOT + "/monitor";

    private Constants() {
    }
}
