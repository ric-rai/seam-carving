package domain;

import data_structures.ArrayTable;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class PixelTable extends ArrayTable<Pixel> {

    public PixelTable(BufferedImage image) {
        super(Pixel.class, image.getWidth(), image.getHeight());
        switch (image.getType()) {
            case TYPE_3BYTE_BGR: setTableFrom_3BYTE_BGR(image); break;
            default: setTableByUsingGeneralMethod(image);
        }
    }

    private void setTableByUsingGeneralMethod(BufferedImage image) {
        System.out.println("Reading pixel data using the general method. It may take a while...");
        mapIndexed((x, y) -> {
            int color = image.getRGB(x, y);
            int blue = color & 0xff;
            int green = (color & 0xff00) >> 8;
            int red = (color & 0xff0000) >> 16;
            return new Pixel(red, green, blue);
        });
    }

    private void setTableFrom_3BYTE_BGR(BufferedImage image) {
        System.out.println("Reading pixel data from 3BYTE_BGR image");
        byte[] bytes = ((DataBufferByte)image.getData().getDataBuffer()).getData();
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                int byteNumber = row * width + col;
                byte red = bytes[byteNumber * 3 + 2];
                byte green = bytes[byteNumber * 3 + 1];
                byte blue = bytes[byteNumber * 3];
                table[row][col] = new Pixel(red, green, blue);
            }
    }

    @Override
    public Pixel get(int row, int col) {
        if(row < 0) row = width - 1;
        if(row >= width) row = 0;
        if(col < 0) col = height - 1;
        if(col >= height) col = 0;
        return table[row][col];
    }

}
