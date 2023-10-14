package PARCIALII.mallado;

import java.awt.Color;

public class MeshWithMatrix {
    private PARCIALI.pixel pixel;

    public MeshWithMatrix() {
        pixel = new PARCIALI.pixel();
    }
    public void drawCircleWithMesh(int xc, int yc, int radio, int meshSpacing) {
        // Crea una matriz de puntos
        int[][] pointsMatrix = createPointsMatrix(xc, yc, radio, meshSpacing);
        
        // Dibuja los triángulos en el mallado
        for (int i = 0; i < pointsMatrix.length; i++) {
            int x = pointsMatrix[i][0];
            int y = pointsMatrix[i][1];
            
            try {
                Thread.sleep(5);
            } catch (Exception e) {
                // TODO: handle exception
            }
            // Dibuja un píxel para representar cada vértice del triángulo
            pixel.putPixel(x, y, Color.GRAY);
        
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
        MeshWithMatrix drawer = new MeshWithMatrix();

        int centerX = 250;
        int centerY = 250;
        int radius = 200;
        int meshSpacing = 20;

        drawer.drawCircleWithMesh(centerX, centerY, radius, meshSpacing);
    }
}
