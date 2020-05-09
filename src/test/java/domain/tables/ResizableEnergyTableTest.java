package domain.tables;

import data.structures.ResizableArrayTable;
import data.structures.ResizableTable;
import data.structures.Table;
import domain.tables.PixelTable;
import domain.tables.ResizableEnergyTable;
import domain.tables.ResizablePixelTable;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class ResizableEnergyTableTest {
    final int red = 0;
    final int green = 1;
    final int blue = 2;
    final int energyIndex = 3;
    final int[][] rgbValues = {
            {255, 101, 51}, {255, 101, 153}, {255, 101, 255},
            {255, 153, 51}, {255, 153, 153}, {255, 153, 255},
            {255, 203, 51}, {255, 204, 153}, {255, 205, 255},
            {255, 255, 51}, {255, 255, 153}, {255, 255, 255}
    };
    final int width = 3;
    final int height = 4;

    @Test
    public void computingDualGradientEnergiesWorksCorrectly() {
        BufferedImage image = getImageFromRgbArray(rgbValues, width, height, TYPE_INT_ARGB);
        ResizableTable<Integer[]> resizableTable = new ResizableArrayTable<>(Integer[].class, width, height);
        PixelTable<Integer[]> pixelTable = new ResizablePixelTable(resizableTable, image, energyIndex + 1, red, green, blue);
        ResizableEnergyTable testTable = new ResizableEnergyTable(pixelTable, energyIndex);
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
        assertThat(getEnergies(resizableTable), is(correctEnergies));
    }

    public int[][] getEnergies(Table<Integer[]> table) {
        int[][] energies = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                energies[row][col] = table.get(row, col)[energyIndex];
            }
        }
        return energies;
    }

}
