package cn.van.kuang.java.core.design.pattern.factory;

public class FoodCreator implements Creator<String> {
    @Override
    public String create() {
        return "Pizza";
    }
}
