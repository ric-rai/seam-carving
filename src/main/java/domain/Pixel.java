package domain;

public class Pixel {
    private int red, green, blue;

    public Pixel() {}

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Pixel(byte red, byte green, byte blue) {
        this.red = red & 0xff;
        this.green = green & 0xff;
        this.blue = blue & 0xff;
    }

    public int getColor(String color) {
        return color.equals("R") ? red : color.equals("G") ? green : blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "Pixel{" + "red=" + red + ", green=" + green + ", blue=" + blue + '}';
    }
}
