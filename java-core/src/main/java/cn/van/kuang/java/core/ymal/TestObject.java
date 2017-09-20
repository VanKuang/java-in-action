package cn.van.kuang.java.core.ymal;

import java.util.List;
import java.util.Map;

public class TestObject {
    private String key;
    private Map<String, String> map;
    private List<String> array;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<String> getArray() {
        return array;
    }

    public void setArray(List<String> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "key='" + key + '\'' +
                ", map=" + map +
                ", array=" + array +
                '}';
    }
}