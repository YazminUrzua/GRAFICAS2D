package PARCIALI;

import java.awt.Color;

public class Rectangle {
    private pixel pixel;

    public Rectangle() {
        pixel = new PARCIALI.pixel();
    }

    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int ancho = width / 2;
        int alt = height / 2;

        // Dibuja los cuatro lados del rectángulo
        for (int x = centerX - ancho; x <= centerX + ancho; x++) {
            pixel.putPixel(x, centerY - alt, Color.blue);
            pixel.putPixel(x, centerY + alt, Color.blue);
        }
        for (int y = centerY - alt; y <= centerY + alt; y++) {
            pixel.putPixel(centerX - ancho, y, Color.blue);
            pixel.putPixel(centerX + ancho, y, Color.blue);
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(centerX, centerY, Color.BLACK);
    }

    public static void main(String[] args) {
        Rectangle drawer = new Rectangle();

        int centerX = 150;
        int centerY = 150;
        int width = 100;
        int height = 60;

        drawer.drawRectangle(centerX, centerY, width, height);
        
    }
}
