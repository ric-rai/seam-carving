package domain.tables;


import java.awt.image.BufferedImage;

import static java.lang.Math.abs;

public class EnergyTable extends PixelTable {
    protected int[][] energies;

    public EnergyTable(BufferedImage image) {
        super(image);
        energies = new int[height][width];
    }

    final protected void computeDualGradientEnergy(int r, int c) {
        energies[r][c] = R_x(r, c - 1, c + 1) + G_x(r, c - 1, c + 1) + B_x(r, c - 1, c + 1) +
                         R_y(r - 1, r + 1, c) + G_y(r - 1, r + 1, c) + B_y(r - 1, r + 1, c);
    }

    final protected void computeDualGradientEnergyForLeftTopCorner() {
        energies[0][0] = R_x(0, width - 1, 1) + G_x(0, width - 1, 1) + B_x(0, width - 1, 1) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    final protected void computeDualGradientEnergyForTopRow(int c) {
        energies[0][c] = R_x(0, c - 1, c + 1) + G_x(0, c - 1, c + 1) + B_x(0, c - 1, c + 1) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    final protected void computeDualGradientEnergyForRightTopCorner() {
        energies[0][width - 1] = R_x(0, width - 2, 0) + G_x(0, width - 2, 0) + B_x(0, width - 2, 0) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    final protected void computeDualGradientEnergyForLeftmostCol(int r) {
        energies[r][0] = R_x(r, width - 1, 1) + G_x(r, width - 1, 1) + B_x(r, width - 1, 1) +
                R_y(r - 1, r + 1, 0) + G_y(r - 1, r + 1, 0) + B_y(r - 1, r + 1, 0);
    }

    final protected void computeDualGradientEnergyForRightmostCol(int r) {
        energies[r][width - 1] = R_x(r, width - 2, 0) + G_x(r, width - 2, 0) + B_x(r, width - 2, 0) +
                R_y(r - 1, r + 1, width - 1) + G_y(r - 1, r + 1, width - 1) + B_y(r - 1, r + 1, width - 1);
    }

    final protected void computeDualGradientEnergyForLeftBottomCorner() {
        energies[height - 1][0] = R_x(0, width - 1, 1) + G_x(0, width - 1, 1) + B_x(0, width - 1, 1) +
                R_y(height - 2, 0, 0) + G_y(height - 2, 0, 0) + B_y(height - 2, 0, 0);
    }

    final protected void computeDualGradientEnergyForBottomRow(int c) {
        int r = height - 1;
        energies[r][c] = R_x(r, c - 1, c + 1) + G_x(r, c - 1, c + 1) + B_x(r, c - 1, c + 1) +
                R_y(r - 1, 0, c) + G_y(r - 1, 0, c) + B_y(r - 1, 0, c);
    }

    final protected void computeDualGradientEnergyForRightBottomCorner() {
        int row = height - 1, col = width - 1;
        energies[row][col] = R_x(row, width - 2, 0) + G_x(0, width - 2, 0) + B_x(0, width - 2, 0) +
                R_y(height - 2, 0, col) + G_y(height - 2, 0, col) + B_y(height - 2, 0, col);
    }

    private int R_x(int row, int colLeft, int colRight) {
        return pow2(abs(red[row][colLeft] - red[row][colRight]));
    }

    private int G_x(int row, int colLeft, int colRight) {
        return pow2(abs(green[row][colLeft] - green[row][colRight]));
    }

    private int B_x(int row, int colLeft, int colRight) {
        return pow2(abs(blue[row][colLeft] - blue[row][colRight]));
    }

    private int R_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(red[rowAbove][col] - red[rowBelow][col]));
    }

    private int G_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(green[rowAbove][col] - green[rowBelow][col]));
    }

    private int B_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(blue[rowAbove][col] - blue[rowBelow][col]));
    }

    private int pow2(int i) {
        return i * i;
    }

}
