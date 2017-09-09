package cn.van.kuang.guice.in.action;

public class Fund {

    private final Integer id;
    private final String name;

    public Fund(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
