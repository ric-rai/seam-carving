package domain.graphs;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class EnergyGraphTest {
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
        PixelGraph pixelGraph = new PixelGraph(image);
        EnergyGraph energyGraph = new EnergyGraph();
        //Leftmost on first row
        Pixel pixel = pixelGraph.getLeftTop();
        energyGraph.computeDualGradientEnergyForLeftTopCorner(pixel);
        pixel = pixel.right;
        //Middle pixels on first Row
        while (pixel.right != null) {
            energyGraph.computeDualGradientEnergyForTopRow(pixel);
            pixel = pixel.right;
        }
        //Rightmost on first row
        energyGraph.computeDualGradientEnergyForRightTopCorner(pixel);
        pixel = pixel.below.leftmostOnRow;
        //Middle rows
        while (pixel.below != null) {
            //Leftmost
            energyGraph.computeDualGradientEnergyForLeftmostCol(pixel);
            pixel = pixel.right;
            //Middle pixels
            while (pixel.right != null) {
                energyGraph.computeDualGradientEnergy(pixel);
                pixel = pixel.right;
            }
            //Rightmost
            energyGraph.computeDualGradientEnergyForRightmostCol(pixel);
            pixel = pixel.below.leftmostOnRow;
        }
        //Leftmost on last row
        energyGraph.computeDualGradientEnergyForLeftBottomCorner(pixel);
        pixel = pixel.right;
        //Middle pixels on last row
        while (pixel.right != null) {
            energyGraph.computeDualGradientEnergyForBottomRow(pixel);
            pixel = pixel.right;
        }
        //Rightmost on last row
        energyGraph.computeDualGradientEnergyForRightBottomCorner(pixel);

        int[][] correctEnergies = {
                {20808, 52020, 20808},
                {20808, 52225, 21220},
                {20809, 52024, 20809},
                {20808, 52225, 21220}
        };
        assertThat(getEnergies(pixelGraph), is(correctEnergies));
    }

    private int[][] getEnergies(PixelGraph pixelGraph) {
        int[][] energies = new int[height][width];
        int row = 0, col = 0;
        Pixel pixel = pixelGraph.getLeftTop();
        while (true) {
            energies[row][col] = pixel.energy;
            boolean lastOnRow = pixel.right == null;
            if (lastOnRow && pixel.below == null) break;
            if (!lastOnRow) {
                pixel = pixel.right;
                col++;
            } else {
                pixel = pixel.below.leftmostOnRow;
                row++;
                col = 0;
            }
        }
        return energies;
    }

}
