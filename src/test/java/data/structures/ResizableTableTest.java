package data.structures;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ResizableTableTest {
    ResizableTestTable<Boolean> testTable;
    int width, height;
    boolean[][] testArray = new boolean[][]{
            {false, true, false},
            {true, false, true},
            {false, true, false},
            {true, false, true}
    };

    @Before
    public void setTestTable() {
        width = testArray[0].length;
        height = testArray.length;
        testTable = new ResizableTestTable<>(Boolean.class, width, height);
        Boolean[][] booleans = new Boolean[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                booleans[row][col] = testArray[row][col];
        testTable.setArray(booleans);
    }

    @Test
    public void constructorWorksCorrectly() {
        testTable = new ResizableTestTable<>(Boolean.class, width, height);
        assertThat(testTable.getWidth(), is(3));
        assertThat(testTable.getHeight(), is(4));
        assertThat(testTable.getArray(), equalTo(new Boolean[height][width]));
    }

    @Test
    public void removeVerticallyWorksCorrectly() {
        testTable.removeVertically(new int[]{0, 1, 0, 1});
        assertThat(testTable.width, is(width - 1));
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{true, false}, {true, true}, {true, false}, {true, true}}));
    }

    @Test
    public void removeHorizontallyWorksCorrectly() {
        testTable.removeHorizontally(new int[]{0, 1, 0});
        assertThat(testTable.height, is(height - 1));
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{true, true, true}, {false, true, false}, {true, false, true}}));
        testTable.removeHorizontally(new int[]{1, 2, 1});
        assertThat(testTable.height, is(height - 2));
        resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{true, true, true}, {true, true, true}}));
    }

    @Test
    public void removeHorizontallyWorksCorrectlyAfterRemoveVertically() {
        testTable.removeVertically(new int[]{0, 1, 0, 1});
        testTable.removeHorizontally(new int[]{1, 0});
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{true, true}, {true, false}, {true, true}}));
    }

    @Test
    public void removeVerticallyWorksCorrectlyAfterRemoveHorizontally() {
        testTable.removeHorizontally(new int[]{0, 1, 0});
        testTable.removeVertically(new int[]{1, 0, 1});
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{true, true}, {true, false}, {true, true}}));
    }

    @Test
    public void getWorksCorrectly() {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                assertThat(testTable.get(row, col), is(testArray[row][col]));
        //assertThat(getException(() -> testTable.get(height, 0)), instanceOf(ArrayIndexOutOfBoundsException.class));
        //assertThat(getException(() -> testTable.get(0, width)), instanceOf(ArrayIndexOutOfBoundsException.class));
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
        //assertThat(getException(() -> testTable.set(height, 0, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
        //assertThat(getException(() -> testTable.set(0, width, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @SuppressWarnings("unchecked")
    private static class ResizableTestTable<E> extends ResizableTable<E> {
        Class<E> eClass;

        public ResizableTestTable(Class<E> eClass, int width, int height) {
            super(eClass, width, height);
            this.eClass = eClass;
        }

        public E[][] getArray() {
            return table;
        }

        public void setArray(E[][] array) {
            table = array;
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
