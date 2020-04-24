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
        if (cumulativeEnergy < lowestCumulativeEnergyValue) {
            lowestCumulativeEnergyIndex = index;
            lowestCumulativeEnergyValue = cumulativeEnergy;
        }
    }

    public abstract void computeSeams();

    public void removeSeams(int numberOfSeams) {
        for (int i = 0; i < numberOfSeams; i++) {
            computeSeams();
            removeSeam();
            lowestCumulativeEnergyValue = Integer.MAX_VALUE;
            lowestCumulativeEnergyIndex = null;
        }
    }

    //abstract public void addSeams(int numberOfSeams);

    abstract protected void removeSeam();

    //abstract protected void addSeam();

}