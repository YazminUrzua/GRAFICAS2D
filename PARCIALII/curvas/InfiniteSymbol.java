package PARCIALII.curvas;
import java.awt.Color;

public class InfiniteSymbol {
    private final PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public InfiniteSymbol() {
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
            pixel.putPixel(x1, y1, Color.BLUE);
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

    public void drawInfinitySymbol() {
        int numPoints = 50; // Aumenta el número de puntos para una curva más suave
        double π = Math.PI;
        int prevX = 0;
        int prevY = 0;

        for (int i = 0; i < numPoints; i++) {
            double t = (i / (double) (numPoints - 1)) * 2 * π;
            double r = 2;
            double x = r * Math.cos(t) / (1 + Math.pow(Math.sin(t), 2));
            double y = r * Math.cos(t) * Math.sin(t) / (1 + Math.pow(Math.sin(t), 2));

            // Aplicar traslación para centrar en el centro del frame
            double xTranslated = x * 50 + 250; // Centrar en el centro del frame
            double yTranslated = y * 50 + 250; // Centrar en el centro del frame

            // Mapear los valores de x e y a las coordenadas de la ventana
            int xCoord = (int) xTranslated;
            int yCoord = (int) yTranslated;

            if (i > 0) {
                drawBresenhamLine(prevX, prevY, xCoord, yCoord);
            }

            prevX = xCoord;
            prevY = yCoord;
        }
    }

    public static void main(String[] args) {
        InfiniteSymbol infiniteDrawer = new InfiniteSymbol();
        infiniteDrawer.drawInfinitySymbol();
    }
}
