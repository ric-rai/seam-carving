package domain;

import data.structures.ResizableArrayTable;
import domain.tables.EnergyTable;
import domain.tables.PixelTable;
import domain.tables.ResizableEnergyTable;
import domain.tables.ResizablePixelTable;
import domain.tables.seam.RecomputingSeamTable;

import java.awt.image.BufferedImage;

public class SeamCarverFactory {
    public final static int TABLE = 0;
    public final static int GRAPH = 1;

    public static SeamCarver getSeamCarver(int type, BufferedImage image) {
        if (type == TABLE) return newSeamTable(image);
        else if (type == GRAPH) return newSeamGraph(image);
        return null;
    }

    private static SeamCarver newSeamTable(BufferedImage image) {
        int red = 0; int green = 1; int blue = 2;
        int energy = 3; int cumulativeEnergy = 4;
        int lowestPredecessorOffset = 5;
        int dataFieldCount = 6;
        ResizableArrayTable<Integer[]> table = new ResizableArrayTable<>(Integer[].class, image.getWidth(), image.getHeight());
        PixelTable<Integer[]> pixelTable = new ResizablePixelTable(table, image, dataFieldCount, red, green, blue);
        EnergyTable<Integer[]> energyTable = new ResizableEnergyTable(pixelTable, energy);
        return new RecomputingSeamTable(energyTable, cumulativeEnergy, lowestPredecessorOffset);
    }

    @SuppressWarnings({"SameReturnValue", "unused"})
    private static SeamCarver newSeamGraph(BufferedImage image) {
        return null;
    }

}
