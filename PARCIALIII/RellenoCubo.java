package PARCIALIII;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class RellenoCubo extends JFrame {

    private BufferedImage buffer;

    public RellenoCubo() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    public void drawCube(int size, double[] projectionVector) {
        int halfSize = size / 2;

        int[][] cubePoints = new int[][]{
                {-halfSize, -halfSize, -halfSize},
                {halfSize, -halfSize, -halfSize},
                {halfSize, halfSize, -halfSize},
                {-halfSize, halfSize, -halfSize},
                {-halfSize, -halfSize, halfSize},
                {halfSize, -halfSize, halfSize},
                {halfSize, halfSize, halfSize},
                {-halfSize, halfSize, halfSize}
        };

        int[][] faces = new int[][]{
                {0, 4, 5, 1},    // Cara derecha
                {2, 3, 7, 6},    // Cara superior (Color.PINK)
                {2, 6, 7, 3},    // Cara trasera
                {0, 1, 5, 4},    // Cara izquierda
                {4, 5, 6, 7}     // Cara inferior
        };

        Color[] colors = new Color[]{
                Color.GREEN,     // Cara derecha
                Color.RED,       // Cara superior (Color.PINK)
                Color.BLUE,      // Cara trasera
                Color.GREEN,     // Cara izquierda
                Color.PINK       // Cara inferior
        };

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Pintar caras laterales en verde
        fillSideFaces(cubePoints, centerX, centerY, projectionVector);

        for (int i = 0; i < faces.length; i++) {
            int[] face = faces[i];
            Color fillColor = colors[i];

            int[] xPoints = new int[face.length];
            int[] yPoints = new int[face.length];

            for (int j = 0; j < face.length; j++) {
                int pointIndex = face[j];
                int x = cubePoints[pointIndex][0] + centerX;
                int y = cubePoints[pointIndex][1] + centerY;
                int z = cubePoints[pointIndex][2];

                // Aplicar proyección en perspectiva
                double zp = projectionVector[2];
                double xp = projectionVector[0];
                double yp = projectionVector[1];

                double projectedZ = -z / zp;
                xPoints[j] = (int) (x + xp * projectedZ);
                yPoints[j] = (int) (y + yp * projectedZ);
            }

            fillPolygonScanLine(xPoints, yPoints, fillColor);
        }

        repaint();
    }

    public void fillPolygonScanLine(int[] xPoints, int[] yPoints, Color fillColor) {
        int minY = Arrays.stream(yPoints).min().orElse(0);
        int maxY = Arrays.stream(yPoints).max().orElse(0);

        for (int y = minY; y < maxY; y++) {
            int[] intersections = getIntersections(xPoints, yPoints, y);

            for (int i = 0; i < intersections.length - 1; i += 2) {
                int startX = intersections[i];
                int endX = intersections[i + 1];

                for (int x = startX; x < endX; x++) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }

    private int[] getIntersections(int[] xPoints, int[] yPoints, int scanY) {
        int count = xPoints.length;
        int[] intersections = new int[count * 2];
        int index = 0;

        for (int i = 0; i < count; i++) {
            int next = (i + 1) % count;

            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[next];
            int y2 = yPoints[next];

            if ((y1 <= scanY && y2 > scanY) || (y2 <= scanY && y1 > scanY)) {
                double t = (double) (scanY - y1) / (y2 - y1);
                intersections[index++] = (int) (x1 + t * (x2 - x1));
            }
        }

        Arrays.sort(intersections, 0, index);
        return intersections;
    }

    private void fillSideFaces(int[][] cubePoints, int centerX, int centerY, double[] projectionVector) {
        int[][] sideFaces = new int[][]{
                {0, 1, 2, 3},    // Cara izquierda (Color.GREEN)
                {1, 2, 6, 5},    // Cara frontal (Color.RED)
                {2, 3, 7, 6},    // Cara superior (Color.PINK)
                {0, 4, 5, 1},    // Cara derecha (Color.GREEN)
                {4, 5, 6, 7}     // Cara inferior (Color.PINK)
        };

        Color[] sideFaceColors = new Color[]{
                Color.GREEN,     // Cara izquierda
                Color.RED,       // Cara frontal
                Color.PINK,      // Cara superior
                Color.GREEN,     // Cara derecha
                Color.PINK       // Cara inferior
        };

        for (int i = 0; i < sideFaces.length; i++) {
            int[] sideFace = sideFaces[i];
            Color sideFaceColor = sideFaceColors[i];

            int[] xPoints = new int[sideFace.length];
            int[] yPoints = new int[sideFace.length];

            for (int j = 0; j < sideFace.length; j++) {
                int pointIndex = sideFace[j];
                int x = cubePoints[pointIndex][0] + centerX;
                int y = cubePoints[pointIndex][1] + centerY;
                int z = cubePoints[pointIndex][2];

                // Aplicar proyección en perspectiva
                double zp = projectionVector[2];
                double xp = projectionVector[0];
                double yp = projectionVector[1];

                double projectedZ = -z / zp;
                xPoints[j] = (int) (x + xp * projectedZ);
                yPoints[j] = (int) (y + yp * projectedZ);
            }

            fillPolygonScanLine(xPoints, yPoints, sideFaceColor);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        RellenoCubo app = new RellenoCubo();
        app.setVisible(true);

        // Definir el vector de proyección (xp, yp, zp)
        double[] projectionVector = {-2, 5, 15};

        app.drawCube(100, projectionVector); // Tamaño del cubo y vector de proyección
    }
}
