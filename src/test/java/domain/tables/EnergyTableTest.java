package domain.tables;

import domain.tables.EnergyTable;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static utilities.TestUtils.getImageFromRgbArray;

public class EnergyTableTest {

    @Test
    public void getDualGradientEnergiesWorksCorrectly() {
        int[][] rgbValues = {
                {255, 101, 51}, {255, 101, 153}, {255, 101, 255},
                {255, 153, 51}, {255, 153, 153}, {255, 153, 255},
                {255, 203, 51}, {255, 204, 153}, {255, 205, 255},
                {255, 255, 51}, {255, 255, 153}, {255, 255, 255}
        };
        BufferedImage image = getImageFromRgbArray(rgbValues, 3, 4, TYPE_INT_ARGB);
        EnergyTable testTable = new EnergyTable(image);
        testTable.computeDualGradientEnergies();
        int[][] correctEnergies = {
                {20808, 52020, 20808},
                {20808, 52225, 21220},
                {20809, 52024, 20809},
                {20808, 52225, 21220}
        };
        assertThat(testTable.getEnergyArray(), is(correctEnergies));
    }

}
