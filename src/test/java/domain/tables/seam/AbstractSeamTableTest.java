package domain.tables.seam;

import data.structures.ResizableTable;
import data.structures.Table;
import domain.tables.EnergyTable;
import domain.tables.PixelTable;

import static java.lang.Math.max;

public class AbstractSeamTableTest {
    protected final int[][] fakeEnergies = new int[][]{
            {1, 4, 3, 5, 2},
            {3, 2, 5, 2, 3},
            {5, 2, 4, 2, 1}};
    protected final int width = 5;
    protected final int height = 3;
    final int energy = 0; final int cumulativeEnergy = 1;
    final int lowestPredecessorOffset = 2;

    protected int[][] getCumulativeEnergies(Table<Integer[]> table) {
        int width = table.getWidth(), height = table.getHeight();
        int[][] cumulativeEnergies = new int[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                cumulativeEnergies[row][col] = table.get(row, col)[cumulativeEnergy];
        return cumulativeEnergies;
    }

    protected int[][] getEnergies(Table<Integer[]> table) {
        int width = table.getWidth(), height = table.getHeight();
        int[][] energies = new int[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                energies[row][col] = table.get(row, col)[energy];
        return energies;
    }

    protected class SuppressedEnergyTable implements EnergyTable<Integer[]> {
        private final ResizableTable<Integer[]> table;

        public SuppressedEnergyTable(ResizableTable<Integer[]> table) {
            this.table = table;
            for (int row = 0; row < table.getHeight(); row++) {
                for (int col = 0; col < table.getWidth(); col++) {
                    if (table.get(row, col) == null) {
                        Integer[] values = new Integer[max(energy, max(cumulativeEnergy, lowestPredecessorOffset)) + 1];
                        values[energy] = fakeEnergies[row][col];
                        table.set(row, col, values);
                    } else {
                        table.get(row, col)[energy] = fakeEnergies[row][col];
                    }
                }
            }
        }

        @Override
        public void computeDualGradientEnergy(int r, int c) {

        }

        @Override
        public void computeDualGradientEnergyForLeftTopCorner() {

        }

        @Override
        public void computeDualGradientEnergyForTopRow(int c) {

        }

        @Override
        public void computeDualGradientEnergyForRightTopCorner() {

        }

        @Override
        public void computeDualGradientEnergyForLeftmostCol(int r) {

        }

        @Override
        public void computeDualGradientEnergyForRightmostCol(int r) {

        }

        @Override
        public void computeDualGradientEnergyForLeftBottomCorner() {

        }

        @Override
        public void computeDualGradientEnergyForBottomRow(int c) {

        }

        @Override
        public void computeDualGradientEnergyForRightBottomCorner() {

        }

        @Override
        public PixelTable<Integer[]> getPixelTable() {
            return new SuppressedPixelTable(table);
        }

        @Override
        public int getEnergyIndex() {
            return 0;
        }

    }

    protected static class SuppressedPixelTable implements PixelTable<Integer[]> {
        private final ResizableTable<Integer[]> table;

        public SuppressedPixelTable(ResizableTable<Integer[]> table) {
            this.table = table;
        }

        @Override
        public Integer getRedIndex() {
            return null;
        }

        @Override
        public Integer getGreenIndex() {
            return null;
        }

        @Override
        public Integer getBlueIndex() {
            return null;
        }

        @Override
        public int getArgbInt(int row, int col) {
            return 0;
        }

        @Override
        public Table<Integer[]> getTable() {
            return table;
        }
    }


}