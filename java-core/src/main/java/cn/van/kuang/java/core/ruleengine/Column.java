package cn.van.kuang.java.core.ruleengine;

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
