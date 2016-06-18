package cn.van.kuang.java.core.data.structure.recursion;

import java.io.File;

public class DirectorySizeCalculator {

    public long calculate(String path) {
        return doCalculate(new File(path));
    }

    public long doCalculate(File file) {
        if (!file.exists()) {
            throw new RuntimeException("[" + file.getAbsolutePath() + "] not exist");
        }

        long size = file.length();

        if (file.isDirectory()) {
            for (String childFileName : file.list()) {
                String childFilePath = new File(file, childFileName).getAbsolutePath();
                size += calculate(childFilePath);
            }
        }

        System.out.println("[" + file.getAbsolutePath() + "], size: " + size);

        return size;
    }

    public static void main(String[] args) {
        DirectorySizeCalculator calculator = new DirectorySizeCalculator();
        System.out.println(calculator.calculate("/Users/VanKuang/Development/workspace/java-in-action/java-core"));
    }

}
