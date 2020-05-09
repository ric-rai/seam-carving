package domain.tables;

import data.structures.Table;
import domain.RgbService;

public interface PixelTable<E> extends RgbService {

    /**
     * If pixel elements are indexed collections like arrays or lists, this method returns the index of red component,
     * otherwise null.
     *k
     * @return Index of red component.
     */
    Integer getRedIndex();

    /**
     * If pixel elements are indexed collections like arrays or lists, this method returns the index of green component,
     * otherwise null.
     *
     * @return Index of green component.
     */
    Integer getGreenIndex();

    /**
     * If pixel elements are indexed collections like arrays or lists, this method returns the index of blue component,
     * otherwise null.
     *
     * @return Index of blue component.
     */
    Integer getBlueIndex();

    /**
     * Returns the underlying table structure.
     *
     * @return Underlying table.
     */
    Table<E> getTable();

}