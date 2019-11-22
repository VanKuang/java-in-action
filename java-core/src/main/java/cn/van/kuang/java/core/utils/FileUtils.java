package cn.van.kuang.java.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class FileUtils {

    /**
     * Use zero-copy to copy file
     * kernel -> read buffer(descriptor)
     *
     * @param from source file path
     * @param to   destination file path
     */
    public static void copy(String from, String to) throws IOException {
        final File srcFile = new File(from);
        final long size = srcFile.length();

        try (FileChannel fileChannel = new FileInputStream(srcFile).getChannel();
             FileChannel outChannel = new FileOutputStream(new File(to)).getChannel()) {
            fileChannel.transferTo(0, size, outChannel);
        }

    }

    private FileUtils() {
    }

    public static void main(String[] args) throws Exception {
        FileUtils.copy("/Users/VanKuang/Development/mark.txt", "/Users/VanKuang/Development/mark-copy.txt");
    }
}
