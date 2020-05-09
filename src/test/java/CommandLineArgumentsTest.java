import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("FieldCanBeLocal")
public class CommandLineArgumentsTest {
    private final Integer EXPECTED_NUMBER_OF_ARGUMENTS = 4;
    private final String TEST_IMAGE_PATH = "src/test/resources/test_image.jpg";
    private final String TEST_OUTPUT_PATH = "src/test/resources/test_output_image.jpg";
    private CommandLineArguments commandLineArguments;

    @Before
    public void createTestImage() {
        BufferedImage img = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        File file = new File(TEST_IMAGE_PATH);
        try {
            ImageIO.write(img, "JPEG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void correctNumberOfArguments(){
        String[][] argumentArrays = {
                {},
                {TEST_IMAGE_PATH},
                {TEST_IMAGE_PATH, "0"},
                {TEST_IMAGE_PATH, "0", "0"},
                {TEST_IMAGE_PATH, "0", "0", TEST_OUTPUT_PATH},
                {TEST_IMAGE_PATH, "0", "0", TEST_OUTPUT_PATH, ""}
        };
        for (int i = 0; i < argumentArrays.length; i++) {
            setCommandLineArguments(new CommandLineArguments(argumentArrays[i]));
            Boolean areValid = commandLineArguments.areValid();
            if (i == EXPECTED_NUMBER_OF_ARGUMENTS) {
                assertThat(areValid, is(true));
                assertThat(commandLineArguments.getValidationErrorMessage().isEmpty(), is(true));
            } else {
                assertThat(areValid, is(false));
                assertThat(commandLineArguments.getValidationErrorMessage().contains("Error"), is(true));
            }
        }
    }

    public void setCommandLineArguments(CommandLineArguments commandLineArguments) {
        this.commandLineArguments = commandLineArguments;
    }
}