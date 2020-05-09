package domain.tables;

import data.structures.ResizableTable;
import domain.RgbService;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class ResizablePixelTable implements PixelTable<Integer[]>, RgbService {
    private final ResizableTable<Integer[]> table;
    private final BufferedImage image;
    private final int dataFieldCount;
    private final int red;
    private final int green;
    private final int blue;

    public ResizablePixelTable(ResizableTable<Integer[]> table, BufferedImage img,
                               int dataFieldCount, int indexRed, int indexGreen, int indexBlue) {
        this.table = table;
        this.image = img;
        this.dataFieldCount = dataFieldCount;
        this.red = indexRed;
        this.green = indexGreen;
        this.blue = indexBlue;
        if (image.getType() == TYPE_3BYTE_BGR) setRgbValuesFromThreeByteBgrImage();
        else setRgbValuesUsingGeneralMethod();
    }

    private void setRgbValuesUsingGeneralMethod() {
        for (int row = 0; row < table.getHeight(); row++) {
            for (int col = 0; col < table.getWidth(); col++) {
                int argbInt = image.getRGB(col, row);
                table.set(row, col, new Integer[dataFieldCount]);
                table.get(row, col)[red] = (argbInt & 0xff0000) >> 16;
                table.get(row, col)[green] = (argbInt & 0xff00) >> 8;
                table.get(row, col)[blue] = argbInt & 0xff;
            }
        }
    }

    private void setRgbValuesFromThreeByteBgrImage() {
        byte[] bytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        for (int row = 0; row < table.getHeight(); row++) {
            for (int col = 0; col < table.getWidth(); col++) {
                int collapsedIndex = row * table.getWidth() + col;
                table.set(row, col, new Integer[dataFieldCount]);
                table.get(row, col)[red] = bytes[collapsedIndex * 3 + 2] & 0xFF;
                table.get(row, col)[green] = bytes[collapsedIndex * 3 + 1] & 0xFF;
                table.get(row, col)[blue] = bytes[collapsedIndex * 3] & 0xFF;
            }
        }
    }

    @Override
    public int getArgbInt(int row, int col) {
        int red = table.get(row, col)[this.red];
        int green = table.get(row, col)[this.green];
        int blue = table.get(row, col)[this.blue];
        return (((red << 8) + green) << 8) + blue;
    }


    @Override
    public Integer getRedIndex() {
        return red;
    }

    @Override
    public Integer getGreenIndex() {
        return green;
    }

    @Override
    public Integer getBlueIndex() {
        return blue;
    }

    @Override
    public ResizableTable<Integer[]> getTable() {
        return table;
    }

}