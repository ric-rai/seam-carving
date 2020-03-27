import domain.ContentAwareImageScaler;
import domain.ImageScaler;
import utilities.ImageHandler;

import java.awt.*;

public class Application {
    private static CommandLineArguments args;

    public static void main(String[] arguments) {
        args = new CommandLineArguments(arguments);
        if (!args.areValid())
            exit(args.getValidationErrorMessage());
        ImageScaler scaler = new ContentAwareImageScaler(args.getImage(), args.getOutputWidth(), args.getOutputHeight());
        Image outputImage = scaler.scale();
        if (!ImageHandler.saveImageToFile(outputImage, args.getOutputFile())) {
            System.out.println("Error: Couldn't save the scaled image to the output file");
        } else {
            System.out.println("Scaled image successfully saved to the output file");
        }
    }

    private static void exit(String message) {
        System.out.println(message);
        System.out.println(args.getUsage());
        System.exit(0);
    }

}