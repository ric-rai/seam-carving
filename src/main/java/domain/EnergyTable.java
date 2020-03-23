package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.BiFunction;

public class EnergyTable {
    private PixelTable pixelTable;
    private ArrayList<ArrayList<Integer>> dualGradientEnergies;

    public EnergyTable(Image image) {
        pixelTable = new PixelTable((BufferedImage) image);
    }

    private void computeDualGradientEnergyTable() {
        BiFunction<Integer, Integer, Integer>
                R_x = diffFunc("R_x"), G_x = diffFunc("G_x"), B_x = diffFunc("B_x"),
                R_y = diffFunc("R_y"), G_y = diffFunc("G_y"), B_y = diffFunc("B_y");
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

    private BiFunction<Integer, Integer, Integer> diffFunc(String colorAndDirection) {
        String[] args = colorAndDirection.split("_");
        return args[1].equals("x") ?
                (x, y) -> pixelTable.get(x - 1, y).getColor(args[0]) - pixelTable.get(x + 1, y).getColor(args[0]) :
                (x, y) -> pixelTable.get(x, y - 1).getColor(args[0]) - pixelTable.get(x, y + 1).getColor(args[0]);
    }

    public ArrayList<ArrayList<Integer>> getDualGradientEnergies() {
        if (dualGradientEnergies == null) computeDualGradientEnergyTable();
        return dualGradientEnergies;
    }
}
