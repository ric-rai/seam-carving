package domain;

import data.structures.ArrayTable;
import domain.enums.Direction;

import java.awt.image.BufferedImage;


public class CumulativeEnergyTable extends EnergyTable {
    private ArrayTable<Integer> cumulativeVerticalEnergies, cumulativeHorizontalEnergies;

    public CumulativeEnergyTable(BufferedImage image) {
        super(image);
        cumulativeVerticalEnergies.mapIndexed((r, c) -> r == 0 ? table[r][c] : 0);
    }

    private int previousNodeOffset(int row, int col, Direction d) {
        return 0;
    }

}
