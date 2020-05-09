package domain.tables.seam;

import data.structures.ResizableArrayTable;
import domain.tables.EnergyTable;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RecomputingHorizontalSeamTableTest extends AbstractSeamTableTest {
    ResizableArrayTable<Integer[]> table;
    RecomputingHorizontalSeamTable seamTable;
    final int[][] correctCumulativeEnergies = new int[][]{
                    {1, 5, 6, 11, 10},
                    {3, 3, 8, 8, 11},

                    {5, 5, 7, 9, 9}
    };

    @Before
    public void setSeamTable() {
        table = new ResizableArrayTable<>(Integer[].class, width, height);
        EnergyTable<Integer[]> energyTable = new SuppressedEnergyTable(table);
        seamTable = new RecomputingHorizontalSeamTable(energyTable, cumulativeEnergy, lowestPredecessorOffset);
    }

    @Test
    public void initialCumulativeEnergiesAreCorrect() {
        seamTable.computeSeams();
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
    }

    @Test
    public void removeSeamsWorksCorrectly() {
        seamTable.removeSeams(1);
        int[][] correctCumulativeEnergies = new int[][]{
                {3, 5, 8, 11, 10},
                {5, 5, 7, 9, 11}};
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
        seamTable.removeSeams(1);
        correctCumulativeEnergies = new int[][]{
                {5, 7, 10, 14, 14}};
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
        setSeamTable();
        seamTable.removeSeams(2);
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
    }


    @Test
    public void addSeamsWorksCorrectly() {
        seamTable.addSeams(2);
        seamTable.computeSeams();
        assertThat(getEnergies(table), is(new int[][]{
                {1, 4, 3, 5, 2},
                {1, 2, 3, 2, 2},
                {3, 2, 5, 2, 3},
                {3, 2, 4, 2, 1},
                {5, 2, 4, 2, 1}
        }));
    }

}
