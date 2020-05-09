package domain;

public interface SeamCarver {

    void removeSeamsVertically(int numberOfSeams);

    void addSeamsVertically(int numberOfSeams);

    void removeSeamsHorizontally(int numberOfSeams);

    void addSeamsHorizontally(int numberOfSeams);

    RgbService getRgbService();

}
