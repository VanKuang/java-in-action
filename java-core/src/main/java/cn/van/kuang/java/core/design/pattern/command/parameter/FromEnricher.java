package cn.van.kuang.java.core.design.pattern.command.parameter;

public class FromEnricher implements Command<Product> {

    @Override
    public void execute(Product product) {
        System.out.println("In FromEnricher.execute()");

        product.setFrom("CHINA");
    }

}
