package domain;

import java.awt.image.BufferedImage;

import static domain.SeamCarverFactory.*;

public class ContentAwareImageScaler {
    private final BufferedImage image;
    private final Integer outputWidth;
    private final Integer outputHeight;

    public ContentAwareImageScaler(BufferedImage image, Integer outputWidth, Integer outputHeight) {
        this.image = image;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
    }

    @SuppressWarnings("ConstantConditions")
    public BufferedImage scale() {
        SeamCarver seamCarver = SeamCarverFactory.getSeamCarver(TABLE, image);
        if (outputWidth >= image.getWidth() * 2)
            return error("Output width is too large!");
        if (outputWidth <= 1)
            return error("Output width is too small!");
        if (outputHeight >= image.getHeight() * 2)
            return error("Output height is too large!");
        if (outputHeight <= 1)
            return error("Output height is too small!");
        if (outputWidth < image.getWidth()) {
            System.out.println("Downscaling width...");
            seamCarver.removeSeamsVertically(image.getWidth() - outputWidth);
        } else if (outputWidth > image.getWidth()) {
            System.out.println("Upscaling width...");
            seamCarver.addSeamsVertically(outputWidth - image.getWidth());
        }
        if (outputHeight < image.getHeight()) {
            System.out.println("Downscaling height...");
            seamCarver.removeSeamsHorizontally(image.getHeight() - outputHeight);
        } else if (outputHeight > image.getHeight()) {
            System.out.println("Upscaling height...");
            seamCarver.addSeamsHorizontally(outputHeight - image.getHeight());
        }
        System.out.println("Converting scaled pixeldata to image...");
        RgbService rgbService = seamCarver.getRgbService();
        BufferedImage scaledImage = new BufferedImage(outputWidth, image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < image.getHeight(); row++)
            for (int col = 0; col < outputWidth; col++)
                scaledImage.setRGB(col, row, rgbService.getArgbInt(row, col));
        return scaledImage;
    }

    private BufferedImage error(String message) {
        System.out.println(message);
        return null;
    }
}
