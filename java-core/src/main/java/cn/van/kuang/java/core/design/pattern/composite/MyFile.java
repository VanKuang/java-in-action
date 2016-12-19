package cn.van.kuang.java.core.design.pattern.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyFile {

    private final String path;

    private MyFile parent;

    private List<MyFile> directories;

    public MyFile(String path) {
        File file = new File(path);

        if (!file.exists()) {
            throw new IllegalArgumentException("No such file or directory: [" + path + "]");
        }

        this.path = path;
        this.parent = null;

        initSubFiles();
    }

    public MyFile getParent() {
        return parent;
    }

    public void setParent(MyFile parent) {
        this.parent = parent;
    }

    private void initSubFiles() {
        directories = new ArrayList<>();

        File file = new File(path);

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    MyFile subFile = new MyFile(f.getAbsolutePath());
                    subFile.setParent(this);

                    directories.add(subFile);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        if (parent != null) {
            string.append("\n");
        }

        MyFile tmp = parent;
        while (tmp != null) {
            string.append("  ");
            tmp = tmp.getParent();
        }

        string.append("|--").append(path);

        directories.forEach(string::append);

        return string.toString();
    }

    public static void main(String[] args) {
        MyFile myFile = new MyFile("/Users/VanKuang/Development/workspace/java-in-action/java-core/src/main/java/cn/van/kuang/java/core/design");
        System.out.println(myFile);
    }
}
