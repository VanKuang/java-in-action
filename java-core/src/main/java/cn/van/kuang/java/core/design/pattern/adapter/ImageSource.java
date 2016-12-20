package cn.van.kuang.java.core.design.pattern.adapter;

import java.util.List;

public interface ImageSource {

    List<Image> get();

    static void main(String[] args) {
        FlexibleImageSource imageSource = new FlexibleImageSourceImpl(new DefaultImageSource());

        System.out.println(imageSource.getImageById(1));

        List<Image> imageList = imageSource.getImagesBySize(200, 300);
        System.out.println(imageList.size());
    }

}
