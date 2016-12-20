package cn.van.kuang.java.core.design.pattern.adapter;

import java.util.ArrayList;
import java.util.List;

public class DefaultImageSource implements ImageSource {

    public List<Image> get() {
        List<Image> imageList = new ArrayList<>();
        imageList.add(new Image(1, "IMG1", 100, 200));
        imageList.add(new Image(2, "IMG2", 200, 300));
        imageList.add(new Image(3, "IMG3", 300, 400));
        imageList.add(new Image(4, "IMG4", 400, 500));
        return imageList;
    }

}
