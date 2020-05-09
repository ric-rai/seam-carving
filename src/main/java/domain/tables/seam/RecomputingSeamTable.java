package domain.tables.seam;

import domain.RgbService;
import domain.SeamCarver;
import domain.tables.EnergyTable;
import domain.tables.PixelTable;

public class RecomputingSeamTable implements SeamCarver {
    private final RecomputingVerticalSeamTable verticalSeamTable;
    private final RecomputingHorizontalSeamTable horizontalSeamTable;
    private final PixelTable<Integer[]> pixelTable;

    public RecomputingSeamTable(EnergyTable<Integer[]> energyTable,
                                int cumulativeEnergyIndex, int lowestPredecessorOffsetIndex) {
        pixelTable = energyTable.getPixelTable();
        this.verticalSeamTable =
                new RecomputingVerticalSeamTable(energyTable, cumulativeEnergyIndex, lowestPredecessorOffsetIndex);
        this.horizontalSeamTable =
                new RecomputingHorizontalSeamTable(energyTable, cumulativeEnergyIndex, lowestPredecessorOffsetIndex);
    }

    @Override
    public final void removeSeamsVertically(int numberOfSeams) {
        verticalSeamTable.removeSeams(numberOfSeams);
    }

    @Override
    public final void addSeamsVertically(int numberOfSeams) {
        verticalSeamTable.addSeams(numberOfSeams);
    }

    @Override
    public final void removeSeamsHorizontally(int numberOfSeams) {
        horizontalSeamTable.removeSeams(numberOfSeams);
    }

    @Override
    public final void addSeamsHorizontally(int numberOfSeams) {
        horizontalSeamTable.addSeams(numberOfSeams);
    }

    @Override
    public RgbService getRgbService() {
        return pixelTable;
    }
}
