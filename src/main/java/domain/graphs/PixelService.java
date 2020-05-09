package domain.graphs;

import domain.RgbService;

@SuppressWarnings("unused")
public interface PixelService extends RgbService {

    Pixel getLeftTop();

    void removeVertically(Pixel pixel);

    @SuppressWarnings("EmptyMethod")
    void removeHorizontally(Pixel pixel);

    int getWidth();

    int getHeight();

}
