package PARCIALII.triangulosRombos;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class TriangleAndDiamond {
    private PARCIALI.pixel pixel;

    public TriangleAndDiamond() {
        pixel = new PARCIALI.pixel();
    }

    public void drawDiamond(int centerX, int centerY, int size) {
        // Rellena el rombo
        for (int x = centerX - size; x <= centerX + size; x++) {
            for (int y = centerY - size; y <= centerY + size; y++) {
                if (Math.abs(x - centerX) + Math.abs(y - centerY) <= size) {
                    pixel.putPixel(x, y, Color.MAGENTA);
                }
            }
        }

        // Pinta el centro del rombo
        pixel.putPixel(centerX, centerY, Color.BLACK);
    }

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        // Encuentra las coordenadas mínimas y máximas que cubren el triángulo
        int minX = Math.min(x1, Math.min(x2, x3));
        int minY = Math.min(y1, Math.min(y2, y3));
        int maxX = Math.max(x1, Math.max(x2, x3));
        int maxY = Math.max(y1, Math.max(y2, y3));

        // Rellena el triángulo
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (isInsideTriangle(x1, y1, x2, y2, x3, y3, x, y)) {
                    pixel.putPixel(x, y, Color.CYAN);
                }
            }
        }

        // Pinta los vértices del triángulo
        pixel.putPixel(x1, y1, Color.BLACK);
        pixel.putPixel(x2, y2, Color.BLACK);
        pixel.putPixel(x3, y3, Color.BLACK);
    }

    // Método para comprobar si un punto (x, y) está dentro de un triángulo
    private boolean isInsideTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y) {
        int area = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2);
        int area1 = Math.abs((x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2)) / 2);
        int area2 = Math.abs((x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y)) / 2);
        int area3 = Math.abs((x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2)) / 2);

        return area == area1 + area2 + area3;
    }

    public static void main(String[] args) {
   
        TriangleAndDiamond drawer = new TriangleAndDiamond();

        int centerX = 150;
        int centerY = 150;
        int size = 80;

        int x1 = 250;
        int y1 = 250;
        int x2 = 300;
        int y2 = 300;
        int x3 = 250;
        int y3 = 400;

        drawer.drawDiamond(centerX, centerY, size);
        drawer.drawTriangle(x1, y1, x2, y2, x3, y3);
    }

}
