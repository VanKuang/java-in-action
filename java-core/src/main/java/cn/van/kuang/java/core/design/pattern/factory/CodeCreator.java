package cn.van.kuang.java.core.design.pattern.factory;

public class CodeCreator implements Creator<String> {
    @Override
    public String create() {
        return "System.out.println()";
    }
}
