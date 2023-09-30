package PARCIALII;
import java.awt.Color;

public class FloodFillCircle {
    private PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public FloodFillCircle() {
        pixel = new PARCIALI.pixel();
    }

    public void drawCircle(int xc, int yc, int radio) {
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));

            // Son ocho cuadrantes para que salga completo
            pixel.putPixel(xc + x, yc + y, Color.ORANGE);
            pixel.putPixel(xc - x, yc + y, Color.ORANGE);
            pixel.putPixel(xc + x, yc - y, Color.ORANGE);
            pixel.putPixel(xc - x, yc - y, Color.ORANGE);
            pixel.putPixel(xc + y, yc + x, Color.ORANGE);
            pixel.putPixel(xc - y, yc + x, Color.ORANGE);
            pixel.putPixel(xc + y, yc - x, Color.ORANGE);
            pixel.putPixel(xc - y, yc - x, Color.ORANGE);
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(xc, yc, Color.BLACK);

        // Realiza la inundación (flood fill) en el interior del círculo
        floodFill(xc, yc, Color.ORANGE, Color.BLUE);
    }

    public void floodFill(int x, int y, Color targetColor, Color fillColor) {
        if (!targetColor.equals(pixel.getPixelColor(x, y))) {
            return;
        }

        java.util.Stack<int[]> stack = new java.util.Stack<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] currentPixel = stack.pop();
            int currentX = currentPixel[0];
            int currentY = currentPixel[1];

            if (!targetColor.equals(pixel.getPixelColor(currentX, currentY))) {
                continue;
            }

            pixel.putPixel(currentX, currentY, fillColor);

            int[][] neighbors = {
                {currentX + 1, currentY},
                {currentX - 1, currentY},
                {currentX, currentY + 1},
                {currentX, currentY - 1}
            };

            for (int[] neighbor : neighbors) {
                int neighborX = neighbor[0];
                int neighborY = neighbor[1];

                if (targetColor.equals(pixel.getPixelColor(neighborX, neighborY))) {
                    stack.push(new int[]{neighborX, neighborY});
                }
            }
        }
    }

    public static void main(String[] args) {
        FloodFillCircle frame = new FloodFillCircle();
        frame.drawCircle(80, 80, 40); // Cambia las coordenadas y el radio según tus necesidades
    }
}
