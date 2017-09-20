package cn.van.kuang.java.core.ymal;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class YmalReader {

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/VanKuang/Development/workspace/java-in-action/java-core/src/main/resources/test.yaml";

        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
            TestObject testObject = yaml.loadAs(in, TestObject.class);
            System.out.println(testObject.toString());
        }
    }

}
