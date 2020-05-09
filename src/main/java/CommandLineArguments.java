import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * A Class related to command line interface. It handles the command line arguments by validating them and mapping each
 * argument to the actual data object (for example [path to image file] to java.awt.Image object).
 */
public class CommandLineArguments {
    @SuppressWarnings("FieldCanBeLocal")
    private final String usage =
            "Usage: SeamCarving [path to image file] [output width] [output height] [path to output file]";
    private String[] args;
    private BufferedImage image;
    private Integer outputWidth;
    private Integer outputHeight;
    private File outputFile;
    private String validationErrorMessage = "";

    public CommandLineArguments(String[] args) {
        this.args = args;
    }


    /** Validates and maps all the arguments.
     * @return Returns true if arguments are valid and false otherwise
     */
    public Boolean areValid() {
        return lengthIsValid() &&
                pathToImageIsValid() &&
                outputWidthAndHeightAreValid() &&
                outputFilePathIsValid();
    }

    private boolean lengthIsValid() {
        if (args == null || args.length != 4) {
            validationErrorMessage = "Error: Wrong number of arguments given.";
            return false;
        } else return true;
    }

    private boolean pathToImageIsValid() {
        try {
            image = ImageIO.read(new File(args[0]));
            return true;
        } catch (IOException e) {
            validationErrorMessage = "Error: Couldn't find the image file. Check the file path.";
            return false;
        }
    }

    private boolean outputWidthAndHeightAreValid() {
        try {
            outputWidth = Integer.parseInt(args[1]);
            outputHeight = Integer.parseInt(args[2]);
            return true;
        } catch (NumberFormatException e) {
            validationErrorMessage = "Error: Second and third argument must be integers.";
            return false;
        }
    }

    private boolean outputFilePathIsValid() {
        outputFile = new File(args[3]);
        return true;
    }

    public String getUsage() {
        return usage;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Integer getOutputWidth() {
        return outputWidth;
    }

    public Integer getOutputHeight() {
        return outputHeight;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public String getValidationErrorMessage() {
        return validationErrorMessage;
    }
}
