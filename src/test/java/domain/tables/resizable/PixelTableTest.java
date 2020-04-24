package domain.tables.resizable;

import domain.tables.resizable.PixelTable;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class PixelTableTest {
    int[][] rgbValues = {
            {255, 0, 0}, {0, 255, 0},
            {0, 0, 255}, {127, 1, 1},
            {1, 127, 1}, {1, 1, 127}
    };
    int width = 2, height = 3;
    PixelTestTable pixelTestTable;

    @Test
    public void rgbValuesWorkCorrectly() {
        pixelTestTable = new PixelTestTable(getImageFromRgbArray(rgbValues, width, height, TYPE_INT_ARGB));
        pixelTestTable.setRgbValuesFromImage();
        assertThat(pixelTestTable.getRgbArray(), is(rgbValues));
        pixelTestTable = new PixelTestTable(getImageFromRgbArray(rgbValues, width, height, TYPE_3BYTE_BGR));
        pixelTestTable.setRgbValuesFromImage();
        assertThat(pixelTestTable.getRgbArray(), is(rgbValues));
    }

    private static class PixelTestTable extends PixelTable {

        public PixelTestTable(BufferedImage image) {
            super(image);
        }

        public int[][] getRgbArray() {
            int[][] rgbArray = new int[width * height][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 2 ;col++) {
                    int i = row * width + col;
                    Integer[] pixel = pixels[row][col];
                    rgbArray[i] = new int[]{pixel[red], pixel[green], pixel[blue]};
                }
            }
            return rgbArray;
        }

    }

}
