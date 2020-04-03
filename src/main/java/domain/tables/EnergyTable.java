package domain.tables;


import java.awt.image.BufferedImage;

import static java.lang.Math.abs;

public class EnergyTable extends PixelTable {

    public EnergyTable(BufferedImage image) {
        super(image);
    }

    public void computeDualGradientEnergies() {
        for (int r = 0; r < height; r++)
            for (int c = 0; c < width; c++)
                get(r, c).energy = R_x(r, c) + G_x(r, c) + B_x(r, c) + R_y(r, c) + G_y(r, c) + B_y(r, c);
    }

    private int R_x(int row, int col) {
        return pow2(abs(get(row, col - 1).red - get(row, col + 1).red));
    }

    private int G_x(int row, int col) {
        return pow2(abs(get(row, col - 1).green - get(row, col + 1).green));
    }

    private int B_x(int row, int col) {
        return pow2(abs(get(row, col - 1).blue - get(row, col + 1).blue));
    }

    private int R_y(int row, int col) {
        return pow2(abs(get(row - 1, col).red - get(row + 1, col).red));
    }

    private int G_y(int row, int col) {
        return pow2(abs(get(row - 1, col).green - get(row + 1, col).green));
    }

    private int B_y(int row, int col) {
        return pow2(abs(get(row - 1, col).blue - get(row + 1, col).blue));
    }

    private int pow2(int i) {
        return i * i;
    }

    public int[][] getEnergyArray() {
        int[][] energies = new int[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                energies[row][col] = get(row, col).energy;
        return energies;
    }

}
