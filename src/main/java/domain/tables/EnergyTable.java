package domain.tables;

public interface EnergyTable<E> {

    void computeDualGradientEnergy(int r, int c);

    void computeDualGradientEnergyForLeftTopCorner();

    void computeDualGradientEnergyForTopRow(int c);

    void computeDualGradientEnergyForRightTopCorner();

    void computeDualGradientEnergyForLeftmostCol(int r);

    void computeDualGradientEnergyForRightmostCol(int r);

    void computeDualGradientEnergyForLeftBottomCorner();

    void computeDualGradientEnergyForBottomRow(int c);

    void computeDualGradientEnergyForRightBottomCorner();

    PixelTable<E> getPixelTable();

    int getEnergyIndex();
}
