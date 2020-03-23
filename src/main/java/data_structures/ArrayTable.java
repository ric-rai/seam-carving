package data_structures;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ArrayTable<E> implements Table<E> {
    protected E[][] table;
    protected int width;
    protected int height;

    @SuppressWarnings("unchecked")
    public ArrayTable(Class<E> eClass, int width, int height) {
        table = (E[][]) Array.newInstance(eClass, width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public E get(int row, int col) {
        return table[row][col];
    }

    @Override
    public void set(int row, int col, E e) {
        table[row][col] = e;
    }

    @Override
    public void map(Function<E, E> function) {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                table[row][col] = function.apply(table[row][col]);
    }

    @Override
    public void mapIndexed(BiFunction<Integer, Integer, E> function) {
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                table[row][col] = function.apply(row, col);
    }

    public E[][] getTable() {
        return table;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
