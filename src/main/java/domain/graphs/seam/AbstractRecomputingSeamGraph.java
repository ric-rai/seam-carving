package domain.graphs.seam;

import domain.graphs.EnergyService;
import domain.graphs.Pixel;
import domain.graphs.PixelGraph;

public abstract class AbstractRecomputingSeamGraph {
    final PixelGraph pixelGraph;
    final EnergyService<Pixel> energyGraph;
    protected Pixel lowestCumulativeEnergyPixel = null;
    private int lowestCumulativeEnergyValue = Integer.MAX_VALUE;

    public AbstractRecomputingSeamGraph(PixelGraph pixelGraph, EnergyService<Pixel> energyGraph) {
        this.pixelGraph = pixelGraph;
        this.energyGraph = energyGraph;
    }

    protected final void checkIfCumulativeEnergyIsLowest(Pixel pixel) {
        if (pixel.cumulativeEnergy < lowestCumulativeEnergyValue) {
            lowestCumulativeEnergyPixel = pixel;
            lowestCumulativeEnergyValue = pixel.cumulativeEnergy;
        }
    }

    public abstract void computeSeams();

    public final void removeSeams(int numberOfSeams) {
        for (int i = 0; i < numberOfSeams; i++) {
            computeSeams();
            removeSeam();
            lowestCumulativeEnergyValue = Integer.MAX_VALUE;
            lowestCumulativeEnergyPixel = null;
        }
    }

    protected abstract void removeSeam();

    protected void connect(Pixel pixel, Pixel lowest) {
        pixel.lowestPredecessor = lowest;
        pixel.cumulativeEnergy = pixel.energy + lowest.cumulativeEnergy;
    }

}
