package cn.van.kuang.java.core.java8;

public interface Worker {

    default void execute() {
        System.out.println("Execute method in interface");
    }

    class Guard implements Worker {
        @Override
        public void execute() {
            System.out.println("Execute method in implementation");
        }
    }

    static void main(String[] args) {
        Worker worker = new Guard();
        worker.execute();
    }

}
