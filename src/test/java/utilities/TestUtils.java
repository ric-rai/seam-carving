package utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class TestUtils {

    public static Exception getException(Runnable expression) {
        try {
            expression.run();
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    public static int getArgbIntFromRgbValues(int r, int g, int b){
        r = (r << 16) & 0x00FF0000;
        g = (g << 8) & 0x0000FF00;
        b = b & 0x000000FF;
        return 0xFF000000 | r | g | b;
    }

    public static BufferedImage getImageFromRgbArray(int[][] rgbValues, int width, int height, int type) {
        BufferedImage img = new BufferedImage(width, height, type);
        for (int i = 0, row = 0, col = 0; i < width * height; i++, row = i / width, col = i - row * width)
            img.setRGB(col, row, new Color(rgbValues[i][0], rgbValues[i][1], rgbValues[i][2]).getRGB());
        return img;
    }
}
