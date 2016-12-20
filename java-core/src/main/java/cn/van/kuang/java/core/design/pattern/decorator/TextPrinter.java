package cn.van.kuang.java.core.design.pattern.decorator;

public class TextPrinter implements Printer {

    @Override
    public void print() {
        System.out.print("Hi Van, today is Tuesday.");
    }

}
