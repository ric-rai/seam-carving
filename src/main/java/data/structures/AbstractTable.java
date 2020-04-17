package data.structures;

import java.lang.reflect.Array;

public abstract class AbstractTable<E> implements Table<E> {
    protected E[][] table;
    protected Class<E> eClass;
    protected int[][] rowIndexes;
    protected int[][] colIndexes;
    protected int width;
    protected int height;

    @SuppressWarnings("unchecked")
    public AbstractTable(Class<E> eClass, int width, int height) {
        this.eClass = eClass;
        this.width = width;
        this.height = height;
        table = (E[][]) Array.newInstance(eClass, height, width);
        rowIndexes = new int[height][width];
        colIndexes = new int[height][width];
        for (int row = 0; row < height; row++)
            rowIndexes[0][row] = row;
        for (int i = 1; i < width; i += i)
            System.arraycopy(rowIndexes, 0, rowIndexes, i, Math.min((width - i), i));
        for (int col = 0; col < width; col++)
            rowIndexes[0][col] = col;

    }

    @Override
    public E get(int row, int col) {
        return table[rowIndexes[row][col]][colIndexes[row][col]];
    }

    @Override
    public void set(int row, int col, E element) {
        table[rowIndexes[row][col]][colIndexes[row][col]] = element;
    }

    @Override
    public void removeVertically(int[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
        }
    }

    @Override
    public void removeHorizontally(int[] indexes) {

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getCollapsedIndex(int row, int col) {
        return row * width + col;
    }

}