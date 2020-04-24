package domain.tables.resizable.seam;

import domain.tables.resizable.EnergyTable;

import java.awt.image.BufferedImage;

public abstract class AbstractSeamTable extends EnergyTable {
    protected final int cumulativeEnergy;
    protected final int lowestPredecessorOffset;
    protected Integer lowestCumulativeEnergyIndex = null;
    protected int lowestCumulativeEnergyValue = Integer.MAX_VALUE;

    public AbstractSeamTable(BufferedImage image) {
        super(image);
        cumulativeEnergy = dataFieldCount++;
        lowestPredecessorOffset = dataFieldCount++;
    }

    final protected void checkIfCumulativeEnergyIsLowest(int cumulativeEnergy, int index) {
        if (cumulativeEnergy < lowestCumulativeEnergyValue)
            lowestCumulativeEnergyIndex = index;
    }

    public abstract void computeSeams();

    abstract public void removeSeams(int numberOfSeams);

    abstract public void addSeams(int numberOfSeams);

}