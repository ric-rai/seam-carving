package domain.tables.seam;

import data.structures.ResizableArrayTable;
import domain.tables.EnergyTable;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RecomputingVerticalSeamTableTest extends AbstractSeamTableTest{
    ResizableArrayTable<Integer[]> table;
    RecomputingVerticalSeamTable seamTable;
    int[][] correctCumulativeEnergies = new int[][]{
                    {1, 4, 3, 5, 2},
                    {4, 3, 8, 4, 5},
                    {8, 5, 7, 6, 5}
    };

    @Before
    public void setSeamTable() {
        table = new ResizableArrayTable<>(Integer[].class, width, height);
        EnergyTable<Integer[]> energyTable = new SuppressedEnergyTable(table);
        seamTable = new RecomputingVerticalSeamTable(energyTable, cumulativeEnergy, lowestPredecessorOffset);
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
                {/*1,*/ 4, 3, 5, 2},
                {4, /*3,*/ 8, 4, 5},
                {8, /*5,*/ 7, 6, 5}};
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
        seamTable.removeSeams(1);
        correctCumulativeEnergies = new int[][]{
                {4, 3, 5, /*2*/},
                {6, 8, /*4,*/ 5},
                {11, 8, 6, /*5,*/}};
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
        setSeamTable();
        seamTable.removeSeams(2);
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
    }


    @Test
    public void addSeamsWorksCorrectly() {
        seamTable.addSeams(2);
        seamTable.computeSeams();
        correctCumulativeEnergies = new int[][]{
                {1, 1, 4, 3, 5, 2, 2},
                {4, 3, 3, 8, 4, 4, 5},
                {8, 5, 5, 7, 6, 5, 5}};
        assertThat(getCumulativeEnergies(table), is(correctCumulativeEnergies));
    }

}
