package domain.tables;

import domain.Pixel;
import org.junit.Test;

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
    Pixel[][] pixels = new Pixel[height][width];
    PixelTable pixelTable;

    public PixelTableTest() {
        pixelTable = new PixelTable(getImageFromRgbArray(rgbValues, width, height, TYPE_INT_ARGB));
        for (int i = 0, row = 0, col = 0; i < width * height; i++, row = i / width, col = i - row * width)
            pixels[row][col] = new Pixel(row, col, rgbValues[i][0], rgbValues[i][1], rgbValues[i][2]);
    }

    @Test
    public void constructorWorksCorrectly() {
        assertThat(pixelTable.getPixelArray(), is(pixels));
        PixelTable pixelTable3byteBgr = new PixelTable(getImageFromRgbArray(rgbValues, width, height, TYPE_3BYTE_BGR));
        assertThat(pixelTable3byteBgr.getPixelArray(), is(pixels));
    }

    @Test
    public void getWorksCorrectly() {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                assertThat(pixelTable.get(row, col), is(pixels[row][col]));
        assertThat(pixelTable.get(-1, 0), is(pixels[height - 1][0]));
        assertThat(pixelTable.get(height, 0), is(pixels[0][0]));
        assertThat(pixelTable.get(0, -1), is(pixels[0][width - 1]));
        assertThat(pixelTable.get(0, width), is(pixels[0][0]));
    }

}
