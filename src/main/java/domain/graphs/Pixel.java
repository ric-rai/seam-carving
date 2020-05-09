package domain.graphs;

public class Pixel {
    public int red, green, blue;
    public Pixel left, above, right, below;
    public Pixel leftmostOnRow, rightmostOnRow;
    public Pixel topmostOnColumn, bottommostOnColumn;
    public int energy, cumulativeEnergy;
    public Pixel lowestPredecessor;

    @Override
    public String toString() {
        return "Pixel{" + "red=" + red + ", green=" + green + ", blue=" + blue
                + ", left=" + left + ", above=" + above + ", right=" + right + ", below=" + below
                + ", energy=" + energy + ", cumulativeEnergy=" + cumulativeEnergy + ", prev=" + lowestPredecessor + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pixel)) return false;
        Pixel o = (Pixel) obj;
        return red == o.red && green == o.green && blue == o.blue;
    }

}
