package domain.tables;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class PixelTable {
    protected BufferedImage image;
    protected int width, height;
    protected int[][] red, green, blue;
    protected RgbValues rgbValues;

    public PixelTable(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        red = new int[height][width];
        green = new int[height][width];
        blue = new int[height][width];
        if (image.getType() == TYPE_3BYTE_BGR) rgbValues = new ThreeByteBgrRgbValues(image);
        else rgbValues = new GeneralRgbValues();
    }

    /*protected void setRgbValues(int row, int col) {
        int argbInt = image.getRGB(col, row);
        this.red[row][col] = (argbInt & 0xff0000) >> 16;
        this.green[row][col] = (argbInt & 0xff00) >> 8;
        this.blue[row][col] = argbInt & 0xff;
    }*/

/*    private void setTableByUsingGeneralMethod(BufferedImage image) {
        System.out.println("Reading pixel data using the general method. It may take a while...");
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                set(row, col, new Pixel(row, col, image.getRGB(col, row)));
    }*/

    /*private void setTableFrom3ByteBgr(BufferedImage image) {
        System.out.println("Reading pixel data from 3BYTE_BGR image.");
        byte[] bytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                int i = getCollapsedIndex(row, col);
                set(row, col, new Pixel(row, col, bytes[i * 3 + 2], bytes[i * 3 + 1], bytes[i * 3]));
            }
    }*/

    /**
     * Gets the element in position (row, col). If the given row or column index is greater than the maximum
     * index it is interpreted to be the index 0 and if it is smaller than 0 it is interpreted to be the maximum index.
     *
     * @return Pixel
     */
/*    public Pixel get(int row, int col) {
        if (row < 0) row = height - 1;
        if (row >= height) row = 0;
        if (col < 0) col = width - 1;
        if (col >= width) col = 0;
        return table[row][col];
    }*/

    public int[][] getRed() {
        return red;
    }

    public int[][] getGreen() {
        return green;
    }

    public int[][] getBlue() {
        return blue;
    }

    protected interface RgbValues {

        void set(int row, int col);

    }

    private class GeneralRgbValues implements RgbValues {

        public void set(int row, int col) {
            int argbInt = image.getRGB(col, row);
            red[row][col] = (argbInt & 0xff0000) >> 16;
            green[row][col] = (argbInt & 0xff00) >> 8;
            blue[row][col] = argbInt & 0xff;
        }

    }

    private class ThreeByteBgrRgbValues implements RgbValues {
        byte[] bytes;

        public ThreeByteBgrRgbValues(BufferedImage image) {
            this.bytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        }

        public void set(int row, int col) {
            int collapsedIndex = row * width + col;
            red[row][col] = bytes[collapsedIndex * 3 + 2];
            green[row][col] = bytes[collapsedIndex * 3 + 1];
            blue[row][col] = bytes[collapsedIndex * 3];
        }
    }

}