import domain.Pixel;
import domain.PixelTable;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class PixelTableTest {

    @Test
    public void constructorWorksCorrectly() {
        int[][] rgbValues = {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {127, 127, 127}};
        int width = 2, height = 2;
        BufferedImage image = getImageFromRgbArray(rgbValues, width, height, TYPE_INT_ARGB);
        PixelTable pixelTable = new PixelTable(image);
        Pixel[][] pixels = new Pixel[2][2];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                int i = row * 2 + col;
                pixels[row][col] = new Pixel(rgbValues[i][0], rgbValues[i][1], rgbValues[i][2]);
            }
        assertThat(pixelTable.getTable(), equalTo(pixels));
    }


}
