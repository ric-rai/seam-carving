package data.structures;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getException;

public class ArrayTableTest {
    ArrayTable<Boolean> testTable;
    int width, height;

    @Before
    public void setTestTable() {
        width = 2;
        height = 3;
        testTable = new ArrayTable<>(Boolean.class, width, height);
    }

    @Test
    public void constructorWorksCorrectly() {
        assertThat(testTable.getWidth(), is(2));
        assertThat(testTable.getHeight(), is(3));
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{null, null}, {null, null}, {null, null}}));
    }

    @Test
    public void getWorksCorrectly() {
        Boolean[][] booleans = testTable.getTable();
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                booleans[row][col] = true;
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                assertThat(testTable.get(row, col), is(true));
        assertThat(getException(() -> testTable.get(height, 0)), instanceOf(ArrayIndexOutOfBoundsException.class));
        assertThat(getException(() -> testTable.get(0, width)), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @Test
    public void setWorksCorrectly() {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                testTable.set(row, col, true);
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{true, true}, {true, true}, {true, true}}));
        assertThat(getException(() -> testTable.set(height, 0, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
        assertThat(getException(() -> testTable.set(0, width, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @Test
    public void mapWorksCorrectly() {
        testTable.map(b -> true);
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{true, true}, {true, true}, {true, true}}));
    }

    @Test
    public void mapIndexedWorksCorrectly() {
        testTable.mapIndexed(Integer::equals);
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{true, false}, {false, true}, {false, false}}));
    }
}
