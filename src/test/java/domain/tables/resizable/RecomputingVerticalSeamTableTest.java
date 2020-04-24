package domain.tables.resizable;

import domain.tables.resizable.seam.RecomputingVerticalSeamTable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

@SuppressWarnings("unchecked")
public class RecomputingVerticalSeamTableTest extends AbstractSeamTableTest{
    RecomputingVerticalSeamTestTable seamTestTable;
    int[][] correctCumulativeEnergies =
            new int[][]{
                    {1, 4, 3, 5, 2},
                    {4, 3, 8, 4, 5},
                    {8, 5, 7, 6, 5}
    };

    @Before
    public void setSeamTestTable() {
        seamTestTable = spy(new RecomputingVerticalSeamTestTable(new BufferedImage(5, 3, TYPE_INT_ARGB)));

    }

    @Test
    public void initialCumulativeEnergiesAreCorrect() {
        stubEnergyTableMethods();
        seamTestTable.setEnergies(fakeEnergies);
        seamTestTable.computeSeams();
        assertThat(seamTestTable.getCumulativeEnergies(), is(correctCumulativeEnergies));
    }

    private void stubEnergyTableMethods() {
        EnergyTable energyTable = seamTestTable;
        doNothing().when(energyTable).computeDualGradientEnergyForLeftTopCorner();
        doNothing().when(energyTable).computeDualGradientEnergyForTopRow(anyInt());
        doNothing().when(energyTable).computeDualGradientEnergyForRightTopCorner();
        doNothing().when(energyTable).computeDualGradientEnergyForLeftmostCol(anyInt());
        doNothing().when(energyTable).computeDualGradientEnergy(anyInt(), anyInt());
        doNothing().when(energyTable).computeDualGradientEnergyForRightmostCol(anyInt());
        doNothing().when(energyTable).computeDualGradientEnergyForLeftBottomCorner();
        doNothing().when(energyTable).computeDualGradientEnergyForBottomRow(anyInt());
        doNothing().when(energyTable).computeDualGradientEnergyForRightBottomCorner();
    }

    /*@Test
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
    }*/

    /*@Test
    public void removeSeamsWorksCorrectly() {
        seamTable.removeSeams(1);
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
        assertThat(seamTable.getCumulativeEnergyArray(), is(correctCumulativeEnergies));
    }*/

    private static class RecomputingVerticalSeamTestTable extends RecomputingVerticalSeamTable {

        public RecomputingVerticalSeamTestTable(BufferedImage image) {
            super(image);
        }

        public void setEnergies(int[][] energies) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    Integer[] pixel = pixels[row][col] = new Integer[dataFieldCount];
                    pixel[red] = pixel[green] = pixel[blue] = pixel[energy] = 0;
                    pixels[row][col][energy] = energies[row][col];
                }
            }
        }

        public int[][] getCumulativeEnergies() {
            int[][] cumulativeEnergies = new int[height][width];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    cumulativeEnergies[row][col] = get(row, col)[cumulativeEnergy];
                }
            }
            return cumulativeEnergies;
        }

/*        @Override
        protected void computeDualGradientEnergy(int r, int c) {

        }

        @Override
        protected void computeDualGradientEnergyForLeftTopCorner() {

        }

        @Override
        protected void computeDualGradientEnergyForTopRow(int c) {

        }

        @Override
        protected void computeDualGradientEnergyForRightTopCorner() {

        }

        @Override
        protected void computeDualGradientEnergyForLeftmostCol(int r) {

        }

        @Override
        protected void computeDualGradientEnergyForRightmostCol(int r) {

        }

        @Override
        protected void computeDualGradientEnergyForLeftBottomCorner() {

        }

        @Override
        protected void computeDualGradientEnergyForBottomRow(int c) {

        }

        @Override
        protected void computeDualGradientEnergyForRightBottomCorner() {

        }*/

    }

}
