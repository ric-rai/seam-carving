package domain;

import org.junit.Before;
import org.junit.Test;

import static domain.enums.Color.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PixelTest {
    Pixel testPixel;

    @Before
    public void setTestPixel() {
        testPixel = new Pixel();
        testPixel.setRed(1);
        testPixel.setGreen(127);
        testPixel.setBlue(255);
    }

    @Test
    public void getColorWorksCorrectly() {
        assertThat(testPixel.getColor(R), is(1));
        assertThat(testPixel.getColor(G), is(127));
        assertThat(testPixel.getColor(B), is(255));
    }

    @Test
    public void constructionFromByteValuesWorksCorrectly() {
        byte red = 1, green = 127, blue = -1;
        Pixel pixel = new Pixel(red, green, blue);
        assertThat(pixel.getRed(), is(1));
        assertThat(pixel.getGreen(), is(127));
        assertThat(pixel.getBlue(), is(255));
    }

    @Test
    public void toStringWorksCorrectly() {
        assertThat(testPixel.toString(), is("Pixel{" + "red=1, green=127, blue=255}"));
    }

    @Test
    public void equalsWorksCorrectly() {
        assertThat(testPixel.equals(new Object()), is(false));
        assertThat(testPixel.equals(new Pixel(1, 127, 255)), is(true));
        assertThat(testPixel.equals(new Pixel(0, 0, 0)), is(false));
    }
}
