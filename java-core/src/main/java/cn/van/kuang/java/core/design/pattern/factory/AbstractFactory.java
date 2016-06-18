package cn.van.kuang.java.core.design.pattern.factory;

public abstract class AbstractFactory<T> {

    protected Creator<T> creator = null;

    public T create() {
        if (creator == null) {
            throw new RuntimeException("Creator is null");
        }

        return creator.create();
    }

    public abstract void setUpCreator();

    public static void main(String[] args) {
        FoodFactory foodFactory = new FoodFactory();
        foodFactory.setUpCreator();
        String food = foodFactory.create();

        CodeFactory codeFactory = new CodeFactory();
        codeFactory.setUpCreator();
        String code = codeFactory.create();

        System.out.println(food);
        System.out.println(code);
    }


}
