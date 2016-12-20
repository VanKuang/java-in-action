package cn.van.kuang.java.core.design.pattern.flyweight;

public interface Move {

    String name();

    enum Type {
        SPIN,
        CROSSOVER,
        PULLUP,
        HOOKSHOT
    }

    static void main(String[] args) {
        System.out.println(MoveFactory.create(Type.CROSSOVER).name());
        System.out.println(MoveFactory.create(Type.SPIN).name());
        System.out.println(MoveFactory.create(Type.CROSSOVER).name());
        System.out.println(MoveFactory.create(Type.PULLUP).name());

        System.out.println("In!!!!!!!!!!Nice move!!!!!!!");
    }
}
