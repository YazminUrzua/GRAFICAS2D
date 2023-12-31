package PARCIALI;

import java.awt.Color;

public class middleCircle {
   private PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public middleCircle() {
    pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel 
    }

    public void drawCircle(int centerX, int centerY, int radius) {
        int x = radius;
        int y = 0;
        int radiusError = 1 - x; //variable que se utiliza para controlar cómo se dibujan los puntos del círculo

        while (x >= y) {
            drawCirclePoints(centerX, centerY, x, y);
            
            y++;
            
            if (radiusError < 0) {
                radiusError += 2 * y + 1; //fórmula especificada por el algoritmo de Bresenham
            } else {
                x--;
                radiusError += 2 * (y - x) + 1; //actualiza el radiusError
            }
        }
       
    }

    private void drawCirclePoints(int centerX, int centerY, int x, int y) {
        Color color = Color.GREEN;
        pixel.putPixel(centerX, centerY, Color.blue);
        pixel.putPixel(centerX + x, centerY + y, color);
        pixel.putPixel(centerX - x, centerY + y, color);
        pixel.putPixel(centerX + x, centerY - y, color);
        pixel.putPixel(centerX - x, centerY - y, color);
        pixel.putPixel(centerX + y, centerY + x, color);
        pixel.putPixel(centerX - y, centerY + x, color);
        pixel.putPixel(centerX + y, centerY - x, color);
        pixel.putPixel(centerX - y, centerY - x, color);
    }

    public static void main(String[] args) {
        
        middleCircle drawer = new middleCircle();

        int centerX = 80;
        int centerY = 80;
        int radius = 40;
        

        drawer.drawCircle(centerX, centerY, radius);
    }
}

