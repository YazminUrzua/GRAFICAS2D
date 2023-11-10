package PARCIALIII;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class cubo extends JFrame {

    private BufferedImage buffer;

    public cubo() {
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

        int[][] cubePoints = new int[][] {
            {-halfSize, -halfSize, -halfSize},
            {halfSize, -halfSize, -halfSize},
            {halfSize, halfSize, -halfSize},
            {-halfSize, halfSize, -halfSize},
            {-halfSize, -halfSize, halfSize},
            {halfSize, -halfSize, halfSize},
            {halfSize, halfSize, halfSize},
            {-halfSize, halfSize, halfSize}
        };

        int[][] lines = new int[][] {
            {0, 1}, {1, 2}, {2, 3}, {3, 0},
            {4, 5}, {5, 6}, {6, 7}, {7, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (int[] point : cubePoints) {
            int x = point[0] + centerX;
            int y = point[1] + centerY;
            int z = point[2];

            // Aplicar proyección en perspectiva
            double zp = projectionVector[2];
            double xp = projectionVector[0];
            double yp = projectionVector[1];

            double projectedZ = -z / zp;
            int projectedX = (int) (x + xp * projectedZ);
            int projectedY = (int) (y + yp * projectedZ);

            putPixel(projectedX, projectedY, Color.blue);
        }

        for (int[] line : lines) {
            int x1 = cubePoints[line[0]][0] + centerX;
            int y1 = cubePoints[line[0]][1] + centerY;
            int z1 = cubePoints[line[0]][2];
            int x2 = cubePoints[line[1]][0] + centerX;
            int y2 = cubePoints[line[1]][1] + centerY;
            int z2 = cubePoints[line[1]][2];

            // Aplicar proyección en perspectiva a los puntos de la línea
            double zp = projectionVector[2];
            double xp = projectionVector[0];
            double yp = projectionVector[1];

            double projectedZ1 = -z1 / zp;
            double projectedZ2 = -z2 / zp;

            int projectedX1 = (int) (x1 + xp * projectedZ1);
            int projectedY1 = (int) (y1 + yp * projectedZ1);
            int projectedX2 = (int) (x2 + xp * projectedZ2);
            int projectedY2 = (int) (y2 + yp * projectedZ2);

            drawLineBresenham(projectedX1, projectedY1, projectedX2, projectedY2, new Color(0xebb7ce));
        }

        repaint();
    }

    public void drawLineBresenham(int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, c);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        cubo app = new cubo();
        app.setVisible(true);

        // Definir el vector de proyección (xp, yp, zp)
        double[] projectionVector = {8, -25, 15};

        app.drawCube(100, projectionVector); // Tamaño del cubo y vector de proyección
    }
}
