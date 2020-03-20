package domain;

import java.awt.*;

public class ContentAwareImageScaler implements ImageScaler {
    private Image image;
    private Integer outputWidth;
    private Integer outputHeight;

    public ContentAwareImageScaler(Image image, Integer outputWidth, Integer outputHeight) {
        this.image = image;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
    }

    @Override
    public Image scale() {
        return image;
    }

    public Image getImage() {
        return image;
    }

    public Integer getOutputWidth() {
        return outputWidth;
    }

    public Integer getOutputHeight() {
        return outputHeight;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setOutputWidth(Integer outputWidth) {
        this.outputWidth = outputWidth;
    }

    public void setOutputHeight(Integer outputHeight) {
        this.outputHeight = outputHeight;
    }
}
