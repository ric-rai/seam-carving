package domain.tables.resizable;

import data.structures.ResizableTable;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class PixelTable extends ResizableTable<Integer[]> {
    protected BufferedImage image;
    protected final Integer[][][] pixels = table;
    protected int dataFieldCount = 3;
    protected final int red = 0;
    protected final int green = 1;
    protected final int blue = 2;

    public PixelTable(BufferedImage image) {
        super(Integer[].class, image.getWidth(), image.getHeight());
        this.image = image;
        if (image.getType() == TYPE_3BYTE_BGR) setRgbValuesFromThreeByteBgrImage();
        else setRgbValuesFromImage();
    }

    private void setRgbValuesFromImage() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int argbInt = image.getRGB(col, row);
                pixels[row][col] = new Integer[dataFieldCount];
                pixels[row][col][red] = (argbInt & 0xff0000) >> 16;
                pixels[row][col][green] = (argbInt & 0xff00) >> 8;
                pixels[row][col][blue] = argbInt & 0xff;
            }
        }
    }

    private void setRgbValuesFromThreeByteBgrImage() {
        byte[] bytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int collapsedIndex = row * width + col;
                pixels[row][col] = new Integer[dataFieldCount];
                pixels[row][col][red] = bytes[collapsedIndex * 3 + 2] & 0xFF;
                pixels[row][col][green] = bytes[collapsedIndex * 3 + 1] & 0xFF;
                pixels[row][col][blue] = bytes[collapsedIndex * 3] & 0xFF;
            }
        }
    }

}