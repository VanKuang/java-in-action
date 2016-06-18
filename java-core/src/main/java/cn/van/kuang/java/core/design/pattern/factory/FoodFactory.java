package cn.van.kuang.java.core.design.pattern.factory;

public class FoodFactory extends AbstractFactory<String> {

    @Override
    public void setUpCreator() {
        creator = new FoodCreator();
    }

}
