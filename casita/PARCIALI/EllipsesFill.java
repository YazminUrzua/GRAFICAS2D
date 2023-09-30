package PARCIALI;

import java.awt.Color;

public class EllipsesFill {
    private pixel pixel;

    public EllipsesFill() {
        pixel = new pixel();
    }

    public void drawEllipse(int centerX, int centerY, int semiMajorAxis, int semiMinorAxis) {
        // Rellena la elipse
        for (int x = centerX - semiMajorAxis; x <= centerX + semiMajorAxis; x++) {
            for (int y = centerY - semiMinorAxis; y <= centerY + semiMinorAxis; y++) {
                double value = Math.pow((x - centerX) / (double) semiMajorAxis, 2)
                             + Math.pow((y - centerY) / (double) semiMinorAxis, 2);
                if (value <= 1) {
                    pixel.putPixel(x, y, Color.magenta);
                }
            }
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(centerX, centerY, Color.BLACK);
        
        // Dibuja la circunferencia de la elipse
        for (double angulo = 0; angulo <= 2 * Math.PI; angulo += 0.01) {
            double x = centerX + semiMajorAxis * Math.cos(angulo);
            double y = centerY + semiMinorAxis * Math.sin(angulo);
            pixel.putPixel((int) x, (int) y, Color.magenta);
        }
    }

    public static void main(String[] args) {
        EllipsesFill drawer = new EllipsesFill();

        int centerX = 150;
        int centerY = 150;
        int semiMajorAxis = 80;
        int semiMinorAxis = 40;

        drawer.drawEllipse(centerX, centerY, semiMajorAxis, semiMinorAxis);
    }
}
