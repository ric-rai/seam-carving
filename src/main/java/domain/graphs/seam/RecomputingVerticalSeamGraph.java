package domain.graphs.seam;

import domain.graphs.EnergyService;
import domain.graphs.Pixel;
import domain.graphs.PixelGraph;

public class RecomputingVerticalSeamGraph extends AbstractRecomputingSeamGraph {

    public RecomputingVerticalSeamGraph(PixelGraph pixelGraph, EnergyService<Pixel> energyGraph) {
        super(pixelGraph, energyGraph);
    }

    @Override
    public void computeSeams() {
        computeLastRow(computeRowsFromSecondToSecondLast(computeFirstRow()));
    }

    @Override
    protected void removeSeam() {
        Pixel pixel = lowestCumulativeEnergyPixel;
        pixelGraph.removeVertically(pixel);
    }

    private Pixel computeFirstRow() {
        //Leftmost on first row
        Pixel pixel = pixelGraph.getLeftTop();
        energyGraph.computeDualGradientEnergyForLeftTopCorner(pixel);
        pixel.cumulativeEnergy = pixel.energy;
        pixel = pixel.right;
        //Middle pixels on first Row
        while (pixel.right != null) {
            energyGraph.computeDualGradientEnergyForTopRow(pixel);
            pixel.cumulativeEnergy = pixel.energy;
            pixel = pixel.right;
        }
        //Rightmost on first row
        energyGraph.computeDualGradientEnergyForRightTopCorner(pixel);
        pixel.cumulativeEnergy = pixel.energy;
        pixel = pixel.below.leftmostOnRow;
        return pixel;
    }

    private Pixel computeRowsFromSecondToSecondLast(Pixel pixel) {
        while (pixel.below != null) {
            //Leftmost
            energyGraph.computeDualGradientEnergyForLeftmostCol(pixel);
            connect(pixel, chooseLowestPredecessorAtLeftmostCol(pixel));
            pixel = pixel.right;
            //Middle pixels
            while (pixel.right != null) {
                energyGraph.computeDualGradientEnergy(pixel);
                connect(pixel, chooseLowestPredecessor(pixel));
                pixel = pixel.right;
            }
            //Rightmost
            energyGraph.computeDualGradientEnergyForRightmostCol(pixel);
            connect(pixel, chooseLowestPredecessorAtRightmostCol(pixel));
            pixel = pixel.below.leftmostOnRow;
        }
        return pixel;
    }

    private void computeLastRow(Pixel pixel) {
        //Leftmost on last row
        energyGraph.computeDualGradientEnergyForLeftBottomCorner(pixel);
        connect(pixel, chooseLowestPredecessorAtLeftmostCol(pixel));
        checkIfCumulativeEnergyIsLowest(pixel);
        pixel = pixel.right;
        //Middle pixels on last row
        while (pixel.right != null) {
            energyGraph.computeDualGradientEnergyForBottomRow(pixel);
            connect(pixel, chooseLowestPredecessor(pixel));
            checkIfCumulativeEnergyIsLowest(pixel);
            pixel = pixel.right;
        }
        //Rightmost on last row
        energyGraph.computeDualGradientEnergyForRightBottomCorner(pixel);
        connect(pixel, chooseLowestPredecessorAtRightmostCol(pixel));
        checkIfCumulativeEnergyIsLowest(pixel);
    }

    private Pixel chooseLowestPredecessorAtLeftmostCol(Pixel pixel) {
        Pixel above = pixel.above;
        return above.right.cumulativeEnergy < above.cumulativeEnergy ? above.right : above;
    }

    private Pixel chooseLowestPredecessor(Pixel pixel) {
        Pixel above = pixel.above;
        Pixel lowest = above.left.cumulativeEnergy < above.cumulativeEnergy ? above.left : above;
        return above.right.cumulativeEnergy < lowest.cumulativeEnergy ? above.right : lowest;
    }

    private Pixel chooseLowestPredecessorAtRightmostCol(Pixel pixel) {
        Pixel above = pixel.above;
        return above.left.cumulativeEnergy < above.cumulativeEnergy ? above.left : above;
    }


}
