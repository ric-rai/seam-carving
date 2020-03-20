package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class PixelTable {
    private ArrayList<ArrayList<Pixel>> pixels;
    private int width, height;

    public PixelTable(Image image) {
        BufferedImage bufferedImage = (BufferedImage) image;
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        byte[] byteTable = ((DataBufferByte)bufferedImage.getData().getDataBuffer()).getData();
        pixels = new ArrayList<>(height);
        for (int row = 0; row < height; row++) {
            ArrayList<Pixel> rowList = new ArrayList<>(width);
            pixels.add(rowList);
            for (int col = 0; col < width; col++) {
                int byteNumber = row * width + col;
                byte red = byteTable[byteNumber * 3 + 2];
                byte green = byteTable[byteNumber * 3 + 1];
                byte blue = byteTable[byteNumber * 3];
                rowList.add(new Pixel(red, green, blue));
            }
        }
    }

    public Pixel get(int x, int y) {
        if(x < 0) x = width - 1;
        if(x >= width) x = 0;
        if(y < 0) y = height - 1;
        if(y >= height) y = 0;
        return pixels.get(x).get(y);
    }

    public ArrayList<ArrayList<Pixel>> getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
