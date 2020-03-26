package domain;

import data_structures.ArrayTable;
import domain.enums.Color;
import domain.enums.Direction;

import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

import static domain.enums.Color.*;
import static domain.enums.Direction.*;

public class EnergyTable extends ArrayTable<Integer> {
    private PixelTable pixelTable;
    private boolean dualGradientComputed;

    public EnergyTable(BufferedImage image) {
        super(Integer.class, image.getWidth(), image.getHeight());
        pixelTable = new PixelTable(image);
    }

    private void computeDualGradientEnergyTable() {
        BiFunction<Integer, Integer, Integer>
                R_x = diffFunc(R, X), G_x = diffFunc(G, X), B_x = diffFunc(B, X),
                R_y = diffFunc(R, Y), G_y = diffFunc(G, Y), B_y = diffFunc(B, Y);
        mapIndexed((x, y) ->
                R_x.apply(x, y) ^ 2 + G_x.apply(x, y) ^ 2 + B_x.apply(x, y) ^ 2 +
                R_y.apply(x, y) ^ 2 + G_y.apply(x, y) ^ 2 + B_y.apply(x, y) ^ 2);
        dualGradientComputed = true;
    }

    /**
     * Gives the difference function that computes the central difference of the given color in the given direction
     * using the pixel location (x, y) given as arguments.
     *
     * @param c Color enum
     * @param d Direction enum
     * @return Difference function
     */
    private BiFunction<Integer, Integer, Integer> diffFunc(Color c, Direction d) {
        return d == Direction.X ?
                (x, y) -> pixelTable.get(x - 1, y).getColor(c) - pixelTable.get(x + 1, y).getColor(c) :
                (x, y) -> pixelTable.get(x, y - 1).getColor(c) - pixelTable.get(x, y + 1).getColor(c);
    }

    public Integer[][] getDualGradientEnergies() {
        if (!dualGradientComputed) computeDualGradientEnergyTable();
        return table;
    }
}
