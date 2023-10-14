package PARCIALII.mallado;

import java.awt.Color;

public class SimpleMeshWithMatrix {
    private PARCIALI.pixel pixel;

    public SimpleMeshWithMatrix() {
        pixel = new PARCIALI.pixel();
    }

    public void drawCircleWithMesh(int xc, int yc, int radio, int meshSpacing) {
        // Crea una matriz de puntos
        int[][] pointsMatrix = createPointsMatrix(xc, yc, radio, meshSpacing);
    
        // Dibuja los contornos de los rombos o rectángulos en el mallado
        for (int i = 0; i < pointsMatrix.length; i++) {
            int x = pointsMatrix[i][0];
            int y = pointsMatrix[i][1];
            int halfSpacing = meshSpacing / 2;
            
            // Dibuja los contornos del rombo o rectángulo
            for (int dx = -halfSpacing; dx <= halfSpacing; dx++) {
                pixel.putPixel(x + dx, y - halfSpacing, Color.GRAY);
                pixel.putPixel(x + dx, y + halfSpacing, Color.GRAY);
                pixel.putPixel(x - halfSpacing, y + dx, Color.GRAY);
                pixel.putPixel(x + halfSpacing, y + dx, Color.GRAY);
            }
        }
    
        // Dibuja el círculo
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
            // Pinta las coordenadas del centro
            pixel.putPixel(xc, yc, Color.BLACK);
        }
    }
    
    
    // Crea una matriz de puntos del mallado
    private int[][] createPointsMatrix(int xc, int yc, int radio, int meshSpacing) {
        int numPoints = (2 * radio / meshSpacing + 1) * (2 * radio / meshSpacing + 1);
        int[][] pointsMatrix = new int[numPoints][2];
        int index = 0;
        for (int x = -radio; x <= radio; x += meshSpacing) {
            for (int y = -radio; y <= radio; y += meshSpacing) {
                if (x * x + y * y <= radio * radio) {
                    pointsMatrix[index][0] = xc + x;
                    pointsMatrix[index][1] = yc + y;
                    index++;
                }
            }
        }
        return pointsMatrix;
    }

    public static void main(String[] args) {
        SimpleMeshWithMatrix drawer = new SimpleMeshWithMatrix();

        int centerX = 250;
        int centerY = 250;
        int radius = 200;
        int meshSpacing = 10;

        drawer.drawCircleWithMesh(centerX, centerY, radius, meshSpacing);
    }
}
