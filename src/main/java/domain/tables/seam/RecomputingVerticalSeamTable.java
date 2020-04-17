package domain.tables.seam;

import domain.Pixel;

import java.awt.image.BufferedImage;


public class RecomputingVerticalSeamTable extends AbstractSeamTable {

    public RecomputingVerticalSeamTable(BufferedImage image) {
        super(image);
    }

    @Override
    public void computeSeams() {
        computeFirstRow();
        computeRowsFromSecondToSecondLast();
        computeLastRow();
    }

    @Override
    public void removeSeams(int numberOfSeams) {
        computeSeams();
        for (int i = 0; i < numberOfSeams; i++) {
            removeSeam();
        }
    }

    @Override
    public void addSeams(int numberOfSeams) {
    }

    void removeSeam() {
        Pixel pixel = lastPixels[0];
        for (int i = 0; i < height; i++) {
            //System.out.println("row: " + pixel.row + " - col: " + pixel.col);
            table[pixel.row][pixel.col] = null;
            pixel = pixel.prev;
        }
        resetTable();
    }

    void resetTable() {
        Pixel[][] newTable = new Pixel[height][width - 1];
        for (int row = 0; row < height; row++) {
            int offset = 0;
            for (int col = 0; col < width - 1; col++) {
                if (offset == 0 && table[row][col] == null) offset = 1;
                Pixel pixel = table[row][col + offset];
                newTable[row][col] = pixel;
                pixel.row = row;
                pixel.col = col;
            }
        }
        table = newTable;
        width -= 1;
    }

    private void computeFirstRow() {
        rgbValues.set(0, 0);
        computeDualGradientEnergyForLeftTopCorner();
        cumulativeEnergies[0][0] = energies[0][0];
        for (int col = 1; col < width - 1; col++) {
            rgbValues.set(0, col);
            computeDualGradientEnergyForTopRow(col);
            cumulativeEnergies[0][col] = energies[0][col];
        }
        rgbValues.set(0, width - 1);
        computeDualGradientEnergyForRightTopCorner();
        cumulativeEnergies[0][width - 1] = energies[0][width - 1];
    }

    private void computeRowsFromSecondToSecondLast() {
        for (int row = 1; row < height - 1; row++) {
            //Leftmost pixels
            rgbValues.set(row, 0);
            computeDualGradientEnergyForLeftmostCol(row);
            connect(row, 0, chooseLowestPredecessorAtLeftmostCol(row));
            //Middle pixels
            for (int col = 1; col < width - 1; col++) {
                rgbValues.set(row, col);
                computeDualGradientEnergy(row, col);
                connect(row, col, chooseLowestPredecessor(row, col));
            }
            //Rightmost pixels
            rgbValues.set(row, width - 1);
            computeDualGradientEnergyForRightmostCol(row);
            connect(row, width - 1, chooseLowestPredecessorAtRightmostCol(row));
        }
    }

    private void computeLastRow() {
        int row = height - 1;
        //Left corner pixel
        rgbValues.set(row, 0);
        computeDualGradientEnergyForLeftBottomCorner();
        connect(row, 0, chooseLowestPredecessorAtLeftmostCol(row));
        checkIfCumulativeEnergyIsLowest(cumulativeEnergies[row][0], 0);
        //Middle pixels
        for (int col = 1; col < width - 1; col++) {
            rgbValues.set(row, col);
            computeDualGradientEnergyForBottomRow(col);
            connect(row, col, chooseLowestPredecessor(row, col));
            checkIfCumulativeEnergyIsLowest(cumulativeEnergies[row][col], col);
        }
        //Right corner pixel
        rgbValues.set(row, width - 1);
        computeDualGradientEnergyForRightBottomCorner();
        connect(row, width - 1, chooseLowestPredecessorAtRightmostCol(row));
        checkIfCumulativeEnergyIsLowest(cumulativeEnergies[row][width - 1], width - 1);
    }

    void connect(int row, int col, int offset) {
        lowestPredecessorOffsets[row][col] = offset;
        cumulativeEnergies[row][col] = energies[row][col] + energies[row + offset][col];
    }

    private int chooseLowestPredecessorAtLeftmostCol(int row) {
        row = row - 1;
        int offset = 0;
        if (cumulativeEnergies[row][1] < cumulativeEnergies[row][0]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessor(int row, int col) {
        row = row - 1;
        int offset = 0;
        if (cumulativeEnergies[row][col - 1] < cumulativeEnergies[row][col]) offset = -1;
        if (cumulativeEnergies[row][col + 1] < cumulativeEnergies[row][col + offset]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessorAtRightmostCol(int row) {
        row = row - 1;
        int col = width - 1;
        int offset = 0;
        if (cumulativeEnergies[row][col - 1] < cumulativeEnergies[row][col]) offset = -1;
        return offset;
    }

}
