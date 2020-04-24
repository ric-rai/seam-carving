package domain.tables.resizable.seam;

import java.awt.image.BufferedImage;


public class RecomputingVerticalSeamTable extends AbstractSeamTable {

    public RecomputingVerticalSeamTable(BufferedImage image) {
        super(image);
        setRgbValuesFromImage();
    }

    @Override
    public void computeSeams() {
        computeFirstRow();
        computeRowsFromSecondToSecondLast();
        computeLastRow();
    }

    @Override
    protected void removeSeam() {
        int[] indexes = new int[height];
        indexes[height - 1] = lowestCumulativeEnergyIndex;
        for (int row = height - 2; row >= 0; row--) {
            int previousRow = row + 1;
            Integer[] previousPixel = get(previousRow, indexes[previousRow]);
            int col = indexes[previousRow] + previousPixel[lowestPredecessorOffset];
            indexes[row] = col;
        }
        removeVertically(indexes);
    }

    private void computeFirstRow() {
        computeDualGradientEnergyForLeftTopCorner();
        get(0, 0)[cumulativeEnergy] = get(0, 0)[energy];
        for (int col = 1; col < width - 1; col++) {
            computeDualGradientEnergyForTopRow(col);
            get(0, col)[cumulativeEnergy] = get(0, col)[energy];
        }
        computeDualGradientEnergyForRightTopCorner();
        get(0, width - 1)[cumulativeEnergy] = get(0, width - 1)[energy];
    }

    private void computeRowsFromSecondToSecondLast() {
        for (int row = 1; row < height - 1; row++) {
            //Leftmost pixels
            computeDualGradientEnergyForLeftmostCol(row);
            connect(row, 0, chooseLowestPredecessorAtLeftmostCol(row));
            //Middle pixels
            for (int col = 1; col < width - 1; col++) {
                computeDualGradientEnergy(row, col);
                connect(row, col, chooseLowestPredecessor(row, col));
            }
            //Rightmost pixels
            computeDualGradientEnergyForRightmostCol(row);
            connect(row, width - 1, chooseLowestPredecessorAtRightmostCol(row));
        }
    }

    private void computeLastRow() {
        int row = height - 1;
        //Left corner pixel
        computeDualGradientEnergyForLeftBottomCorner();
        connect(row, 0, chooseLowestPredecessorAtLeftmostCol(row));
        checkIfCumulativeEnergyIsLowest(get(row, 0)[cumulativeEnergy], 0);
        //Middle pixels
        for (int col = 1; col < width - 1; col++) {
            computeDualGradientEnergyForBottomRow(col);
            connect(row, col, chooseLowestPredecessor(row, col));
            checkIfCumulativeEnergyIsLowest(get(row, col)[cumulativeEnergy], col);
        }
        //Right corner pixel
        computeDualGradientEnergyForRightBottomCorner();
        connect(row, width - 1, chooseLowestPredecessorAtRightmostCol(row));
        checkIfCumulativeEnergyIsLowest(get(row, width - 1)[cumulativeEnergy], width - 1);
    }

    private void connect(int row, int col, int offset) {
        get(row, col)[lowestPredecessorOffset] = offset;
        get(row, col)[cumulativeEnergy] = get(row, col)[energy] + get(row - 1, col + offset)[cumulativeEnergy];
    }

    private int chooseLowestPredecessorAtLeftmostCol(int row) {
        row = row - 1;
        int offset = 0;
        if (get(row, 1)[cumulativeEnergy] < get(row, 0)[cumulativeEnergy]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessor(int row, int col) {
        row = row - 1;
        int offset = 0;
        if (get(row, col - 1)[cumulativeEnergy] < get(row, col)[cumulativeEnergy]) offset = -1;
        if (get(row, col + 1)[cumulativeEnergy] < get(row, col + offset)[cumulativeEnergy]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessorAtRightmostCol(int row) {
        row = row - 1;
        int col = width - 1;
        int offset = 0;
        if (get(row, col - 1)[cumulativeEnergy] < get(row, col)[cumulativeEnergy]) offset = -1;
        return offset;
    }

}
