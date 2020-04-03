package domain.tables.seam;

import domain.Pixel;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class VerticalSeamTableTest extends AbstractSeamTableTest{
    int[][] verticalCumulativeEnergies =
            new int[][]{
                    {1, 4, 3, 5, 2},
                    {4, 3, 8, 4, 5},
                    {8, 5, 7, 6, 5}
    };

    public VerticalSeamTableTest() {
        seamTable = new VerticalSeamTable(new BufferedImage(5, 3, TYPE_INT_ARGB));
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 5; col++) {
                Pixel pixel = new Pixel();
                pixel.row = row; pixel.col = col;
                pixel.energy = energies[row][col];
                seamTable.set(row, col, pixel);
            }
        seamTable.computeSeams();
    }

    @Test
    public void initialCumulativeEnergiesAreCorrect() {
        assertThat(seamTable.getCumulativeEnergyArray(), is(verticalCumulativeEnergies));
    }

    @Test
    public void initialSeamsAreCorrect() {
        Pixel[] seamPixels = new Pixel[3];
        setSeamPixels(seamPixels, 0);
        assertThat(getPixelPositions(seamPixels), is(positions(rc(2, 1), rc(1, 1), rc(0, 0))));
        setSeamPixels(seamPixels, 1);
        assertThat(getPixelPositions(seamPixels), is(positions(rc(2, 4), rc(1, 3), rc(0, 4))));
        setSeamPixels(seamPixels, 2);
        assertThat(getPixelPositions(seamPixels), is(positions(rc(2, 3), rc(1, 3), rc(0, 4))));
        setSeamPixels(seamPixels, 3);
        assertThat(getPixelPositions(seamPixels), is(positions(rc(2, 2), rc(1, 1), rc(0, 0))));
        setSeamPixels(seamPixels, 4);
        assertThat(getPixelPositions(seamPixels), is(positions(rc(2, 0), rc(1, 1), rc(0, 0))));
    }

    @Test
    public void initialAdjacencyListsAreCorrect() {
        Integer[][] nullAdjacencyListPositions = new Integer[][]{{0, 1}, {0, 3}, {1, 0}, {1, 2}, {1, 4}};
        Stream<Integer[]> lastRowPositions = IntStream.range(0, 5).mapToObj(i -> new Integer[]{2, i});
        Pixel[] pixels = Stream.concat(Arrays.stream(nullAdjacencyListPositions), lastRowPositions)
                .map(ints -> seamTable.get(ints[0], ints[1])).toArray(Pixel[]::new);
        for (Pixel pixel : pixels)
            assertThat(pixel, hasProperty("adjacents", is(array(nullValue(), nullValue(), nullValue()))));
        assertThat(getPixelPositions(seamTable.get(0, 0).adjacents), is(positions(null, rc(1, 0), rc(1, 1))));
        assertThat(getPixelPositions(seamTable.get(0, 2).adjacents), is(positions(null, rc(1, 2), null)));
        assertThat(getPixelPositions(seamTable.get(0, 4).adjacents), is(positions(rc(1, 3), rc(1, 4), null)));
        assertThat(getPixelPositions(seamTable.get(1, 1).adjacents), is(positions(rc(2, 0), rc(2, 1), rc(2, 2))));
        assertThat(getPixelPositions(seamTable.get(1, 3).adjacents), is(positions(null, rc(2, 3), rc(2, 4))));
    }

}
