package domain.tables.seam;

import domain.Pixel;

import java.awt.image.BufferedImage;


public class RecomputingVerticalSeamTable extends AbstractSeamTable {

    public RecomputingVerticalSeamTable(BufferedImage image) {
        super(image);
    }

    @Override
    public void computeSeams() {
        lastPixels = new Pixel[width];
        computeFirstRow();
        computeRowsFromSecondToSecondLast();
        computeLastRow();
        sortLastPixelsByCumulativeEnergy();
    }

    @Override
    public void removeSeams(int numberOfSeams) {
        for (int i = 0; i < numberOfSeams; i++) {
            computeSeams();
            removeSeam();
            computeDualGradientEnergies();
        }
    }

    @Override
    public void addSeams(int numberOfSeams) {
    }

    void removeSeam() {
        Pixel pixel = lastPixels[0];
        for (int i = 0; i < height; i++) {
            //System.out.println("row: " + pixel.row + " - col: " + pixel.col);
            table[pixel.row][pixel.col] = null;
            pixel = pixel.prev;
        }
        resetTable();
    }

    void resetTable() {
        Pixel[][] newTable = new Pixel[height][width - 1];
        for (int row = 0; row < height; row++) {
            int offset = 0;
            for (int col = 0; col < width - 1; col++) {
                if (offset == 0 && table[row][col] == null) offset = 1;
                Pixel pixel = table[row][col + offset];
                newTable[row][col] = pixel;
                pixel.row = row;
                pixel.col = col;
            }
        }
        table = newTable;
        width -= 1;
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
        super.connect(predecessor, pixel);
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
