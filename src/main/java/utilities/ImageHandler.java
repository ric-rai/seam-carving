package utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public final class ImageHandler {

    /**
     * A helper method that saves the given image to the file system.
     * @param image The image to be saved
     * @param file The path to the output file
     * @return Boolean indicating the success
     */
    public static Boolean saveImageToFile(Image image, File file) {
        try {
            int index = file.getName().lastIndexOf(".");
            String name = file.getName().substring(0, index);
            String path = file.getParent() == null ?
                    name + ".png" : file.getParent() + "/" + name + ".png";
            ImageIO.write((RenderedImage) image, "png", new File(path));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
