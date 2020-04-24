package domain.tables.resizable;

import domain.Pixel;

import java.util.Arrays;

public class AbstractSeamTableTest {
    //AbstractSeamTable seamTable;
    int[][] fakeEnergies =
            new int[][]{
                    {1, 4, 3, 5, 2},
                    {3, 2, 5, 2, 3},
                    {5, 2, 4, 2, 1}
    };

/*
    void setSeamPixels(Pixel[] seamPixels, int seamIndex) {
        seamPixels[0] = seamTable.lastPixels[seamIndex];
        for (int i = 1; i < seamPixels.length; i++)
            seamPixels[i] = seamPixels[i - 1].prev;
    }

    Integer[][] getPixelPositions(Pixel[] pixels) {
        return Arrays.stream(pixels)
                .map(pixel -> pixel != null ? new Integer[]{pixel.row, pixel.col} : null)
                .toArray(Integer[][]::new);
    }

   /**
     * @param row index
     * @param col index
     * @return new Integer[]{row, col}
     */

/*
    Integer[] rc(int row, int col) {
        return new Integer[]{row, col};
    }

    Integer[][] positions(Integer[]... ints) {
        return ints;
    }*/
}