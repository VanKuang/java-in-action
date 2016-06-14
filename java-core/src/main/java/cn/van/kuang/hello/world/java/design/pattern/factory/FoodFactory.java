package cn.van.kuang.hello.world.java.design.pattern.factory;

public class FoodFactory extends AbstractFactory<String> {

    @Override
    public void setUpCreator() {
        creator = new FoodCreator();
    }

}
