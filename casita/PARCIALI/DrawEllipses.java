package PARCIALI;

import java.awt.Color;

public class DrawEllipses {
    private pixel pixel;

    public DrawEllipses() {
        pixel = new pixel();
    }

    public void drawEllipse(int centerX, int centerY, int semiMajorAxis, int semiMinorAxis) {
        //para establecer las veces que se dara la vuelta 
        for (double theta = 0; theta <= 2 * Math.PI; theta += 0.01) {
            double x = centerX + semiMajorAxis * Math.cos(theta);
            double y = centerY + semiMinorAxis * Math.sin(theta);
            pixel.putPixel((int) x, (int) y, Color.magenta);
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(centerX, centerY, Color.BLACK);
    }

    public static void main(String[] args) {
        DrawEllipses drawer = new DrawEllipses();

        int centerX = 150;
        int centerY = 150;
        int semiMajorAxis = 80;
        int semiMinorAxis = 40;

        drawer.drawEllipse(centerX, centerY, semiMajorAxis, semiMinorAxis);
       
    }
}
