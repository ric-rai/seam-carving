package domain.tables.seam;

import domain.tables.EnergyTable;


public class RecomputingVerticalSeamTable extends AbstractRecomputingSeamTable {
    private int width, height;

    public RecomputingVerticalSeamTable(EnergyTable<Integer[]> energyTable, int cumulativeEnergyIndex, int lowestPredecessorOffsetIndex) {
        super(energyTable, cumulativeEnergyIndex, lowestPredecessorOffsetIndex);
    }

    @Override
    public void computeSeams() {
        width = table.getWidth();
        height = table.getHeight();
        computeFirstRow();
        computeRowsFromSecondToSecondLast();
        computeLastRow();
    }

    @Override
    protected void removeSeam() {
        table.removeVertically(traceLowestEnergySeamIndexes());
    }

    private int[] traceLowestEnergySeamIndexes() {
        int[] indexes = new int[height];
        indexes[height - 1] = lowestCumulativeEnergyIndex;
        for (int row = height - 2; row >= 0; row--) {
            int previousRow = row + 1;
            Integer[] previousPixel = table.get(previousRow, indexes[previousRow]);
            int col = indexes[previousRow] + previousPixel[lowestPredecessorOffset];
            indexes[row] = col;
        }
        return indexes;
    }

    @Override
    public void addSeams(int numberOfSeams) {
        int width = table.getWidth() + numberOfSeams;
        removeSeams(numberOfSeams);
        int[][] duplicatedColIndexes = new int[height][width];
        int[][] duplicatedRowIndexes = new int[height][width];
        int[][] colIndexes = table.getColIndexes();
        for (int row = 0; row < height; row++) {
            for (int col = 0, offset = 0; col < width; col++) {
                int offsetCol = col - 2 * offset;
                if (offsetCol < table.getWidth() && colIndexes[row][offsetCol] == col - offset) {
                    duplicatedColIndexes[row][col] = colIndexes[row][offsetCol];
                    duplicatedRowIndexes[row][col] = row;
                } else {
                    duplicatedColIndexes[row][col] = duplicatedColIndexes[row][col + 1] = col - offset;
                    duplicatedRowIndexes[row][col] = duplicatedRowIndexes[row][col + 1] = row;
                    offset++;
                    col++;
                }
            }
        }
        table.setRowIndexes(duplicatedRowIndexes);
        table.setColIndexes(duplicatedColIndexes);
        table.setWidth(width);
    }

    private void computeFirstRow() {
        energyTable.computeDualGradientEnergyForLeftTopCorner();
        table.get(0, 0)[cumulativeEnergy] = table.get(0, 0)[energy];
        for (int col = 1; col < width - 1; col++) {
            energyTable.computeDualGradientEnergyForTopRow(col);
            table.get(0, col)[cumulativeEnergy] = table.get(0, col)[energy];
        }
        energyTable.computeDualGradientEnergyForRightTopCorner();
        table.get(0, width - 1)[cumulativeEnergy] = table.get(0, width - 1)[energy];
    }

    private void computeRowsFromSecondToSecondLast() {
        for (int row = 1; row < height - 1; row++) {
            //Leftmost pixels
            energyTable.computeDualGradientEnergyForLeftmostCol(row);
            connect(row, 0, chooseLowestPredecessorAtLeftmostCol(row));
            //Middle pixels
            for (int col = 1; col < width - 1; col++) {
                energyTable.computeDualGradientEnergy(row, col);
                connect(row, col, chooseLowestPredecessor(row, col));
            }
            //Rightmost pixels
            energyTable.computeDualGradientEnergyForRightmostCol(row);
            connect(row, width - 1, chooseLowestPredecessorAtRightmostCol(row));
        }
    }

    private void computeLastRow() {
        int row = height - 1;
        //Left corner pixel
        energyTable.computeDualGradientEnergyForLeftBottomCorner();
        connect(row, 0, chooseLowestPredecessorAtLeftmostCol(row));
        checkIfCumulativeEnergyIsLowest(table.get(row, 0)[cumulativeEnergy], 0);
        //Middle pixels
        for (int col = 1; col < width - 1; col++) {
            energyTable.computeDualGradientEnergyForBottomRow(col);
            connect(row, col, chooseLowestPredecessor(row, col));
            checkIfCumulativeEnergyIsLowest(table.get(row, col)[cumulativeEnergy], col);
        }
        //Right corner pixel
        energyTable.computeDualGradientEnergyForRightBottomCorner();
        connect(row, width - 1, chooseLowestPredecessorAtRightmostCol(row));
        checkIfCumulativeEnergyIsLowest(table.get(row, width - 1)[cumulativeEnergy], width - 1);
    }

    private void connect(int row, int col, int offset) {
        table.get(row, col)[lowestPredecessorOffset] = offset;
        table.get(row, col)[cumulativeEnergy] =
                table.get(row, col)[energy] + table.get(row - 1, col + offset)[cumulativeEnergy];
    }

    private int chooseLowestPredecessorAtLeftmostCol(int row) {
        row--;
        int offset = 0;
        if (table.get(row, 1)[cumulativeEnergy] < table.get(row, 0)[cumulativeEnergy]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessor(int row, int col) {
        row--;
        int offset = 0;
        if (table.get(row, col - 1)[cumulativeEnergy] < table.get(row, col)[cumulativeEnergy]) offset = -1;
        if (table.get(row, col + 1)[cumulativeEnergy] < table.get(row, col + offset)[cumulativeEnergy]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessorAtRightmostCol(int row) {
        row--;
        int offset = 0;
        int col = width - 1;
        if (table.get(row, col - 1)[cumulativeEnergy] < table.get(row, col)[cumulativeEnergy]) offset = -1;
        return offset;
    }

}
