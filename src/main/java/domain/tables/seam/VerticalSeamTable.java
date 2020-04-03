package domain.tables.seam;

import domain.Pixel;

import java.awt.image.BufferedImage;


public class VerticalSeamTable extends AbstractSeamTable {

    public VerticalSeamTable(BufferedImage image) {
        super(image);
    }

    @Override
    protected void computeSeams() {
        lastPixels = new Pixel[width];
        computeFirstRow();
        computeRowsFromSecondToSecondLast();
        computeLastRow();
        sortLastPixelsByCumulativeEnergy();
    }

    @Override
    protected void removeSeam(Pixel pixel) {
    }

    @Override
    protected void addSeam() {
    }

    private void computeFirstRow() {
        for (int col = 0; col < width; col++)
            table[0][col].cumulativeEnergy = table[0][col].energy;
    }

    private void computeRowsFromSecondToSecondLast() {
        for (int row = 1; row < height - 1; row++)
            for (int col = 0; col < width; col++)
                connect(chooseLowestPredecessor(row, col), table[row][col]);
    }

    private void computeLastRow() {
        for (int col = 0; col < width; col++)
            connect(chooseLowestPredecessor(height - 1, col),
                    addToLastPixels(table[height - 1][col], col));
    }

    protected void connect(Pixel predecessor, Pixel pixel) {
        super.connect(predecessor, pixel, pixel.col - predecessor.col + 1);
    }

    private Pixel chooseLowestPredecessor(int row, int col) {
        row = row - 1;
        int offset = 0;
        if (col == 0) {
            if (table[row][col + 1].cumulativeEnergy < table[row][col].cumulativeEnergy) offset = 1;
        } else if (col == width - 1) {
            if (table[row][col - 1].cumulativeEnergy < table[row][col].cumulativeEnergy) offset = -1;
        } else {
            if (table[row][col - 1].cumulativeEnergy < table[row][col].cumulativeEnergy) offset = -1;
            if (table[row][col + 1].cumulativeEnergy < table[row][col + offset].cumulativeEnergy) offset = 1;
        }
        return table[row][col + offset];
    }

}
