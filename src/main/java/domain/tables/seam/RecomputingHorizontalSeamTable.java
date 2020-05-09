package domain.tables.seam;

import domain.tables.EnergyTable;

public class RecomputingHorizontalSeamTable extends AbstractRecomputingSeamTable {
    private int width, height;

    public RecomputingHorizontalSeamTable(EnergyTable<Integer[]> energyTable,
                                          int cumulativeEnergyIndex, int lowestPredecessorOffsetIndex) {
        super(energyTable, cumulativeEnergyIndex, lowestPredecessorOffsetIndex);
    }

    @Override
    public void computeSeams() {
        width = table.getWidth();
        height = table.getHeight();
        computeFirstCol();
        computeColsFromSecondToSecondLast();
        computeLastCol();
    }

    @Override
    protected void removeSeam() {
        table.removeHorizontally(traceLowestEnergySeamIndexes());
    }

    private int[] traceLowestEnergySeamIndexes() {
        int[] indexes = new int[width];
        indexes[width - 1] = lowestCumulativeEnergyIndex;
        for (int col = width - 2; col >= 0; col--) {
            int previousCol = col + 1;
            Integer[] previousPixel = table.get(indexes[previousCol], previousCol);
            int row = indexes[previousCol] + previousPixel[lowestPredecessorOffset];
            indexes[col] = row;
        }
        return indexes;
    }

    @Override
    public void addSeams(int numberOfSeams) {
        int height = table.getHeight() + numberOfSeams;
        removeSeams(numberOfSeams);
        // Table indexes are now transposed
        int[][] duplicatedColIndexes = new int[width][height];
        int[][] duplicatedRowIndexes = new int[width][height];
        int[][] rowIndexes = table.getRowIndexes();
        for (int row = 0; row < width; row++) {
            for (int col = 0, offset = 0; col < height; col++) {
                int offsetCol = col - 2 * offset;
                if (offsetCol < table.getHeight() && rowIndexes[row][offsetCol] == col - offset) {
                    duplicatedColIndexes[row][col] = row;
                    duplicatedRowIndexes[row][col] = rowIndexes[row][offsetCol];
                } else {
                    duplicatedColIndexes[row][col] = duplicatedColIndexes[row][col + 1] = row;
                    duplicatedRowIndexes[row][col] = duplicatedRowIndexes[row][col + 1] = col - offset;
                    offset++;
                    col++;
                }
            }
        }
        table.setRowIndexes(duplicatedRowIndexes);
        table.setColIndexes(duplicatedColIndexes);
        table.setHeight(height);
    }

    private void computeFirstCol() {
        energyTable.computeDualGradientEnergyForLeftTopCorner();
        table.get(0, 0)[cumulativeEnergy] = table.get(0, 0)[energy];
        for (int row = 1; row < height - 1; row++) {
            energyTable.computeDualGradientEnergyForLeftmostCol(row);
            table.get(row, 0)[cumulativeEnergy] = table.get(row, 0)[energy];
        }
        energyTable.computeDualGradientEnergyForLeftBottomCorner();
        table.get(height - 1, 0)[cumulativeEnergy] = table.get(height - 1, 0)[energy];
    }

    private void computeColsFromSecondToSecondLast() {
        for (int col = 1; col < width - 1; col++) {
            //Top pixels
            energyTable.computeDualGradientEnergyForTopRow(col);
            connect(0, col, chooseLowestPredecessorAtTopRow(col));
            //Middle pixels
            for (int row = 1; row < height - 1; row++) {
                energyTable.computeDualGradientEnergy(row, col);
                connect(row, col, chooseLowestPredecessor(row, col));
            }
            //Bottom pixels
            energyTable.computeDualGradientEnergyForBottomRow(col);
            connect(height - 1, col, chooseLowestPredecessorAtBottomRow(col));
        }
    }

    private void computeLastCol() {
        int col = width - 1;
        //Top corner pixel
        energyTable.computeDualGradientEnergyForRightTopCorner();
        connect(0, col, chooseLowestPredecessorAtTopRow(col));
        checkIfCumulativeEnergyIsLowest(table.get(0, col)[cumulativeEnergy], 0);
        //Middle pixels
        for (int row = 1; row < height - 1; row++) {
            energyTable.computeDualGradientEnergyForRightmostCol(row);
            connect(row, col, chooseLowestPredecessor(row, col));
            checkIfCumulativeEnergyIsLowest(table.get(row, col)[cumulativeEnergy], row);
        }
        //Bottom corner pixel
        energyTable.computeDualGradientEnergyForRightBottomCorner();
        connect(height - 1, col, chooseLowestPredecessorAtBottomRow(col));
        checkIfCumulativeEnergyIsLowest(table.get(height - 1, col)[cumulativeEnergy], height - 1);
    }

    private void connect(int row, int col, int offset) {
        table.get(row, col)[lowestPredecessorOffset] = offset;
        table.get(row, col)[cumulativeEnergy] =
                table.get(row, col)[energy] + table.get(row + offset, col - 1)[cumulativeEnergy];
    }

    private int chooseLowestPredecessorAtTopRow(int col) {
        col--;
        int offset = 0;
        if (table.get(1, col)[cumulativeEnergy] < table.get(0, col)[cumulativeEnergy]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessor(int row, int col) {
        col--;
        int offset = 0;
        if (table.get(row - 1, col)[cumulativeEnergy] < table.get(row, col)[cumulativeEnergy]) offset = -1;
        if (table.get(row + 1, col)[cumulativeEnergy] < table.get(row + offset, col)[cumulativeEnergy]) offset = 1;
        return offset;
    }

    private int chooseLowestPredecessorAtBottomRow(int col) {
        col--;
        int offset = 0;
        int row = height - 1;
        if (table.get(row - 1, col)[cumulativeEnergy] < table.get(row, col)[cumulativeEnergy]) offset = -1;
        return offset;
    }

}
