package domain;

public class Pixel {
    public int red, green, blue;
    public int row, col;
    public Pixel left, above, right, below;
    public int energy, cumulativeEnergy;
    public Pixel prev;

    public Pixel() {}

    public Pixel(int row, int col) {
        setColors(red, green, blue);
        setPosition(row, col);
    }

    /*public Pixel(int row, int col, int argbInt) {
        setColors((argbInt & 0xff0000) >> 16, (argbInt & 0xff00) >> 8, argbInt & 0xff);
        setPosition(row, col);
    }

    public Pixel(int row, int col, byte red, byte green, byte blue) {
        setColors(red & 0xff, green & 0xff, blue & 0xff);
        setPosition(row, col);
    }*/

    private void setColors(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "Pixel{" + "r=" + red + ", g=" + green + ", b=" + blue +
                ", row=" + row + ", col=" + col + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pixel)) return false;
        Pixel o = (Pixel) obj;
        return red == o.red && green == o.green && blue == o.blue;
    }

}
