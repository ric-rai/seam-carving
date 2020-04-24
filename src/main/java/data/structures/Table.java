package data.structures;

/**
 * Matrix like two dimensional table structure. The user of this interface has precise control over where in the table
 * each element is inserted. The user can access elements by their integer coordinates (position in the table).
 *
 * @param <E> Type of elements in this table
 */
public interface Table<E> {

    /**
     * Gets the element in position (row, col) = (y, x)
     *
     * @param row y-coordinate
     * @param col x-coordinate
     * @return Element of type E
     */
    E get(int row, int col);

    /**
     * Sets the element in position (row, col) = (y, x)
     *
     * @param row y-coordinate
     * @param col x-coordinate
     */
    void set(int row, int col, E e);

    /**
     * @return Width of the table
     */
    int getWidth();

    /**
     * @return Height of the table
     */
    int getHeight();

}
