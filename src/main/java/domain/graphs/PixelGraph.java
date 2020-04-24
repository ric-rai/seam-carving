package domain.graphs;

import domain.Pixel;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class PixelGraph {
    protected BufferedImage image;
    protected int width, height;
    protected Pixel first;
    protected Pixel[][] pixels;
    protected RgbValues rgbValues;

    public PixelGraph(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        if (image.getType() == TYPE_3BYTE_BGR) rgbValues = new ThreeByteBgrRgbValues(image);
        else rgbValues = new GeneralRgbValues();
        initGraph();
    }

    private void initGraph() {
        pixels = new Pixel[height][width];
        first = pixels[0][0] = new Pixel(0, 0);
        rgbValues.setFromImage(first);
        for (int col = 1; col < width; col++) {
            Pixel pixel = pixels[0][col] = new Pixel(0, col);
            rgbValues.setFromImage(pixel);
            Pixel prev = pixels[0][col - 1];
            pixel.left = prev;
            prev.right = pixel;
        }
        for (int row = 1; row < height; row++) {
            Pixel pixel = pixels[row][0] = new Pixel(row, 0);
            rgbValues.setFromImage(pixel);
            Pixel above = pixels[row - 1][0];
            pixel.above = above;
            above.below = pixel;
            for (int col = 1; col < width; col++) {
                pixel = pixels[row][col] = new Pixel(row, col);
                rgbValues.setFromImage(pixel);
                Pixel prev = pixels[row][col - 1];
                pixel.left = prev;
                prev.right = pixel;
                above = pixels[row - 1][col];
                pixel.above = above;
                above.below = pixel;
            }
        }
    }

    protected interface RgbValues {

        void setFromImage(Pixel pixel);

    }

    private class GeneralRgbValues implements RgbValues {

        public void setFromImage(Pixel pixel) {
            int argbInt = image.getRGB(pixel.col, pixel.row);
            pixel.red = (argbInt & 0xff0000) >> 16;
            pixel.green = (argbInt & 0xff00) >> 8;
            pixel.blue = argbInt & 0xff;
        }

    }

    private class ThreeByteBgrRgbValues implements RgbValues {
        byte[] bytes;

        public ThreeByteBgrRgbValues(BufferedImage image) {
            this.bytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        }

        public void setFromImage(Pixel pixel) {
            int row = pixel.row, col = pixel.col;
            int collapsedIndex = row * width + col;
            pixel.red = bytes[collapsedIndex * 3 + 2];
            pixel.green = bytes[collapsedIndex * 3 + 1];
            pixel.blue = bytes[collapsedIndex * 3];
        }
    }

}
