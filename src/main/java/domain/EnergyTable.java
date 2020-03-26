package domain;

import domain.enums.Color;
import domain.enums.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.BiFunction;

import static domain.enums.Color.*;
import static domain.enums.Direction.*;

public class EnergyTable {
    private PixelTable pixelTable;
    private ArrayList<ArrayList<Integer>> dualGradientEnergies;

    public EnergyTable(Image image) {
        pixelTable = new PixelTable((BufferedImage) image);
    }

    private void computeDualGradientEnergyTable() {
        BiFunction<Integer, Integer, Integer>
                R_x = diffFunc(R, X), G_x = diffFunc(G, X), B_x = diffFunc(B, X),
                R_y = diffFunc(R, Y), G_y = diffFunc(G, Y), B_y = diffFunc(B, Y);
        dualGradientEnergies = new ArrayList<>(pixelTable.getWidth());
        for (int row = 0; row < pixelTable.getHeight(); row++) {
            ArrayList<Integer> rowList= new ArrayList<>(pixelTable.getWidth());
            dualGradientEnergies.add(rowList);
            for (int col = 0; col < pixelTable.getWidth(); col++) {
                int xGradientSquare = R_x.apply(row, col)^2 + G_x.apply(row, col)^2 + B_x.apply(row, col)^2;
                int yGradientSquare = R_y.apply(row, col)^2 + G_y.apply(row, col)^2 + B_y.apply(row, col)^2;
                rowList.add(xGradientSquare + yGradientSquare);
            }
        }
    }

    private BiFunction<Integer, Integer, Integer> diffFunc(Color c, Direction d) {
        return d == Direction.X ?
                (x, y) -> pixelTable.get(x - 1, y).getColor(c) - pixelTable.get(x + 1, y).getColor(c) :
                (x, y) -> pixelTable.get(x, y - 1).getColor(c) - pixelTable.get(x, y + 1).getColor(c);
    }

    public ArrayList<ArrayList<Integer>> getDualGradientEnergies() {
        if (dualGradientEnergies == null) computeDualGradientEnergyTable();
        return dualGradientEnergies;
    }
}
