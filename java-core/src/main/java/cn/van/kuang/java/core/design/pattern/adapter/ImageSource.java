package cn.van.kuang.java.core.design.pattern.adapter;

public interface ImageSource {

    Image get();

    static void main(String[] args) {
        ImageSource imageSource = new ThumbnailImageSource(new DefaultImageSource());
        System.out.println(imageSource.get());
    }

}
