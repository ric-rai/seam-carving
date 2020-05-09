package data.structures;

import java.lang.reflect.Array;

@SuppressWarnings({"ManualMinMaxCalculation", "unchecked", "unused"})
public class ResizableArrayTable<E> implements ResizableTable<E> {
    protected final E[][] table;
    protected int width;
    protected int height;
    private boolean isTransposed;
    protected int[][] rowIndexes;
    protected int[][] colIndexes;
    private final BasicGetter basicGetter = new BasicGetter();
    private final TransposedGetter transposedGetter = new TransposedGetter();
    private Getter<E> getter = basicGetter;
    private final BasicSetter basicSetter = new BasicSetter();
    private final TransposedSetter transposedSetter = new TransposedSetter();
    private Setter<E> setter = basicSetter;

    public ResizableArrayTable(Class<E> eClass, int width, int height) {
        this.width = width;
        this.height = height;
        table = (E[][]) Array.newInstance(eClass, height, width);
        initIndexes();
    }

    protected void initIndexes() {
        rowIndexes = new int[height][width];
        colIndexes = new int[height][width];
        for (int col = 1; col < width; col++)
            colIndexes[0][col] = col;
        for (int row = 1; row < height; row++)
            System.arraycopy(colIndexes[0], 0, colIndexes[row], 0, width);
        for (int row = 0; row < height; row++) {
            for (int i = 1; i < width; i += i) {
                rowIndexes[row][0] = row;
                System.arraycopy(rowIndexes[row], 0, rowIndexes[row], i, ((width - i) < i) ? (width - i) : i);
            }
        }
    }

    private void transposeIndexes() {
        int[][] transposedRowIndexes, transposedColIndexes;
        transposedRowIndexes = new int[width][height];
        transposedColIndexes = new int[width][height];
        swapIndexArraysRowsAndColumns(transposedRowIndexes, transposedColIndexes);
        getter = transposedGetter;
        setter = transposedSetter;
        isTransposed = true;
        rowIndexes = transposedRowIndexes;
        colIndexes = transposedColIndexes;
    }

    private void revertIndexTranspose() {
        int[][] transposedRowIndexes, transposedColIndexes;
        transposedRowIndexes = new int[height][width];
        transposedColIndexes = new int[height][width];
        swapIndexArraysRowsAndColumns(transposedRowIndexes, transposedColIndexes);
        getter = basicGetter;
        setter = basicSetter;
        isTransposed = false;
        rowIndexes = transposedRowIndexes;
        colIndexes = transposedColIndexes;
    }

    private void swapIndexArraysRowsAndColumns(int[][] transposedRowIndexes, int[][] transposedColIndexes) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                transposedRowIndexes[col][row] = rowIndexes[row][col];
                transposedColIndexes[col][row] = colIndexes[row][col];
            }
        }
    }

    @Override
    public final E get(int row, int col) {
        return getter.get(row, col);
    }

    @Override
    public final void set(int row, int col, E element) {
        setter.set(row, col, element);
    }

    @Override
    public final void removeVertically(int[] indexes) {
        if (isTransposed) revertIndexTranspose();
        removeIndexesRowByRow(indexes, width);
        width--;
    }

    @Override
    public final void removeHorizontally(int[] indexes) {
        if (!isTransposed) transposeIndexes();
        removeIndexesRowByRow(indexes, height);
        height--;
    }

    private void removeIndexesRowByRow(int[] indexes, int rowLength) {
        for (int row = 0; row < indexes.length; row++) {
            if (indexes[row] < rowLength - 1) {
                int col = indexes[row];
                System.arraycopy(colIndexes[row], col + 1, colIndexes[row], col, rowLength - col - 1);
                System.arraycopy(rowIndexes[row], col + 1, rowIndexes[row], col, rowLength - col - 1);
            }
        }
    }

    @Override
    public final void duplicateVertically(int[] indexes) {
        if (isTransposed) revertIndexTranspose();
        if (width == table[0].length) increaseIndexes();
        duplicateIndexesRowByRow(indexes, width);
        width++;
    }

    @Override
    public final void duplicateHorizontally(int[] indexes) {
        if (!isTransposed) transposeIndexes();
        if (height == table.length) increaseIndexes();
        duplicateIndexesRowByRow(indexes, height);
        height++;
    }

    private void duplicateIndexesRowByRow(int[] indexes, int rowLength) {
        for (int row = 0; row < indexes.length; row++) {
            int col = indexes[row];
            System.arraycopy(colIndexes[row], col, colIndexes[row], col + 1, rowLength - col);
            System.arraycopy(rowIndexes[row], col, rowIndexes[row], col + 1, rowLength - col);
        }
    }

    private void increaseIndexes() {
        int height = isTransposed ? this.width : this.height;
        int width = isTransposed ? this.height : this.width;
        int[][] rowIndexes = new int[height][width * 2];
        int[][] colIndexes = new int[height][width * 2];
        for (int row = 0; row < height; row++) {
            System.arraycopy(this.rowIndexes[row], 0, rowIndexes[row], 0, width);
            System.arraycopy(this.colIndexes[row], 0, colIndexes[row], 0, width);
        }
        this.rowIndexes = rowIndexes;
        this.colIndexes = colIndexes;
    }

    private interface Getter<E> {
        E get(int row, int col);
    }

    private final class BasicGetter implements Getter<E> {
        @Override
        public E get(int row, int col) {
            return table[rowIndexes[row][col]][colIndexes[row][col]];
        }
    }

    private final class TransposedGetter implements Getter<E> {
        @Override
        public E get(int row, int col) {
            return table[rowIndexes[col][row]][colIndexes[col][row]];
        }
    }

    private interface Setter<E> {
        void set(int row, int col, E element);
    }

    private final class BasicSetter implements Setter<E> {
        @Override
        public void set(int row, int col, E element) {
            table[rowIndexes[row][col]][colIndexes[row][col]] = element;
        }
    }

    private final class TransposedSetter implements Setter<E> {
        @Override
        public void set(int row, int col, E element) {
            table[rowIndexes[col][row]][colIndexes[col][row]] = element;
        }
    }

    @Override
    public final int getWidth() {
        return width;
    }

    @Override
    public final int getHeight() {
        return height;
    }

    public final void setWidth(int width) {
        this.width = width;
    }

    public final void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int[][] getRowIndexes() {
        return rowIndexes;
    }

    @Override
    public int[][] getColIndexes() {
        return colIndexes;
    }

    @Override
    public void setRowIndexes(int[][] rowIndexes) {
        this.rowIndexes = rowIndexes;
    }

    @Override
    public void setColIndexes(int[][] colIndexes) {
        this.colIndexes = colIndexes;

    }
}