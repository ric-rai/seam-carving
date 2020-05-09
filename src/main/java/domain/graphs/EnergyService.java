package domain.graphs;

public interface EnergyService<E> {

    void computeDualGradientEnergy(E e);

    void computeDualGradientEnergyForLeftTopCorner(E e);

    void computeDualGradientEnergyForTopRow(E e);

    void computeDualGradientEnergyForRightTopCorner(E e);

    void computeDualGradientEnergyForLeftmostCol(E e);

    void computeDualGradientEnergyForRightmostCol(E e);

    void computeDualGradientEnergyForLeftBottomCorner(E e);

    void computeDualGradientEnergyForBottomRow(E e);

    void computeDualGradientEnergyForRightBottomCorner(E e);

}
