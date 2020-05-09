package data.structures;

/**
 * Matrix like two dimensional table structure. The user of this interface has precise control over where in the table
 * each element is inserted. The user can access elements by their integer coordinates (position in the table).
 *
 * @param <E> Type of elements in this table
 */
@SuppressWarnings("unused")
public interface ResizableTable<E> extends Table<E>{

    /**
     * Removes elements row by row. For every row the position of the removed element is (row, indexes[row]).
     *
     * @param indexes row indexes
     */
    void removeVertically(int[] indexes);

    /**
     * Removes elements column by column. For every column the position of the removed element is (indexes[col], col).
     *
     * @param indexes column indexes
     */
    void removeHorizontally(int[] indexes);

    /**
     * Duplicates elements row by row. For every row the position of the duplicated element is (row, indexes[row]).
     * After the duplication every duplicated element is in two positions; (row, indexes[row]) and (row, indexes[row] +
     * 1). All the positions for which col > indexes[row] is true are shifted to position (row, col + 1).
     *
     * @param indexes row indexes
     */
    void duplicateVertically(int[] indexes);

    /**
     * Duplicates elements column by column. For every column the position of the duplicated element is (indexes[col],
     * col). After the duplication every duplicated element is in two positions; (indexes[col], col) and (indexes[col] +
     * 1, col). All the positions for which row > indexes[col] is true are shifted to position (row + 1, col).
     *
     * @param indexes column indexes
     */
    void duplicateHorizontally(int[] indexes);

    int[][] getRowIndexes();

    int[][] getColIndexes();

    void setRowIndexes(int[][] rowIndexes);

    void setColIndexes(int[][] colIndexes);

    void setWidth(int width);

    void setHeight(int height);

}
