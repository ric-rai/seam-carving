package domain.graphs;

public class EnergyGraph implements EnergyService<Pixel> {

    public final void computeDualGradientEnergy(Pixel p) {
        p.energy = R_x(p.left, p.right) + G_x(p.left, p.right) + B_x(p.left, p.right) +
                R_y(p.above, p.below) + G_y(p.above, p.below) + B_y(p.above, p.below);
    }

    public final void computeDualGradientEnergyForLeftTopCorner(Pixel p) {
        Pixel left = p.rightmostOnRow, above = p.bottommostOnColumn;
        p.energy = R_x(left, p.right) + G_x(left, p.right) + B_x(left, p.right) +
                R_y(above, p.below) + G_y(above, p.below) + B_y(above, p.below);
    }

    public final void computeDualGradientEnergyForTopRow(Pixel p) {
        Pixel above = p.bottommostOnColumn;
        p.energy = R_x(p.left, p.right) + G_x(p.left, p.right) + B_x(p.left, p.right) +
                R_y(above, p.below) + G_y(above, p.below) + B_y(above, p.below);
    }

    public final void computeDualGradientEnergyForRightTopCorner(Pixel p) {
        Pixel right = p.leftmostOnRow, above = p.bottommostOnColumn;
        p.energy = R_x(p.left, right) + G_x(p.left, right) + B_x(p.left, right) +
                R_y(above, p.below) + G_y(above, p.below) + B_y(above, p.below);
    }

    public final void computeDualGradientEnergyForLeftmostCol(Pixel p) {
        Pixel left = p.rightmostOnRow;
        p.energy = R_x(left, p.right) + G_x(left, p.right) + B_x(left, p.right) +
                R_y(p.above, p.below) + G_y(p.above, p.below) + B_y(p.above, p.below);
    }

    public final void computeDualGradientEnergyForRightmostCol(Pixel p) {
        Pixel right = p.leftmostOnRow;
        p.energy = R_x(p.left, right) + G_x(p.left, right) + B_x(p.left, right) +
                R_y(p.above, p.below) + G_y(p.above, p.below) + B_y(p.above, p.below);
    }

    public final void computeDualGradientEnergyForLeftBottomCorner(Pixel p) {
        Pixel left = p.rightmostOnRow, below = p.topmostOnColumn;
        p.energy = R_x(left, p.right) + G_x(left, p.right) + B_x(left, p.right) +
                R_y(p.above, below) + G_y(p.above, below) + B_y(p.above, below);
    }

    public final void computeDualGradientEnergyForBottomRow(Pixel p) {
        Pixel below = p.topmostOnColumn;
        p.energy = R_x(p.left, p.right) + G_x(p.left, p.right) + B_x(p.left, p.right) +
                R_y(p.above, below) + G_y(p.above, below) + B_y(p.above, below);
    }

    public final void computeDualGradientEnergyForRightBottomCorner(Pixel p) {
        Pixel right = p.leftmostOnRow, below = p.topmostOnColumn;
        p.energy = R_x(p.left, right) + G_x(p.left, right) + B_x(p.left, right) +
                R_y(p.above, below) + G_y(p.above, below) + B_y(p.above, below);
    }

    private int R_x(Pixel left, Pixel right) {
        return pow2(abs(left.red - right.red));
    }

    private int G_x(Pixel left, Pixel right) {
        return pow2(abs(left.green - right.green));
    }

    private int B_x(Pixel left, Pixel right) {
        return pow2(abs(left.blue - right.blue));
    }

    private int R_y(Pixel above, Pixel below) {
        return pow2(abs(above.red - below.red));
    }

    private int G_y(Pixel above, Pixel below) {
        return pow2(abs(above.green - below.green));
    }

    private int B_y(Pixel above, Pixel below) {
        return pow2(abs(above.blue - below.blue));
    }

    private int pow2(int i) {
        return i * i;
    }

    private int abs(int n) {
        return n > 0 ? n : -n;
    }

}
