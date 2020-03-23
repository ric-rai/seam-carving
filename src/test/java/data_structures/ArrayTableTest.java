package data_structures;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getException;

public class ArrayTableTest {
    ArrayTable<Boolean> testTable;
    @Rule public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setTestTable() {
        testTable = new ArrayTable<>(Boolean.class, 2, 2);
    }

    @Test
    public void constructorWorksCorrectly() {
        assertThat(testTable.getWidth(), is(2));
        assertThat(testTable.getHeight(), is(2));
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{null, null}, {null, null}}));
    }

    @Test
    public void getWorksCorrectly() {
        Boolean[][] booleans = testTable.getTable();
        for (int row = 0; row < 2; row++)
            for (int col = 0; col < 2; col++)
                booleans[row][col] = true;
        for (int row = 0; row < 2; row++)
            for (int col = 0; col < 2; col++)
                assertThat(testTable.get(row, col), is(true));
        assertThat(getException(() -> testTable.get(2, 0)), instanceOf(ArrayIndexOutOfBoundsException.class));
        assertThat(getException(() -> testTable.get(0, 2)), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @Test
    public void setWorksCorrectly() {
        testTable.set(0, 1, true);
        testTable.set(1, 0, true);
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{null, true}, {true, null}}));
        assertThat(getException(() -> testTable.set(2, 0, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
        assertThat(getException(() -> testTable.set(0, 2, true)), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @Test
    public void mapIsWorksCorrectly() {
        testTable.map(b -> true);
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{true, true}, {true, true}}));
    }

    @Test
    public void mapIndexedWorksCorrectly() {
        testTable.mapIndexed(Integer::equals);
        assertThat(testTable.getTable(), equalTo(new Boolean[][]{{true, false}, {false, true}}));
    }
}
