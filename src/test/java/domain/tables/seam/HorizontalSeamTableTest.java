package domain.tables.seam;

import domain.Pixel;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class HorizontalSeamTableTest extends AbstractSeamTableTest{
    int[][] horizontalCumulativeEnergies = new int[][] {
            {1, 5, 6, 11, 10},
            {3, 3, 8, 8, 11},
            {5, 5, 7, 9, 9}
    };

    public HorizontalSeamTableTest() {
        seamTable = new HorizontalSeamTable(new BufferedImage(5, 3, TYPE_INT_ARGB));
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
        assertThat(seamTable.getCumulativeEnergyArray(), is(horizontalCumulativeEnergies));
    }

    @Test
    public void initialSeamsAreCorrect() {
        Pixel[] seamPixels = new Pixel[5];
        Integer[][] positions = new Integer[][]{null, {1, 3}, {0, 2}, {1, 1}, {0, 0}};
        Function<Integer[], Integer[][]> setFirstPosition =
                rc -> IntStream.range(0, 5).mapToObj(i -> i == 0 ? rc : positions[i]).toArray(Integer[][]::new);
        setSeamPixels(seamPixels, 0);
        assertThat(getPixelPositions(seamPixels), is(setFirstPosition.apply(rc(2, 4))));
        setSeamPixels(seamPixels, 1);
        assertThat(getPixelPositions(seamPixels), is(setFirstPosition.apply(rc(0, 4))));
        setSeamPixels(seamPixels, 2);
        assertThat(getPixelPositions(seamPixels), is(setFirstPosition.apply(rc(1, 4))));
    }

    @Test
    public void initialAdjacencyListsAreCorrect() {
        Integer[][] nullAdjacencyListPositions = new Integer[][]{
                {0, 1}, {0, 3}, {0, 4},
                {1, 2}, {1, 4}, {1, 4},
                {2, 0}, {2, 1}, {2, 3}, {2, 4}};
        Pixel[] pixels = Arrays.stream(nullAdjacencyListPositions)
                .map(ints -> seamTable.get(ints[0], ints[1])).toArray(Pixel[]::new);
        for (Pixel pixel : pixels)
            assertThat(pixel, hasProperty("adjacents", is(array(nullValue(), nullValue(), nullValue()))));
        assertThat(getPixelPositions(seamTable.get(0, 0).adjacents), is(positions(null, rc(0, 1), rc(1, 1))));
        assertThat(getPixelPositions(seamTable.get(0, 2).adjacents), is(positions(null, rc(0, 3), rc(1, 3))));
        assertThat(getPixelPositions(seamTable.get(1, 0).adjacents), is(positions(null, null, rc(2, 1))));
        assertThat(getPixelPositions(seamTable.get(1, 1).adjacents), is(positions(rc(0, 2), rc(1, 2), rc(2, 2))));
        assertThat(getPixelPositions(seamTable.get(1, 3).adjacents), is(positions(rc(0, 4), rc(1, 4), rc(2, 4))));
        assertThat(getPixelPositions(seamTable.get(2, 2).adjacents), is(positions(null, rc(2, 3), null)));
    }

}