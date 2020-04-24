package domain.tables.resizable;

import domain.tables.resizable.seam.RecomputingVerticalSeamTable;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

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
        stubEnergyTableMethods();
        seamTestTable.setEnergies(fakeEnergies);
    }

    @Test
    public void initialCumulativeEnergiesAreCorrect() {
        seamTestTable.computeSeams();
        assertThat(seamTestTable.getCumulativeEnergies(), is(correctCumulativeEnergies));
    }

    @Test
    public void removeSeamsWorksCorrectly() {
        seamTestTable.removeSeams(1);
        int[][] correctCumulativeEnergies = new int[][]{
                {/*1,*/ 4, 3, 5, 2},
                {4, /*3,*/ 8, 4, 5},
                {8, /*5,*/ 7, 6, 5}};
        assertThat(seamTestTable.getCumulativeEnergies(), is(correctCumulativeEnergies));
        seamTestTable.removeSeams(1);
        correctCumulativeEnergies = new int[][]{
                {4, 3, 5, /*2*/},
                {6, 8, /*4*/ 5},
                {11, 8, 6, /*5*/}};
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

    }

}
