package PARCIALI;

import java.awt.Color;

public class circleBasic {
    private PARCIALI.pixel pixel;

    public circleBasic() {
        pixel = new PARCIALI.pixel();
    }

    public void drawCircle(int centerX, int centerY, int radius) {
        int x = radius;
        int y = 0;
        int radiusError = 1 - x;
        Color color = Color.BLUE;

        while (x >= y) {
            pixel.putPixel(centerX + x, centerY + y, color);
            pixel.putPixel(centerX - x, centerY + y, color);
            pixel.putPixel(centerX - x, centerY - y, color);
            pixel.putPixel(centerX + x, centerY - y, color);
            pixel.putPixel(centerX + y, centerY + x, color);
            pixel.putPixel(centerX - y, centerY + x, color);
            pixel.putPixel(centerX - y, centerY - x, color);
            pixel.putPixel(centerX + y, centerY - x, color);

            y++;

            if (radiusError < 0) {
                radiusError += 2 * y + 1;
            } else {
                x--;
                radiusError += 2 * (y - x + 1);
            }
             pixel.putPixel(centerX, centerY, Color.red);
        }
       

    }

    public static void main(String[] args) {
        circleBasic drawer = new circleBasic();

        int centerX = 80;
        int centerY = 80;
        int radius = 40;

        drawer.drawCircle(centerX, centerY, radius);
    }
}
