package cn.van.kuang.hello.world.java.design.pattern.factory;

public class CodeCreator implements Creator<String> {
    @Override
    public String create() {
        return "System.out.println()";
    }
}
