package cn.van.kuang.java.core.design.pattern.adapter;

import java.util.ArrayList;
import java.util.List;

public class FlexibleImageSourceImpl implements FlexibleImageSource {

    private final DefaultImageSource defaultImageSource;

    public FlexibleImageSourceImpl(DefaultImageSource defaultImageSource) {
        this.defaultImageSource = defaultImageSource;
    }


    @Override
    public Image getImageById(int id) {
        List<Image> imageList = defaultImageSource.get();

        for (Image image : imageList) {
            if (id == image.getId()) {
                return image;
            }
        }

        return null;
    }

    @Override
    public List<Image> getImagesBySize(int width, int height) {
        List<Image> result = new ArrayList<>();

        List<Image> imageList = defaultImageSource.get();

        for (Image image : imageList) {
            if (image.getWidth() <= width && image.getHeight() <= height) {
                result.add(image);
            }
        }

        return result;
    }
}
