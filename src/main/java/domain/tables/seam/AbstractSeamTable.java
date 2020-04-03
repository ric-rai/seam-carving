package domain.tables.seam;

import domain.Pixel;
import domain.tables.EnergyTable;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;

public abstract class AbstractSeamTable extends EnergyTable {
    Pixel[] lastPixels;

    public AbstractSeamTable(BufferedImage image) {
        super(image);
    }

    public abstract void computeSeams();

    abstract public void removeSeams(int numberOfSeams);

    abstract public void addSeams(int numberOfSeams);

    Pixel addToLastPixels(Pixel pixel, int i) {
        return lastPixels[i] = pixel;
    }

    void connect(Pixel predecessor, Pixel pixel) {
        pixel.cumulativeEnergy = pixel.energy + predecessor.cumulativeEnergy;
        pixel.prev = predecessor;
    }

    void sortLastPixelsByCumulativeEnergy() {
        Arrays.sort(lastPixels, Comparator.comparingInt(p -> p.cumulativeEnergy)); }

    public int[][] getCumulativeEnergyArray() {
        int[][] energies = new int[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                energies[row][col] = get(row, col).cumulativeEnergy;
        return energies;
    }

}
