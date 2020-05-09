package domain.tables;

import data.structures.Table;

public class ResizableEnergyTable implements EnergyTable<Integer[]> {
    private final Table<Integer[]> table;
    private final PixelTable<Integer[]> pixelTable;
    private final int red, green, blue;
    private final int energy;

    public ResizableEnergyTable(PixelTable<Integer[]> pixelTable, int energy) {
        this.pixelTable = pixelTable;
        this.table = pixelTable.getTable();
        this.red = pixelTable.getRedIndex();
        this.green = pixelTable.getGreenIndex();
        this.blue = pixelTable.getBlueIndex();
        this.energy = energy;
    }

    public final void computeDualGradientEnergy(int r, int c) {
        table.get(r, c)[energy] = R_x(r, c - 1, c + 1) + G_x(r, c - 1, c + 1) + B_x(r, c - 1, c + 1) +
                         R_y(r - 1, r + 1, c) + G_y(r - 1, r + 1, c) + B_y(r - 1, r + 1, c);
    }

    public final void computeDualGradientEnergyForLeftTopCorner() {
        int width = table.getWidth();
        int height = table.getHeight();
        table.get(0, 0)[energy] = R_x(0, width - 1, 1) + G_x(0, width - 1, 1) + B_x(0, width - 1, 1) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    public final void computeDualGradientEnergyForTopRow(int c) {
        int height = table.getHeight();
        table.get(0, c)[energy] = R_x(0, c - 1, c + 1) + G_x(0, c - 1, c + 1) + B_x(0, c - 1, c + 1) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    public final void computeDualGradientEnergyForRightTopCorner() {
        int width = table.getWidth();
        int height = table.getHeight();
        table.get(0, width - 1)[energy] = R_x(0, width - 2, 0) + G_x(0, width - 2, 0) + B_x(0, width - 2, 0) +
                R_y(height - 1, 1, 0) + G_y(height - 1, 1, 0) + B_y(height - 1, 1, 0);
    }

    public final void computeDualGradientEnergyForLeftmostCol(int r) {
        int width = table.getWidth();
        table.get(r, 0)[energy] = R_x(r, width - 1, 1) + G_x(r, width - 1, 1) + B_x(r, width - 1, 1) +
                R_y(r - 1, r + 1, 0) + G_y(r - 1, r + 1, 0) + B_y(r - 1, r + 1, 0);
    }

    public final void computeDualGradientEnergyForRightmostCol(int r) {
        int width = table.getWidth();
        table.get(r, width - 1)[energy] = R_x(r, width - 2, 0) + G_x(r, width - 2, 0) + B_x(r, width - 2, 0) +
                R_y(r - 1, r + 1, width - 1) + G_y(r - 1, r + 1, width - 1) + B_y(r - 1, r + 1, width - 1);
    }

    public final void computeDualGradientEnergyForLeftBottomCorner() {
        int width = table.getWidth();
        int height = table.getHeight();
        table.get(height - 1, 0)[energy] = R_x(0, width - 1, 1) + G_x(0, width - 1, 1) + B_x(0, width - 1, 1) +
                R_y(height - 2, 0, 0) + G_y(height - 2, 0, 0) + B_y(height - 2, 0, 0);
    }

    public final void computeDualGradientEnergyForBottomRow(int c) {
        int r = table.getHeight() - 1;
        table.get(r, c)[energy] = R_x(r, c - 1, c + 1) + G_x(r, c - 1, c + 1) + B_x(r, c - 1, c + 1) +
                R_y(r - 1, 0, c) + G_y(r - 1, 0, c) + B_y(r - 1, 0, c);
    }

    public final void computeDualGradientEnergyForRightBottomCorner() {
        int width = table.getWidth();
        int height = table.getHeight();
        int row = height - 1, col = width - 1;
        table.get(row, col)[energy] = R_x(row, width - 2, 0) + G_x(0, width - 2, 0) + B_x(0, width - 2, 0) +
                R_y(height - 2, 0, col) + G_y(height - 2, 0, col) + B_y(height - 2, 0, col);
    }

    private int R_x(int row, int colLeft, int colRight) {
        return pow2(abs(table.get(row, colLeft)[red] - table.get(row, colRight)[red]));
    }

    private int G_x(int row, int colLeft, int colRight) {
        return pow2(abs(table.get(row, colLeft)[green] - table.get(row, colRight)[green]));
    }

    private int B_x(int row, int colLeft, int colRight) {
        return pow2(abs(table.get(row, colLeft)[blue] - table.get(row, colRight)[blue]));
    }

    private int R_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(table.get(rowAbove, col)[red] - table.get(rowBelow, col)[red]));
    }

    private int G_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(table.get(rowAbove, col)[green] - table.get(rowBelow, col)[green]));
    }

    private int B_y(int rowAbove, int rowBelow, int col) {
        return pow2(abs(table.get(rowAbove, col)[blue] - table.get(rowBelow, col)[blue]));
    }

    private int pow2(int i) {
        return i * i;
    }

    private int abs(int n) {
        return n > 0 ? n : -n;
    }

    @Override
    public PixelTable<Integer[]> getPixelTable() {
        return pixelTable;
    }

    @Override
    public int getEnergyIndex() {
        return energy;
    }

}
