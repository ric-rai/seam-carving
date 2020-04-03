package domain.tables.seam;

import domain.Pixel;

import java.awt.image.BufferedImage;


public class HorizontalSeamTable extends AbstractSeamTable {

    public HorizontalSeamTable(BufferedImage image) {
        super(image);
    }

    @Override
    protected void computeSeams() {
        lastPixels = new Pixel[height];
        computeFirstColumn();
        computeColumnsFromSecondToSecondLast();
        computeLastColumn();
        sortLastPixelsByCumulativeEnergy();
    }

    @Override
    protected void removeSeam(Pixel pixel) {
    }

    @Override
    protected void addSeam() {
    }

    private void computeFirstColumn() {
        for (int row = 0; row < height; row++)
            table[row][0].cumulativeEnergy = table[row][0].energy;
    }

    private void computeColumnsFromSecondToSecondLast() {
        for (int col = 1; col < width - 1; col++)
            for (int row = 0; row < height; row++)
                connect(chooseLowestPredecessor(row, col), table[row][col]);
    }

    private void computeLastColumn() {
        for (int row = 0; row < height; row++)
            connect(chooseLowestPredecessor(row, width - 1),
                    addToLastPixels(table[row][width - 1], row));
    }

    protected void connect(Pixel predecessor, Pixel pixel) {
        super.connect(predecessor, pixel, pixel.row - predecessor.row + 1);
    }

    private Pixel chooseLowestPredecessor(int row, int col) {
        col = col - 1;
        int offset = 0;
        if (row == 0) {
            if (table[row + 1][col].cumulativeEnergy < table[row][col].cumulativeEnergy) offset = 1;
        } else if (row == height - 1) {
            if (table[row - 1][col].cumulativeEnergy < table[row][col].cumulativeEnergy) offset = -1;
        } else {
            if (table[row - 1][col].cumulativeEnergy < table[row][col].cumulativeEnergy) offset = -1;
            if (table[row + 1][col].cumulativeEnergy < table[row + offset][col].cumulativeEnergy) offset = 1;
        }
        return table[row + offset][col];
    }

}
