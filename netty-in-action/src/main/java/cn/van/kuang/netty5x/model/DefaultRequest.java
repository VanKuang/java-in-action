package cn.van.kuang.netty5x.model;

public class DefaultRequest implements Request {

    public Type getType() {
        return Type.DATABASE;
    }

    @Override
    public String toString() {
        return "DefaultRequest{type=" + getType() + "}";
    }
}
