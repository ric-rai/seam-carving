package data.structures;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class SimpleResizableTable<E> implements Table<E> {
    protected E[][] table;
    protected int width;
    protected int height;

    public SimpleResizableTable(Class<E> eClass, int width, int height) {
        this.width = width;
        this.height = height;
        table = (E[][]) Array.newInstance(eClass, height, width);
    }

    public final void removeVertically(int[] indexes) {
        for (int row = 0; row < indexes.length; row++) {
            if (indexes[row] < width - 1) {
                for (int col = indexes[row]; col < width - 1; col++) {
                    table[row][col] = table[row][col + 1];
                }
            }
        }
        width--;
    }

    public final void removeHorizontally(int[] indexes) {
        for (int col = 0; col < indexes.length; col++) {
            if (indexes[col] < height - 1) {
                for (int row = indexes[col]; row < height - 1; row++) {
                    table[row][col] = table[row + 1][col];
                }
            }
        }
        height--;
    }

    @Override
    public final E get(int row, int col) {
        return table[row][col];
    }

    @Override
    public final void set(int row, int col, E e) {
        table[row][col] = e;
    }

    @Override
    public final int getWidth() {
        return width;
    }

    @Override
    public final int getHeight() {
        return height;
    }
}
