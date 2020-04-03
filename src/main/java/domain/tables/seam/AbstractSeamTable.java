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

    abstract void computeSeams();

    abstract void removeSeam(Pixel pixel);

    void removeSeam() {
        removeSeam(lastPixels[0]);
        lastPixels[0] = null;
        sortLastPixelsByCumulativeEnergy();
    }

    abstract void addSeam();

    public void removeSeams(int numberOfSeams) {
        computeSeams();
        for (int i = 0; i < numberOfSeams; i++) removeSeam();
    }

    public void addSeams(int numberOfSeams) {
        computeSeams();
        for (int i = 0; i < numberOfSeams; i++) addSeam();
    }

    Pixel addToLastPixels(Pixel pixel, int i) {
        return lastPixels[i] = pixel;
    }

    void connect(Pixel predecessor, Pixel pixel, int adjacentIndex) {
        predecessor.adjacents[adjacentIndex] = pixel;
        pixel.cumulativeEnergy = pixel.energy + predecessor.cumulativeEnergy;
        pixel.prev = predecessor;
        if(predecessor.next == null || predecessor.next.cumulativeEnergy > pixel.cumulativeEnergy)
            predecessor.next = pixel;
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
