package cn.van.kuang.java.core.design.pattern.proxy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileBaseDocument implements Document {

    private String content;

    public FileBaseDocument() {
        loadDocument();
    }

    @Override
    public void show() {
        System.out.println(content);
    }

    private void loadDocument() {
        try {
            System.out.println("Loading file from disk...\n");
            byte[] bytes = Files.readAllBytes(Paths.get("/Users/VanKuang/Development/workspace/java-in-action/java-core/src/main/java/cn/van/kuang/java/core/design/pattern/proxy/FileBaseDocument.java"));
            content = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
