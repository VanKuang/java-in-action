package cn.van.kuang.hello.world.java.ruleengine;

import java.util.HashMap;
import java.util.Map;

public class FluentMap<K, V> {

    private Map<K, V> map;

    public static <K, V> FluentMap<K, V> map(int size) {
        return new FluentMap<K, V>(size);
    }

    private FluentMap(int size) {
        this.map = new HashMap<K, V>(size);
    }

    public FluentMap<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public V get(K key) {
        return map.get(key);
    }
}

