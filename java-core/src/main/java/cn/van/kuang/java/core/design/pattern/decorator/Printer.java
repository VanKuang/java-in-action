package cn.van.kuang.java.core.design.pattern.decorator;

public interface Printer {

    void print();

    static void main(String[] args) {
        Printer printer = new BorderDecorator(new TextPrinter());
        printer.print();
    }

}
