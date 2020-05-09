package domain;

import data.structures.ResizableArrayTable;
import domain.graphs.EnergyGraph;
import domain.graphs.PixelGraph;
import domain.graphs.seam.RecomputingVerticalSeamGraph;
import domain.tables.EnergyTable;
import domain.tables.PixelTable;
import domain.tables.ResizableEnergyTable;
import domain.tables.ResizablePixelTable;
import domain.tables.seam.RecomputingVerticalSeamTable;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScalingPerformanceTest {
    BufferedImage image;
    RecomputingVerticalSeamTable seamTable;
    RecomputingVerticalSeamGraph seamGraph;

    @Before
    public void before() {
        File tower = new File("src/test/resources/tower.jpg");
        try {
            image = ImageIO.read(tower);
        } catch (IOException ignored) {

        }
        ResizableArrayTable<Integer[]> table =
                new ResizableArrayTable<>(Integer[].class, image.getWidth(), image.getHeight());
        PixelTable<Integer[]> pixelTable = new ResizablePixelTable(table, image, 6, 0, 1, 2);
        EnergyTable<Integer[]> energyTable = new ResizableEnergyTable(pixelTable, 3);
        seamTable = new RecomputingVerticalSeamTable(energyTable, 4, 5);
        PixelGraph pixelGraph = new PixelGraph(image);
        seamGraph = new RecomputingVerticalSeamGraph(pixelGraph, new EnergyGraph());
    }

    @Test
    public void comparisonTest() {
        long first = System.currentTimeMillis();
        seamTable.removeSeams(image.getWidth() / 2);
        long second = System.currentTimeMillis();
        seamGraph.removeSeams(image.getWidth() / 2);
        long third = System.currentTimeMillis();
        System.out.println("Table: " + (second - first));
        System.out.println("Graph: " + (third - second));
    }
}
