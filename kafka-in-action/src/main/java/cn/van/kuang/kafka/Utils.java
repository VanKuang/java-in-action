package cn.van.kuang.kafka;

import java.io.File;

public final class Utils {

    public static String getResourcePath() {
        return System.getProperty("user.dir")
                + File.separator
                + "kafka-in-action"
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "resources"
                + File.separator;
    }

    public static void main(String[] args) {
        System.out.println(getResourcePath());
    }

    private Utils() {
    }

}
