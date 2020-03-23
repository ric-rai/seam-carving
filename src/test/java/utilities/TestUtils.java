package utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public final class TestUtils {

    public static Exception getException(Runnable expression) {
        try {
            expression.run();
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    public static BufferedImage getImageFromRgbArray(int[][] rgbValues, int width, int height, int type) {
        BufferedImage image;
        switch (type) {
            case TYPE_3BYTE_BGR:
                image = new BufferedImage(width, height, TYPE_3BYTE_BGR); break;
            default:
                image = new BufferedImage(width, height, TYPE_INT_ARGB);
        }
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                int i = row * width + col;
                Color color = new Color(rgbValues[i][0], rgbValues[i][1], rgbValues[i][2]);
                image.setRGB(row, col, color.getRGB());
            }
        return image;
    }

}
