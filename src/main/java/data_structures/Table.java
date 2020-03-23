package data_structures;

import java.util.function.BiFunction;
import java.util.function.Function;


/**
 * Matrix like two dimensional table structure. The user of this interface has precise control over where in the table
 * each element is inserted. The user can access elements by their integer coordinates (position in the table).
 * <p>
 * The Table interface provides a mapping method, which can be used to transform the underlying table data.
 *
 * @param <E> Type of elements in this table
 */
public interface Table<E> {

    /**
     * Gets the element in position (row, col) = (x, y)
     *
     * @param row x-coordinate in table
     * @param col y-coordinate in table
     * @return Element of type E
     */
    E get(int row, int col);

    /**
     * Sets the element in position (row, col) = (x, y)
     *
     * @param row x-coordinate in table
     * @param col y-coordinate in table
     */
    void set(int row, int col, E e);

    /**
     * Applies a mapping function to the table data to transform the table.
     *
     * @param function A mapping function
     */
    void map(Function<E, E> function);

    /**
     * Applies a mapping function to the table data to transform the table.
     *
     * @param function A mapping function that takes two arguments; the first one is the row number and the second one
     *                 the column number.
     */
    void mapIndexed(BiFunction<Integer, Integer, E> function);

    /**
     * @return Width of the table
     */
    int getWidth();

    /**
     * @return Height of the table
     */
    int getHeight();
}
