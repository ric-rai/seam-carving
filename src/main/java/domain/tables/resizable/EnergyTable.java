package domain.tables.resizable;


import domain.tables.resizable.PixelTable;

import java.awt.image.BufferedImage;

import static java.lang.Math.abs;

public class EnergyTable extends PixelTable {
    protected final int energy;

    public EnergyTable(BufferedImage image) {
        super(image);
        energy = dataFieldCount++;
    }

    protected void computeDualGradientEnergy(int r, int c) {
        get(r, c)[energy] = R_x(r, c - 1, c + 1) + G_x(r, c - 1, c + 1) + B_x(r, c - 1, c + 1) +
                         R_y(r - 1, r + 1, c) + G_y(r - 1, r + 1, c) + B_y(r - 1, r + 1, c);
    }

    protected void computeDualGradientEnergyForLeftTopCorner() {
        get(0, 0)[energy] = R_x(0, width - 1, 1) + G_x(0, width - 1, 1) + B_x(0, width - 1, 1) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    protected void computeDualGradientEnergyForTopRow(int c) {
        get(0, c)[energy] = R_x(0, c - 1, c + 1) + G_x(0, c - 1, c + 1) + B_x(0, c - 1, c + 1) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    protected void computeDualGradientEnergyForRightTopCorner() {
        get(0, width - 1)[energy] = R_x(0, width - 2, 0) + G_x(0, width - 2, 0) + B_x(0, width - 2, 0) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    protected void computeDualGradientEnergyForLeftmostCol(int r) {
        get(r, 0)[energy] = R_x(r, width - 1, 1) + G_x(r, width - 1, 1) + B_x(r, width - 1, 1) +
                R_y(r - 1, r + 1, 0) + G_y(r - 1, r + 1, 0) + B_y(r - 1, r + 1, 0);
    }

    protected void computeDualGradientEnergyForRightmostCol(int r) {
        get(r, width - 1)[energy] = R_x(r, width - 2, 0) + G_x(r, width - 2, 0) + B_x(r, width - 2, 0) +
                R_y(r - 1, r + 1, width - 1) + G_y(r - 1, r + 1, width - 1) + B_y(r - 1, r + 1, width - 1);
    }

    protected void computeDualGradientEnergyForLeftBottomCorner() {
        get(height - 1, 0)[energy] = R_x(0, width - 1, 1) + G_x(0, width - 1, 1) + B_x(0, width - 1, 1) +
                R_y(height - 2, 0, 0) + G_y(height - 2, 0, 0) + B_y(height - 2, 0, 0);
    }

    protected void computeDualGradientEnergyForBottomRow(int c) {
        int r = height - 1;
        get(r, c)[energy] = R_x(r, c - 1, c + 1) + G_x(r, c - 1, c + 1) + B_x(r, c - 1, c + 1) +
                R_y(r - 1, 0, c) + G_y(r - 1, 0, c) + B_y(r - 1, 0, c);
    }

    protected void computeDualGradientEnergyForRightBottomCorner() {
        int row = height - 1, col = width - 1;
        get(row, col)[energy] = R_x(row, width - 2, 0) + G_x(0, width - 2, 0) + B_x(0, width - 2, 0) +
                R_y(height - 2, 0, col) + G_y(height - 2, 0, col) + B_y(height - 2, 0, col);
    }

    private int R_x(int row, int colLeft, int colRight) {
        return pow2(abs(get(row, colLeft)[red] - get(row, colRight)[red]));
    }

    private int G_x(int row, int colLeft, int colRight) {
        return pow2(abs(get(row, colLeft)[green] - get(row, colRight)[green]));
    }

    private int B_x(int row, int colLeft, int colRight) {
        return pow2(abs(get(row, colLeft)[blue] - get(row, colRight)[blue]));
    }

    private int R_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(get(rowAbove, col)[red] - get(rowBelow, col)[red]));
    }

    private int G_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(get(rowAbove, col)[green] - get(rowBelow, col)[green]));
    }

    private int B_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(get(rowAbove, col)[blue] - get(rowBelow, col)[blue]));
    }

    private int pow2(int i) {
        return i * i;
    }

}
