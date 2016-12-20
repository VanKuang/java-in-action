package cn.van.kuang.java.core.design.pattern.decorator;

public class BorderDecorator implements Printer {

    private final Printer textPrinter;

    public BorderDecorator(Printer textPrinter) {
        this.textPrinter = textPrinter;
    }

    @Override
    public void print() {
        System.out.println("*****************************");
        System.out.print("* ");
        textPrinter.print();
        System.out.println(" *");
        System.out.println("*****************************");
    }
}
