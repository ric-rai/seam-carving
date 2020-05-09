package domain.graphs;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

public class PixelGraph implements PixelService {
    private final BufferedImage image;
    private int width;
    private final int height;
    private Pixel leftTop;
    private final RgbValues rgbValues;
    private boolean sizeHasChanged = false;
    private Pixel[][] pixelArray;

    public PixelGraph(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        if (image.getType() == TYPE_3BYTE_BGR) rgbValues = new ThreeByteBgrRgbValues(image);
        else rgbValues = new GeneralRgbValues();
        initGraph();
    }

    private void initGraph() {
        //Leftmost/root pixel
        Pixel[][] pixels = new Pixel[height][width];
        Pixel pixel = leftTop = pixels[0][0] = new Pixel();
        rgbValues.setRgbValuesFromImage(leftTop, 0, 0);
        //Rest on first row
        for (int col = 1; col < width; col++) {
            pixel = pixels[0][col] = new Pixel();
            rgbValues.setRgbValuesFromImage(pixel, 0, col);
            Pixel prev = pixels[0][col - 1];
            pixel.left = prev;
            prev.right = pixel;
        }
        pixel.leftmostOnRow = leftTop;
        leftTop.rightmostOnRow = pixel;
        //From second to second last
        for (int row = 1; row < height - 1; row++) {
            pixel = initFirstPixelOnRow(pixels, row);
            //Rest on row
            for (int col = 1; col < width; col++)
                pixel = initPixel(pixels, row, col);
            pixel.leftmostOnRow = pixels[row][0];
            pixels[row][0].rightmostOnRow = pixel;
        }
        //First on last row
        pixel = initFirstPixelOnRow(pixels, height - 1);
        pixel.topmostOnColumn = leftTop;
        leftTop.bottommostOnColumn = pixel;
        //Rest on last row
        int row = height - 1;
        for (int col = 1; col < width; col++) {
            pixel = initPixel(pixels, row, col);
            pixel.topmostOnColumn = pixels[0][col];
            pixels[0][col].bottommostOnColumn = pixel;
        }
        pixel.leftmostOnRow = pixels[row][0];
        pixels[row][0].rightmostOnRow = pixel;
    }

    private Pixel initFirstPixelOnRow(Pixel[][] pixels, int row) {
        Pixel pixel = pixels[row][0] = new Pixel();
        rgbValues.setRgbValuesFromImage(pixel, row, 0);
        Pixel above = pixels[row - 1][0];
        pixel.above = above;
        above.below = pixel;
        return pixel;
    }

    private Pixel initPixel(Pixel[][] pixels, int row, int col) {
        Pixel pixel = pixels[row][col] = new Pixel();
        rgbValues.setRgbValuesFromImage(pixel, row, col);
        Pixel prev = pixels[row][col - 1];
        pixel.left = prev;
        prev.right = pixel;
        Pixel above = pixels[row - 1][col];
        pixel.above = above;
        above.below = pixel;
        return pixel;
    }

    @Override
    public Pixel getLeftTop() {
        return leftTop;
    }

    @Override
    public void removeVertically(Pixel pixel) {
        while (pixel != null) {
            if (pixel.left == null) {
                pixel.right.left = null;
                Pixel rightmost = pixel.rightmostOnRow;
                pixel.right.rightmostOnRow = rightmost;
                rightmost.leftmostOnRow = pixel.right;
                if (pixel == leftTop) leftTop = pixel.right;
            } else if (pixel.right == null) {
                pixel.left.right = null;
                Pixel leftmost = pixel.leftmostOnRow;
                pixel.left.leftmostOnRow = leftmost;
                leftmost.rightmostOnRow = pixel.left;
            } else {
                pixel.left.right = pixel.right;
                pixel.right.left = pixel.left;
            }
            updateReferencesOnRightSide(pixel);
            pixel = pixel.lowestPredecessor;
        }
        width--;
        sizeHasChanged = true;
    }

    private void updateReferencesOnRightSide(Pixel pixel) {
        if (pixel.below == null) {
            updateBelowReferencesOnRightSide(pixel);
        } else if (pixel.above == null) {
            updateAboveReferencesOnRightSide(pixel);
        } else {
            updateBelowReferencesOnRightSide(pixel);
            updateAboveReferencesOnRightSide(pixel);
        }
    }

    private void updateBelowReferencesOnRightSide(Pixel pixel) {
        Pixel next = pixel.above;
        while (next != null && next.below.right != null) {
            Pixel belowRight = next.below.right;
            next.below = belowRight;
            belowRight.above = next;
            next = next.right;
        }
    }

    private void updateAboveReferencesOnRightSide(Pixel pixel) {
        Pixel next = pixel.below;
        while (next != null && next.above.right != null) {
            Pixel aboveRight = next.above.right;
            next.above = aboveRight;
            aboveRight.below = next;
            next = next.right;
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void removeHorizontally(Pixel pixel) {

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getArgbInt(int row, int col) {
        if (sizeHasChanged) {
            pixelArray = new Pixel[height][width];
            int r = 0, c = 0;
            Pixel pixel = leftTop;
            while (true) {
                pixelArray[r][c] = new Pixel();
                boolean lastOnRow = pixel.right == null;
                if (lastOnRow && pixel.below == null) break;
                if (!lastOnRow) {
                    pixel = pixel.right;
                    c++;
                } else {
                    pixel = pixel.below.leftmostOnRow;
                    r++;
                    c = 0;
                }
            }
            sizeHasChanged = false;
        }
        int red = pixelArray[row][col].red;
        int green = pixelArray[row][col].green;
        int blue = pixelArray[row][col].blue;
        return (((red << 8) + green) << 8) + blue;
    }

    protected interface RgbValues {

        void setRgbValuesFromImage(Pixel pixel, int row, int col);

    }

    private class GeneralRgbValues implements RgbValues {

        public void setRgbValuesFromImage(Pixel pixel, int row, int col) {
            int argbInt = image.getRGB(col, row);
            pixel.red = (argbInt & 0xff0000) >> 16;
            pixel.green = (argbInt & 0xff00) >> 8;
            pixel.blue = argbInt & 0xff;
        }

    }

    private class ThreeByteBgrRgbValues implements RgbValues {
        final byte[] bytes;

        public ThreeByteBgrRgbValues(BufferedImage image) {
            this.bytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        }

        public void setRgbValuesFromImage(Pixel pixel, int row, int col) {
            int collapsedIndex = row * width + col;
            pixel.red = bytes[collapsedIndex * 3 + 2] & 0xFF;
            pixel.green = bytes[collapsedIndex * 3 + 1] & 0xFF;
            pixel.blue = bytes[collapsedIndex * 3] & 0xFF;
        }
    }

}
