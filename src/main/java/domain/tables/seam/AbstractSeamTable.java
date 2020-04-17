package domain.tables.seam;

import domain.tables.EnergyTable;

import java.awt.image.BufferedImage;

public abstract class AbstractSeamTable extends EnergyTable {
    protected boolean[][] isRemoved;
    protected int[][][] adjacentOffsets;
    protected int[][][] predecessorOffsets;
    protected int[][] cumulativeEnergies;
    protected int[][] lowestPredecessorOffsets;
    protected Integer lowestCumulativeEnergyIndex = null;
    protected int lowestCumulativeEnergyValue = Integer.MAX_VALUE;

    public AbstractSeamTable(BufferedImage image) {
        super(image);
        cumulativeEnergies = new int[height][width];
        lowestPredecessorOffsets = new int[height][width];
        initializeAdjacentAndPredecessorOffset();
    }

    private void initializeAdjacentAndPredecessorOffset() {
        adjacentOffsets = new int[height][width][3];
        predecessorOffsets = new int[height][width][3];
        int[][] initialOffsetsRow = new int[width][3];
        initialOffsetsRow[0] = new int[]{-1, 0, 1};
        for (int i = 1; i < width; i += i)
            System.arraycopy(initialOffsetsRow, 0, initialOffsetsRow, i, Math.min((width - i), i));
        System.arraycopy(initialOffsetsRow, 0, adjacentOffsets[0], 0, width);
        for (int i = 1; i < width; i += i)
            System.arraycopy(adjacentOffsets, 0, adjacentOffsets, i, Math.min((width - i), i));
        System.arraycopy(adjacentOffsets, 0, predecessorOffsets, 0, height);
    }

    final protected void checkIfCumulativeEnergyIsLowest(int cumulativeEnergy, int index) {
        if (cumulativeEnergy < lowestCumulativeEnergyValue)
            lowestCumulativeEnergyIndex = index;
    }

    public abstract void computeSeams();

    abstract public void removeSeams(int numberOfSeams);

    abstract public void addSeams(int numberOfSeams);

}
