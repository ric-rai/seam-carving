package data.structures;

import java.lang.reflect.Array;

public abstract class AbstractTable<E> implements Table<E> {
    protected E[][] table;
    protected int width;
    protected int height;

    @SuppressWarnings("unchecked")
    public AbstractTable(Class<E> eClass, int width, int height) {
        table = (E[][]) Array.newInstance(eClass, height, width);
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