import domain.ContentAwareImageScaler;
import utilities.ImageHandler;

import java.awt.*;

public class Application {
    private static CommandLineArguments args;

    public static void main(String[] arguments) {
        args = new CommandLineArguments(arguments);
        if (!args.areValid())
            exit(args.getValidationErrorMessage());
        System.out.println("Original height: " + args.getImage().getHeight());
        System.out.println("Original width: " + args.getImage().getWidth());
        ContentAwareImageScaler scaler =
                new ContentAwareImageScaler(args.getImage(), args.getOutputWidth(), args.getOutputHeight());
        Image outputImage = scaler.scale();
        if (outputImage != null) {
            System.out.println("Saving scaled image to output file...");
            if (!ImageHandler.saveImageToFile(outputImage, args.getOutputFile())) {
                System.out.println("Error: Couldn't save the scaled image to the output file");
            } else {
                System.out.println("Scaled image successfully saved to the output file");
            }
        }
    }

    private static void exit(String message) {
        System.out.println(message);
        System.out.println(args.getUsage());
        System.exit(0);
    }

}