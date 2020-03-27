package domain;

import data.structures.ArrayTable;
import domain.enums.Color;
import domain.enums.Direction;

import java.awt.image.BufferedImage;

import static domain.enums.Color.*;
import static domain.enums.Direction.*;
import static java.lang.Math.abs;

public class EnergyTable extends ArrayTable<Integer> {
    private PixelTable pixelTable;
    private boolean dualGradientComputed;

    public EnergyTable(BufferedImage image) {
        super(Integer.class, image.getWidth(), image.getHeight());
        pixelTable = new PixelTable(image);
    }

    private void computeDualGradientEnergyTable() {
        mapIndexed((r, c) ->
                squaredDiff(R, X, r, c) + squaredDiff(G, X, r, c) + squaredDiff(B, X, r, c) +
                squaredDiff(R, Y, r, c) + squaredDiff(G, Y, r, c) + squaredDiff(B, Y, r, c));
        dualGradientComputed = true;
    }

    private int squaredDiff(Color c, Direction d, int row, int col) {
        return pow2(abs(d == Direction.X ?
                pixelTable.get(row, col - 1).getColor(c) - pixelTable.get(row, col + 1).getColor(c) :
                pixelTable.get(row - 1, col).getColor(c) - pixelTable.get(row + 1, col).getColor(c)));
    }

    private int pow2(int i) {
        return i * i;
    }

    public Integer[][] getDualGradientEnergies() {
        if (!dualGradientComputed) computeDualGradientEnergyTable();
        return table;
    }
}
