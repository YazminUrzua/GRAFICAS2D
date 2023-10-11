package PARCIALII.curvas;

import java.awt.Color;

public class haze {
    private final PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public haze() {
        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel
    }

    // Algoritmo de Bresenham modificado para dibujar líneas entre puntos
    public void drawBresenhamLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int err = dx - dy;
        int xStep = x1 < x2 ? 1 : -1;
        int yStep = y1 < y2 ? 1 : -1;

        while (x1 != x2 || y1 != y2) {
            pixel.putPixel(x1, y1, Color.MAGENTA);
            int err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += xStep;
            }

            if (err2 < dx) {
                err += dx;
                y1 += yStep;
            }
        }
    }

   
    public void humus() {
        int numPoints = 100; // Aumenta el número de puntos para una curva más suave
        double π = Math.PI;
        int prevX = 0;
        int prevY = 0;

        for (int i = 0; i < numPoints; i++) {
            double y = (i / (double) (numPoints - 1)) * 2*π;
            double s = 4*y;
            double x = y*Math.cos(s);

            // Mapear los valores de x e y a las coordenadas de la ventana
            int xCoord = (int) (x * 100); // Ajusta este valor según el tamaño de tu ventana
            int yCoord = (int) (y * 100); // Ajusta este valor según el tamaño de tu ventana

            if (i > 0) {
                drawBresenhamLine(prevX, prevY, xCoord, yCoord);
            }

            prevX = xCoord;
            prevY = yCoord;
        }
    }

    public static void main(String[] args) {
        haze curveDrawer = new haze();
        curveDrawer.humus();
    }
}
