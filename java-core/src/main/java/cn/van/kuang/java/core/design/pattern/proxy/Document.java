package cn.van.kuang.java.core.design.pattern.proxy;

public interface Document {

    void show();

    static void main(String[] args) {
        Document proxy = new DocumentProxy();
        proxy.show();
    }

}
