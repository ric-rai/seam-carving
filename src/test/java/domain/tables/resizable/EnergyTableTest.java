package domain.tables.resizable;

import domain.tables.resizable.EnergyTable;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class EnergyTableTest {

    @Test
    public void computingDualGradientEnergiesWorksCorrectly() {
        int[][] rgbValues = {
                {255, 101, 51}, {255, 101, 153}, {255, 101, 255},
                {255, 153, 51}, {255, 153, 153}, {255, 153, 255},
                {255, 203, 51}, {255, 204, 153}, {255, 205, 255},
                {255, 255, 51}, {255, 255, 153}, {255, 255, 255}
        };
        int width = 3, height = 4;
        BufferedImage image = getImageFromRgbArray(rgbValues, 3, 4, TYPE_INT_ARGB);
        EnergyTestTable testTable = new EnergyTestTable(image);
        testTable.setRgbValues(rgbValues);
        //Top row
        testTable.computeDualGradientEnergyForLeftTopCorner();
        for (int col = 1; col < width - 1; col++)
            testTable.computeDualGradientEnergyForTopRow(col);
        testTable.computeDualGradientEnergyForRightTopCorner();
        //Middle rows
        for (int row = 1; row < height - 1; row++) {
            testTable.computeDualGradientEnergyForLeftmostCol(row);
            for (int col = 1; col < width - 1; col++)
                testTable.computeDualGradientEnergy(row, col);
            testTable.computeDualGradientEnergyForRightmostCol(row);
        }
        //Bottom row
        testTable.computeDualGradientEnergyForLeftBottomCorner();
        for (int col = 1; col < width - 1; col++)
            testTable.computeDualGradientEnergyForBottomRow(col);
        testTable.computeDualGradientEnergyForRightBottomCorner();
        int[][] correctEnergies = {
                {20808, 52020, 20808},
                {20808, 52225, 21220},
                {20809, 52024, 20809},
                {20808, 52225, 21220}
        };
        assertThat(testTable.getEnergies(), is(correctEnergies));
    }

    private static class EnergyTestTable extends EnergyTable {

        public EnergyTestTable(BufferedImage image) {
            super(image);
        }

        public int[][] getEnergies() {
            int[][] energies = new int[height][width];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    energies[row][col] = get(row, col)[energy];
                }
            }
            return energies;
        }

        public void setRgbValues(int[][] rgbValues) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width ;col++) {
                    int i = row * width + col;
                    set(row, col, new Integer[]{rgbValues[i][0], rgbValues[i][1], rgbValues[i][2], null});
                }
            }
        }

    }

}
