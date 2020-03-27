package domain;

import data.structures.ArrayTable;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class PixelTable extends ArrayTable<Pixel> {

    public PixelTable(BufferedImage image) {
        super(Pixel.class, image.getWidth(), image.getHeight());
        if (image.getType() == TYPE_3BYTE_BGR) setTableFrom3ByteBgr(image);
        else setTableByUsingGeneralMethod(image);
    }

    private void setTableByUsingGeneralMethod(BufferedImage image) {
        System.out.println("Reading pixel data using the general method. It may take a while...");
        mapIndexed((row, col) -> new Pixel(image.getRGB(col, row)));
    }

    private void setTableFrom3ByteBgr(BufferedImage image) {
        System.out.println("Reading pixel data from 3BYTE_BGR image.");
        byte[] bytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        mapIndexed(getCollapsedIndex.andThen(i -> new Pixel(bytes[i * 3 + 2], bytes[i * 3 + 1], bytes[i * 3])));
    }

    /**
     * Gets the element in position (row, col). If the given row or column index is greater than the maximum
     * index it is interpreted to be the index 0 and if it is smaller than 0 it is interpreted to be the maximum index.
     *
     * @return Pixel
     */
    @Override
    public Pixel get(int row, int col) {
        if (row < 0) row = height - 1;
        if (row >= height) row = 0;
        if (col < 0) col = width - 1;
        if (col >= width) col = 0;
        return table[row][col];
    }

}
