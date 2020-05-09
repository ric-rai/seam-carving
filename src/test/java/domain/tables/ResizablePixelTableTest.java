package domain.tables;

import data.structures.ResizableArrayTable;
import data.structures.ResizableTable;
import domain.tables.ResizablePixelTable;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class ResizablePixelTableTest {
    final int[][] rgbValues = {
            {255, 0, 0}, {0, 255, 0},
            {0, 0, 255}, {127, 1, 1},
            {1, 127, 1}, {1, 1, 127}
    };
    final int width = 2;
    final int height = 3;
    final int red = 0;
    final int green = 1;
    final int blue = 2;

    @Test
    public void rgbValuesWorkCorrectly() {
        ResizableTable<Integer[]> table = new ResizableArrayTable<>(Integer[].class, width, height);
        BufferedImage image = getImageFromRgbArray(rgbValues, width, height, TYPE_INT_ARGB);
        new ResizablePixelTable(table, image, 3, red, green, blue);
        assertThat(getRgbValues(table), is(rgbValues));
        image = getImageFromRgbArray(rgbValues, width, height, TYPE_3BYTE_BGR);
        new ResizablePixelTable(table, image, 3, red, green, blue);
        assertThat(getRgbValues(table), is(rgbValues));
    }

    private int[][] getRgbValues(ResizableTable<Integer[]> resizableTable) {
        int[][] rgbArray = new int[width * height][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 2 ;col++) {
                int i = row * width + col;
                rgbArray[i] = new int[]{
                        resizableTable.get(row, col)[red],
                        resizableTable.get(row, col)[green],
                        resizableTable.get(row, col)[blue]};
            }
        }
        return rgbArray;
    }

}
