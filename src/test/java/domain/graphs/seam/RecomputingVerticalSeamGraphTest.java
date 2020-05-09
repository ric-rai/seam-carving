package domain.graphs.seam;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RecomputingVerticalSeamGraphTest extends AbstractRecomputingSeamGraphTest {
    RecomputingVerticalSeamGraph seamGraph;
    final int[][] correctCumulativeEnergies = new int[][]{
                    {1, 4, 3, 5, 2},
                    {4, 3, 8, 4, 5},
                    {8, 5, 7, 6, 5}
    };

    @Before
    public void setSeamGraph() {
        initPixelGraph();
        seamGraph = new RecomputingVerticalSeamGraph(pixelGraph, new SuppressedEnergyGraph());
    }

    @Test
    public void initialCumulativeEnergiesAreCorrect() {
        seamGraph.computeSeams();
        assertThat(getCumulativeEnergies(pixelGraph), is(correctCumulativeEnergies));
    }

    @Test
    public void removeSeamsWorksCorrectly() {
        seamGraph.removeSeams(1);
        int[][] correctCumulativeEnergies = new int[][]{
                {/*1,*/ 4, 3, 5, 2},
                {4, /*3,*/ 8, 4, 5},
                {8, /*5,*/ 7, 6, 5}};
        assertThat(getCumulativeEnergies(pixelGraph), is(correctCumulativeEnergies));
        seamGraph.removeSeams(1);
        correctCumulativeEnergies = new int[][]{
                {4, 3, 5, /*2*/},
                {6, 8, /*4,*/ 5},
                {11, 8, 6, /*5,*/}};
        assertThat(getCumulativeEnergies(pixelGraph), is(correctCumulativeEnergies));
        setSeamGraph();
        seamGraph.removeSeams(2);
        assertThat(getCumulativeEnergies(pixelGraph), is(correctCumulativeEnergies));
    }



}
