package cn.van.kuang.java.core.design.pattern.proxy;

public class DocumentProxy implements Document {

    private Document fileBaseDocument;

    @Override
    public synchronized void show() {
        if (fileBaseDocument == null) {
            fileBaseDocument = new FileBaseDocument();
        }

        fileBaseDocument.show();
    }
}
