package cn.van.kuang.hello.world.java.design.pattern.factory;

public class CodeFactory extends AbstractFactory<String> {
    @Override
    public void setUpCreator() {
        creator = new CodeCreator();
    }
}
