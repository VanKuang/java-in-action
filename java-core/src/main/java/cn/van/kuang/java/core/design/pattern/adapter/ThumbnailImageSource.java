package cn.van.kuang.java.core.design.pattern.adapter;

public class ThumbnailImageSource implements ImageSource {

    private static final int WIDTH = 10;

    private final DefaultImageSource defaultImageSource;

    public ThumbnailImageSource(DefaultImageSource defaultImageSource) {
        this.defaultImageSource = defaultImageSource;
    }

    @Override
    public Image get() {
        Image originalImage = defaultImageSource.get();

        double ratio = (double) WIDTH / originalImage.getWidth();

        return new Image(
                WIDTH,
                (int) (originalImage.getHeight() * ratio),
                originalImage.getName());
    }
}
