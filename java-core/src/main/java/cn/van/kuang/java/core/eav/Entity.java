package cn.van.kuang.java.core.eav;

import java.util.Map;

public class Entity {

    private final int id;
    private final Map<String, String> attributes;

    public Entity(int id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public int getId() {

        return id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", attributes=" + attributes +
                '}';
    }
}
