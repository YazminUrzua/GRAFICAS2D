package PARCIALI;

import java.awt.Color;

public class PolarCoordinatesCircle {
    private PARCIALI.pixel pixel;

    public PolarCoordinatesCircle() {
        pixel = new PARCIALI.pixel();
    }

    public void drawCircle(int xc, int yc, int radius) {
        //Ciclo for que recorre seún los pasos establecidos
        for (int angulo = 0; angulo < 360; angulo++) {
            double radians = Math.toRadians(angulo);
            int x = (int) (radius * Math.cos(radians));
            int y = (int) (radius * Math.sin(radians));

            pixel.putPixel(xc + x, yc + y, Color.magenta);
            pixel.putPixel(xc - x, yc + y, Color.magenta);
            pixel.putPixel(xc + x, yc - y, Color.magenta);
            pixel.putPixel(xc - x, yc - y, Color.magenta);
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(xc, yc, Color.BLACK);
    }

    public static void main(String[] args) {
        PolarCoordinatesCircle drawer = new PolarCoordinatesCircle();

        int centerX = 80;
        int centerY = 80;
        int radius = 40;

        drawer.drawCircle(centerX, centerY, radius);
    }
}
