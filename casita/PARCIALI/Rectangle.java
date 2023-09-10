package PARCIALI;

import java.awt.Color;

public class Rectangle {
    private pixel pixel;

    public Rectangle() {
        pixel = new pixel();
    }

    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        // Dibuja los cuatro lados del rectángulo
        for (int x = centerX - halfWidth; x <= centerX + halfWidth; x++) {
            pixel.putPixel(x, centerY - halfHeight, Color.blue);
            pixel.putPixel(x, centerY + halfHeight, Color.blue);
        }
        for (int y = centerY - halfHeight; y <= centerY + halfHeight; y++) {
            pixel.putPixel(centerX - halfWidth, y, Color.blue);
            pixel.putPixel(centerX + halfWidth, y, Color.blue);
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