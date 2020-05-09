package domain.tables.seam;

import data.structures.ResizableTable;
import domain.tables.EnergyTable;
import domain.tables.PixelTable;

public abstract class AbstractRecomputingSeamTable {
    protected final ResizableTable<Integer[]> table;
    protected final PixelTable<Integer[]> pixelTable;
    protected final EnergyTable<Integer[]> energyTable;
    protected final int energy;
    protected final int cumulativeEnergy;
    protected final int lowestPredecessorOffset;
    protected Integer lowestCumulativeEnergyIndex = null;
    private int lowestCumulativeEnergyValue = Integer.MAX_VALUE;

    public AbstractRecomputingSeamTable(EnergyTable<Integer[]> energyTable,
                                        int cumulativeEnergyIndex, int lowestPredecessorOffsetIndex) {
        this.energyTable = energyTable;
        pixelTable = energyTable.getPixelTable();
        table = (ResizableTable<Integer[]>) pixelTable.getTable();
        energy = energyTable.getEnergyIndex();
        cumulativeEnergy = cumulativeEnergyIndex;
        lowestPredecessorOffset = lowestPredecessorOffsetIndex;
    }


    protected final void checkIfCumulativeEnergyIsLowest(int cumulativeEnergy, int index) {
        if (cumulativeEnergy < lowestCumulativeEnergyValue) {
            lowestCumulativeEnergyIndex = index;
            lowestCumulativeEnergyValue = cumulativeEnergy;
        }
    }

    public abstract void computeSeams();

    public final void removeSeams(int numberOfSeams) {
        for (int i = 0; i < numberOfSeams; i++) {
            computeSeams();
            removeSeam();
            lowestCumulativeEnergyValue = Integer.MAX_VALUE;
            lowestCumulativeEnergyIndex = null;
        }
    }

    protected abstract void removeSeam();

    @SuppressWarnings("unused")
    public abstract void addSeams(int numberOfSeams);

}