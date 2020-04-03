package data.structures;

public class ArrayTable<E> extends AbstractTable<E>{

    public ArrayTable(Class<E> eClass, int width, int height) {
        super(eClass, width, height);
    }

    public E[][] getArray() {
        return table;
    }
}