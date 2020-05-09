package data.structures;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getException;

public class ResizableArrayTableTest {
    ResizableArrayTestTable<Boolean> testTable;
    int width, height;
    final boolean[][] testArray = new boolean[][]{
            {false, true, false},
            {true, false, true},
            {false, true, false},
            {true, false, true}
    };

    @Before
    public void setTestTable() {
        width = testArray[0].length;
        height = testArray.length;
        testTable = new ResizableArrayTestTable<>(Boolean.class, width, height);
        Boolean[][] booleans = new Boolean[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                booleans[row][col] = testArray[row][col];
        testTable.setArray(booleans);
    }

    @Test
    public void constructorWorksCorrectly() {
        testTable = new ResizableArrayTestTable<>(Boolean.class, width, height);
        assertThat(testTable.getWidth(), is(width));
        assertThat(testTable.getHeight(), is(height));
        assertThat(testTable.getArray(), is(new Boolean[height][width]));
    }

    @Test
    public void removeVerticallyWorksCorrectly() {
        testTable.removeVertically(new int[]{0, 1, 0, 1});
        assertThat(testTable.width, is(width - 1));
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, is(new Boolean[][]{{true, false}, {true, true}, {true, false}, {true, true}}));
    }

    @Test
    public void removeHorizontallyWorksCorrectly() {
        testTable.removeHorizontally(new int[]{0, 1, 0});
        assertThat(testTable.height, is(height - 1));
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, is(new Boolean[][]{{true, true, true}, {false, true, false}, {true, false, true}}));
        testTable.removeHorizontally(new int[]{1, 2, 1});
        assertThat(testTable.height, is(height - 2));
        resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, is(new Boolean[][]{{true, true, true}, {true, true, true}}));
    }

    @Test
    public void removeHorizontallyWorksCorrectlyAfterRemoveVertically() {
        testTable.removeVertically(new int[]{0, 1, 0, 1});
        testTable.removeHorizontally(new int[]{1, 0});
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, is(new Boolean[][]{{true, true}, {true, false}, {true, true}}));
    }

    @Test
    public void removeVerticallyWorksCorrectlyAfterRemoveHorizontally() {
        testTable.removeHorizontally(new int[]{0, 1, 0});
        testTable.removeVertically(new int[]{1, 0, 1});
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, is(new Boolean[][]{{true, true}, {true, false}, {true, true}}));
    }

    @Test
    public void duplicateVerticallyWorksCorrectly() {
        int[] indexes = new int[]{1, 2, 1, 2};
        testTable.duplicateVertically(indexes);
        boolean[][] testArray = new boolean[height][width + 1];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width + 1; col++) {
                if (col <= indexes[row]) testArray[row][col] = this.testArray[row][col];
                else testArray[row][col] = this.testArray[row][col - 1];
            }
        }
        assertThat(testTable.getResizedTableAsArray(), is(testArray));
    }

    @Test
    public void duplicateHorizontallyWorksCorrectly() {
        int[] indexes = new int[]{3, 2, 3};
        testTable.duplicateHorizontally(indexes);
        boolean[][] testArray = new boolean[height + 1][width];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height + 1; row++) {
                if (row <= indexes[col]) testArray[row][col] = this.testArray[row][col];
                else testArray[row][col] = this.testArray[row - 1][col];
            }
        }
        assertThat(testTable.getResizedTableAsArray(), is(testArray));
    }

    @Test
    public void removeAndDuplicateWorksCorrectlyVerticallyAndHorizontally() {
        int[] verticalIndexes = new int[]{0, 1, 0, 1};
        int[] horizontalIndexes = new int[]{0, 1, 0};
        for (int i = 0; i < 2; i++)
            testTable.removeVertically(verticalIndexes);
        for (int i = 0; i < 2; i++)
            testTable.duplicateVertically(verticalIndexes);
        for (int i = 0; i < 3; i++)
            testTable.removeHorizontally(horizontalIndexes);
        for (int i = 0; i < 3; i++)
            testTable.duplicateHorizontally(horizontalIndexes);
    }

    @Test
    public void getWorksCorrectly() {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                assertThat(testTable.get(row, col), is(testArray[row][col]));
        assertThat(getException(() -> testTable.get(height, 0)), instanceOf(ArrayIndexOutOfBoundsException.class));
        assertThat(getException(() -> testTable.get(0, width)), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @Test
    public void setWorksCorrectly() {
        Boolean[][] inverted = new Boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                testTable.set(row, col, !testArray[row][col]);
                inverted[row][col] = !testArray[row][col];
            }
        }
        assertThat(testTable.getArray(), equalTo(inverted));
        assertThat(getException(() -> testTable.set(height, 0, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
        assertThat(getException(() -> testTable.set(0, width, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @SuppressWarnings("unchecked")
    private static class ResizableArrayTestTable<E> extends ResizableArrayTable<E> {
        final Class<E> eClass;

        public ResizableArrayTestTable(Class<E> eClass, int width, int height) {
            super(eClass, width, height);
            this.eClass = eClass;
        }

        public E[][] getArray() {
            return table;
        }

        @SuppressWarnings("ManualArrayCopy")
        public void setArray(E[][] array) {
            for (int row = 0; row < height; row++)
                for (int col = 0; col < width; col++)
                    table[row][col] = array[row][col];
        }

        public E[][] getResizedTableAsArray() {
            E[][] array = (E[][]) Array.newInstance(eClass, height, width);
            for (int row = 0; row < height; row++)
                for (int col = 0; col < width; col++)
                    array[row][col] = get(row, col);
            return array;
        }

    }

}
