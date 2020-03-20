import domain.Pixel;
import domain.PixelTable;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PixelTableTest {

    @Test
    public void constructionFromImage() {
        int[][] rgbValues = {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {127, 127, 127}};
        int width = 2, height = 2;
        Image image = getImageFromArray(rgbValues, width, height);
        PixelTable pixelTable = new PixelTable(image);
        ArrayList<ArrayList<Pixel>> pixels = pixelTable.getPixels();
        pixels.forEach(p -> p.forEach(System.out::println));
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                Pixel pixel = pixels.get(row).get(col);
                int[] rgbValue = rgbValues[row * width + col];
                assertThat(pixel.getRed(), is(rgbValue[0]));
                assertThat(pixel.getGreen(), is(rgbValue[1]));
                assertThat(pixel.getBlue(), is(rgbValue[2]));
            }
        }
    }

    public Image getImageFromArray(int[][] rgbValues, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color color = new Color(rgbValues[row][0], rgbValues[row][1], rgbValues[row][2]);
                image.setRGB(col, row, color.getRGB());
            }
        }
        return image;
    }
}
