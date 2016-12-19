package cn.van.kuang.java.core.design.pattern.adapter;

public class DefaultImageSource implements ImageSource {

    public Image get() {
        return new Image(100, 200, "Image_Test");
    }

}
