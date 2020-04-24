package data.structures;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getException;

public class SimpleResizableTableTest {
    SimpleResizableTestTable<Boolean> testTable;
    boolean[][] testArray = new boolean[][]{
            {false, true},
            {true, false},
            {false, true}
    };
    int width, height;

    @Before
    public void setTestTable() {
        width = 2;
        height = 3;
        testTable = new SimpleResizableTestTable<>(Boolean.class, width, height);
        Boolean[][] booleans = new Boolean[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                booleans[row][col] = testArray[row][col];
        testTable.setArray(booleans);
    }

    @Test
    public void constructorWorksCorrectly() {
        testTable = new SimpleResizableTestTable<>(Boolean.class, width, height);
        assertThat(testTable.getWidth(), is(2));
        assertThat(testTable.getHeight(), is(3));
        assertThat(testTable.getArray(), equalTo(new Boolean[][]{{null, null}, {null, null}, {null, null}}));
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

    @Test
    public void removeVerticallyWorksCorrectly() {
        testTable.removeVertically(new int[]{1, 0, 1});
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{false}, {false}, {false}}));
    }

    @Test
    public void removeHorizontallyWorksCorrectly() {
        testTable.removeHorizontally(new int[]{1, 2});
        Boolean[][] resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{false, true}, {false, false}}));
        testTable.removeHorizontally(new int[]{1, 0});
        resizedArray = testTable.getResizedTableAsArray();
        assertThat(resizedArray, equalTo(new Boolean[][]{{false, false}}));
    }

    @SuppressWarnings("unchecked")
    private static class SimpleResizableTestTable<E> extends SimpleResizableTable<E> {
        Class<E> eClass;

        public SimpleResizableTestTable(Class<E> eClass, int width, int height) {
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
