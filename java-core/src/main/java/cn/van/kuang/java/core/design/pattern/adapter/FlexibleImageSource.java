package cn.van.kuang.java.core.design.pattern.adapter;

import java.util.List;

public interface FlexibleImageSource {

    Image getImageById(int id);

    List<Image> getImagesBySize(int width, int height);

}
