package domain;

import domain.tables.seam.RecomputingVerticalSeamTable;

import java.awt.image.BufferedImage;

public class ContentAwareImageScaler implements ImageScaler {
    private BufferedImage image;
    private Integer outputWidth;
    private Integer outputHeight;

    public ContentAwareImageScaler(BufferedImage image, Integer outputWidth, Integer outputHeight) {
        this.image = image;
        this.outputWidth = outputWidth;
        this.outputHeight = outputHeight;
    }

    @Override
    public BufferedImage scale() {
        RecomputingVerticalSeamTable seamTable = new RecomputingVerticalSeamTable(image);
        if (outputWidth < image.getWidth()) {
            seamTable.removeSeams(image.getWidth() - outputWidth);
            //Pixel[][] pixels = seamTable.getPixelArray();
            BufferedImage scaledImage = new BufferedImage(outputWidth, image.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int row = 0; row < image.getHeight(); row++) {
                for (int col = 0; col < outputWidth; col++) {
                    //Pixel pixel = pixels[row][col];
                    //int rgb = (((pixel.red << 8) + pixel.green) << 8) + pixel.blue;
                    //scaledImage.setRGB(col, row, rgb);
                }
            }
            return scaledImage;
        } else {
            System.out.println("Upscaling not supported yet!");
        }
        return image;
    }
}
