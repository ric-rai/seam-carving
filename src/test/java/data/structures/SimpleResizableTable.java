package data.structures;

import java.lang.reflect.Array;

@SuppressWarnings({"unchecked", "ManualArrayCopy"})
public class SimpleResizableTable<E> {
    protected E[][] table;
    protected final Class<E> eClass;
    protected int width;
    protected int height;

    public SimpleResizableTable(Class<E> eClass, int width, int height) {
        this.eClass = eClass;
        this.width = width;
        this.height = height;
        table = (E[][]) Array.newInstance(eClass, height, width);
    }

    public final void removeVertically(int[] indexes) {
        for (int row = 0; row < indexes.length; row++) {
            if (indexes[row] < width - 1) {
                if (width - 1 - indexes[row] >= 0)
                    System.arraycopy(table[row], indexes[row] + 1, table[row], indexes[row], width - 1 - indexes[row]);
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

    public final void duplicateVertically(int[] indexes) {
        if (width == this.table[0].length) {
            E[][] table = (E[][]) Array.newInstance(eClass, height, width * 2);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width + 1; col++) {
                    if (col <= indexes[row]) table[row][col] = this.table[row][col];
                    else table[row][col] = this.table[row][col - 1];
                }
            }
            this.table = table;
        } else {
            for (int row = 0; row < height; row++)
                for (int col = indexes[row] + 1; col < width + 1; col++)
                    table[row][col] = this.table[row][col - 1];
        }
        width++;
    }

    public final void duplicateHorizontally(int[] indexes) {
        if (height == this.table.length) {
            E[][] table = (E[][]) Array.newInstance(eClass, height * 2, width);
            for (int row = 0; row < height + 1; row++) {
                for (int col = 0; col < width; col++) {
                    if (row <= indexes[col]) table[row][col] = this.table[row][col];
                    else table[row][col] = this.table[row - 1][col];                }
            }
            this.table = table;
        } else {
            for (int col = 0; col < width; col++)
                for (int row = indexes[col] + 1; row < height + 1; row++)
                    table[row][col] = this.table[row - 1][col];
        }
        height++;
    }

    public final E get(int row, int col) {
        return table[row][col];
    }

    public final void set(int row, int col, E e) {
        table[row][col] = e;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }
}
