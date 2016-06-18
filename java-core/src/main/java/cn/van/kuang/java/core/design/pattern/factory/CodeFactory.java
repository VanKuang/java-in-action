package cn.van.kuang.java.core.design.pattern.factory;

public class CodeFactory extends AbstractFactory<String> {
    @Override
    public void setUpCreator() {
        creator = new CodeCreator();
    }
}
