package domain.graphs;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PixelTest {
    Pixel testPixel;
    final int red = 1;
    final int green = 127;
    final int blue = 255;

    @Before
    public void setTestPixel() {
        testPixel = new Pixel();
        testPixel.red = red;
        testPixel.green = green;
        testPixel.blue = blue;
    }

    @Test
    public void toStringWorksCorrectly() {
        assertThat(testPixel.toString(), is("Pixel{" + "red=" + red + ", green=" + green + ", blue=" + blue
                + ", left=" + null + ", above=" + null + ", right=" + null + ", below=" + null
                + ", energy=" + 0 + ", cumulativeEnergy=" + 0 + ", prev=" + null + '}'));
    }

    @Test
    public void equalsWorksCorrectly() {
        assertThat(testPixel.equals(new Object()), is(false));
        Pixel pixel = new Pixel();
        assertThat(testPixel.equals(pixel), is(false));
        pixel.red = red;
        pixel.green = green;
        pixel.blue = blue;
        assertThat(testPixel.equals(pixel), is(true));
    }

}
