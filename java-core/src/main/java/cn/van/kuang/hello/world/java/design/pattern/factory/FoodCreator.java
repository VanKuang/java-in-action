package cn.van.kuang.hello.world.java.design.pattern.factory;

public class FoodCreator implements Creator<String> {
    @Override
    public String create() {
        return "Pizza";
    }
}
