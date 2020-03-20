package utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public final class ImageHandler {

    /**
     * A helper method that saves the given image to the file system.
     * @param image object of class java.awt.Image - the image to be saved
     * @param file object of class java.io.File - the path to the output file
     * @return Boolean indicating the success
     */
    public static Boolean saveImageToFile(Image image, File file) {
        try {
            ImageIO.write((RenderedImage) image, "jpg", file);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
