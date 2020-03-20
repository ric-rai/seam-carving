import domain.EnergyTable;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnergyTableTest {

    @Test
    public void simpleArrayTest() {
        int[][] rgbValues = {
                {255, 101, 51}, {255, 101, 153}, {255, 101, 255},
                {255, 153, 51}, {255, 153, 153}, {255, 153, 255},
                {255, 203, 51}, {255, 204, 153}, {255, 205, 255},
                {255, 255, 51}, {255, 255, 153}, {255, 255, 255}
        };
        EnergyTable energyTable = new EnergyTable(getImageFromArray(rgbValues, 3, 4));
        energyTable.getDualGradientEnergies().forEach(
                l -> l.forEach(System.out::println)
        );
    }

    public Image getImageFromArray(int[][] rgbValues, int width, int height) {
        int[] pixelValues = new int[rgbValues.length];
        for (int[] rgbValue : rgbValues) new Color(rgbValue[0], rgbValue[1], rgbValue[2]).getRGB();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        image.setRGB(0, 0, width, height, pixelValues, 0, width);
        return image;
    }
}
