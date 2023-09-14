package PARCIALI;

import java.awt.Color;

public class Ellipses {
    private pixel pixel;

    public Ellipses() {
        pixel = new pixel();
    }

    public void drawEllipse(int centerX, int centerY, int semiMajorAxis, int semiMinorAxis) {
        //para establecer las veces que se dara la vuelta 
        for (double angulo = 0; angulo <= 2 * Math.PI; angulo += 0.01) {
            double x = centerX + semiMajorAxis * Math.cos(angulo);
            double y = centerY + semiMinorAxis * Math.sin(angulo);
            pixel.putPixel((int) x, (int) y, Color.magenta);
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(centerX, centerY, Color.BLACK);
    }

    public static void main(String[] args) {
        Ellipses drawer = new Ellipses();

        int centerX = 150;
        int centerY = 150;
        int semiMajorAxis = 80;
        int semiMinorAxis = 40;

        drawer.drawEllipse(centerX, centerY, semiMajorAxis, semiMinorAxis);
       
    }
}
