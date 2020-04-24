package data.structures;

import org.junit.Before;
import org.junit.Test;

public class ResizableTablePerformanceTest {
    int width = 1920, height = 1080;
    ResizableTable<Integer> resizableTable = new ResizableTable<>(Integer.class, width, height);
    SimpleResizableTable<Integer> simpleResizableTable = new SimpleResizableTable<>(Integer.class, width, height);
    int[] verticalIndexes = new int[height];
    int[] horizontalIndexes = new int[width / 2];

    @Before
    public void setIndexes() {
        for (int row = 0; row < height; row += 2) {
            verticalIndexes[row] = 0;
            verticalIndexes[row + 1] = 1;
        }
        for (int col = 0; col < width / 2; col += 2) {
            horizontalIndexes[col] = 0;
            horizontalIndexes[col + 1] = 1;
        }
    }

    @Test
    public void comparisonTest() {
        long first = System.currentTimeMillis();
        for (int i = 0; i < width / 2; i++)
            resizableTable.removeVertically(verticalIndexes);
        for (int i = 0; i < height / 2; i++)
            resizableTable.removeHorizontally(horizontalIndexes);
        long second = System.currentTimeMillis();
        for (int i = 0; i < width / 2; i++)
            simpleResizableTable.removeVertically(verticalIndexes);
        for (int i = 0; i < height / 2; i++)
            simpleResizableTable.removeHorizontally(horizontalIndexes);
        long third = System.currentTimeMillis();
        System.out.println("Resizable table: " + (second - first));
        System.out.println("Simple resizable table: " + (third - second));
    }

}
