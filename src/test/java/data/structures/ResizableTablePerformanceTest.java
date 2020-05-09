package data.structures;

import org.junit.Before;
import org.junit.Test;

public class ResizableTablePerformanceTest {
    final int width = 1920;
    final int height = 1080;
    final ResizableArrayTable<Integer> resizableTable = new ResizableArrayTable<>(Integer.class, width, height);
    final SimpleResizableTable<Integer> simpleResizableTable = new SimpleResizableTable<>(Integer.class, width, height);
    final int[] verticalIndexes = new int[height];
    final int[] horizontalIndexes = new int[width];

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
        for (int i = 0; i < width / 4; i++)
            resizableTable.removeVertically(verticalIndexes);
        for (int i = 0; i < width / 4; i++)
            resizableTable.duplicateVertically(verticalIndexes);
        for (int i = 0; i < height / 4; i++)
            resizableTable.removeHorizontally(horizontalIndexes);
        for (int i = 0; i < height / 4; i++)
            resizableTable.duplicateHorizontally(horizontalIndexes);
        long second = System.currentTimeMillis();
        for (int i = 0; i < width / 4; i++)
            simpleResizableTable.removeVertically(verticalIndexes);
        for (int i = 0; i < width / 4; i++)
            simpleResizableTable.duplicateVertically(verticalIndexes);
        for (int i = 0; i < height / 4; i++)
            simpleResizableTable.removeHorizontally(horizontalIndexes);
        for (int i = 0; i < height / 4; i++)
            simpleResizableTable.duplicateHorizontally(horizontalIndexes);
        long third = System.currentTimeMillis();
        System.out.println("Resizable table: " + (second - first));
        System.out.println("Simple resizable table: " + (third - second));
    }

}
