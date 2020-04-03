package domain.tables.seam;

import domain.Pixel;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class RecomputingVerticalSeamTableTest extends AbstractSeamTableTest{
    int[][] correctCumulativeEnergies =
            new int[][]{
                    {1, 4, 3, 5, 2},
                    {4, 3, 8, 4, 5},
                    {8, 5, 7, 6, 5}
    };

    @Before
    public void setSeamTable() {
        seamTable = new RecomputingVerticalSeamTable(new BufferedImage(5, 3, TYPE_INT_ARGB));
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
        assertThat(seamTable.getCumulativeEnergyArray(), is(correctCumulativeEnergies));
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
    public void removeSeamsWorksCorrectly() {
/*        seamTable.removeSeams(1);
        int[][] correctCumulativeEnergies = new int[][]{
                {4, 3, 5, 2},
                {6, 8, 4, 5},
                {11, 8, 6, 5}};
        assertThat(seamTable.getCumulativeEnergyArray(), is(correctCumulativeEnergies));
        seamTable.removeSeams(1);
        correctCumulativeEnergies = new int[][]{
                {4, 3, 5},
                {6, 8, 6},
                {11, 10, 8}};
        assertThat(seamTable.getCumulativeEnergyArray(), is(correctCumulativeEnergies));*/
    }

}
