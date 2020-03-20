import domain.Pixel;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PixelTest {

    @Test
    public void getColor() {
        Pixel pixel = new Pixel();
        pixel.setRed(0);
        pixel.setGreen(127);
        pixel.setBlue(255);
        assertThat(pixel.getColor("R"), is(0));
        assertThat(pixel.getColor("G"), is(127));
        assertThat(pixel.getColor("B"), is(255));
    }

    @Test
    public void constructionFromByteValues() {
        byte red = 0, green = 127, blue = -1;
        Pixel pixel = new Pixel(red, green, blue);
        assertThat(pixel.getRed(), is(0));
        assertThat(pixel.getGreen(), is(127));
        assertThat(pixel.getBlue(), is(255));
    }
}
