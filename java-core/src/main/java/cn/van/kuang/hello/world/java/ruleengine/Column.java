package cn.van.kuang.hello.world.java.ruleengine;

public class Column {

    public final String name;

    public final boolean isWildcard;

    public Column(String name, boolean isWildcard) {
        if (name == null) {
            throw new IllegalArgumentException("Column name can't be null");
        }

        this.name = name;
        this.isWildcard = isWildcard;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", isWildcard=" + isWildcard +
                '}';
    }
}
