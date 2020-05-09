package domain.graphs.seam;

import domain.graphs.EnergyService;
import domain.graphs.Pixel;
import domain.graphs.PixelGraph;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class AbstractRecomputingSeamGraphTest {
    protected final int[][] fakeEnergies = new int[][]{
            {1, 4, 3, 5, 2},
            {3, 2, 5, 2, 3},
            {5, 2, 4, 2, 1}};
    protected final int width = 5;
    protected final int height = 3;
    protected PixelGraph pixelGraph;

    protected void initPixelGraph() {
        BufferedImage image = new BufferedImage(width, height, TYPE_INT_ARGB);
        pixelGraph = new PixelGraph(image);
        int row = 0, col = 0;
        Pixel pixel = pixelGraph.getLeftTop();
        while (true) {
            pixel.energy = fakeEnergies[row][col];
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
    }

    protected int[][] getCumulativeEnergies(PixelGraph pixelGraph) {
        int[][] cumulativeEnergies = new int[pixelGraph.getHeight()][pixelGraph.getWidth()];
        int row = 0, col = 0;
        Pixel pixel = pixelGraph.getLeftTop();
        while (true) {
            cumulativeEnergies[row][col] = pixel.cumulativeEnergy;
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
        return cumulativeEnergies;
    }

    protected static class SuppressedEnergyGraph implements EnergyService<Pixel> {

        @Override
        public void computeDualGradientEnergy(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForLeftTopCorner(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForTopRow(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForRightTopCorner(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForLeftmostCol(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForRightmostCol(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForLeftBottomCorner(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForBottomRow(Pixel pixel) {

        }

        @Override
        public void computeDualGradientEnergyForRightBottomCorner(Pixel pixel) {

        }
    }

}
