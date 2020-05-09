package domain.graphs;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class PixelGraphTest {
    final int[][] rgbValues = {
            {255, 0, 0}, {0, 255, 0},
            {0, 0, 255}, {127, 1, 1},
            {1, 127, 1}, {1, 1, 127}
    };
    final int width = 2;
    final int height = 3;

    @Test
    public void rgbValuesWorkCorrectly() {
        BufferedImage image = getImageFromRgbArray(rgbValues, width, height, TYPE_INT_ARGB);
        PixelGraph pixelGraph = new PixelGraph(image);
        assertThat(getRgbValues(pixelGraph), is(rgbValues));
        image = getImageFromRgbArray(rgbValues, width, height, TYPE_3BYTE_BGR);
        pixelGraph = new PixelGraph(image);
        assertThat(getRgbValues(pixelGraph), is(rgbValues));
    }

    private int[][] getRgbValues(PixelGraph pixelGraph) {
        int[][] rgbArray = new int[width * height][3];
        int row = 0, col = 0;
        Pixel pixel = pixelGraph.getLeftTop();
        while (true) {
            int i = row * width + col;
            rgbArray[i] = new int[]{pixel.red, pixel.green, pixel.blue};
            boolean lastOnRow = pixel.right == null;
            if (lastOnRow && pixel.below == null) break;
            if (!lastOnRow) {
                pixel = pixel.right;
                col++;
            } else {
                pixel = pixel.below.leftmostOnRow;
                row++;
                col = 0;
            }
        }
        return rgbArray;
    }
}
