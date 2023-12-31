package PARCIALIII.transformations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class RotatingCubeNoTras extends JPanel implements ActionListener {

    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private double angle = 40;  // Ángulo de rotación alrededor del eje y
    private double[][] cubeVertices;
    private int[][] cubeEdges;
    private int[][] cubeFaces;
    private Color backgroundColor = Color.BLACK;
    private Color cubeColor = Color.blue; // Relleno rosa
    private Color borderColor = Color.BLACK; // Contorno negro
    private boolean increasing = true;

    public RotatingCubeNoTras() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();
        Timer timer = new Timer(100, this);
        timer.start();

        cubeVertices = new double[][]{
                {-25, -25, -25, 1}, // Cambiado a coordenada homogénea 1
                {25, -25, -25, 1},
                {25, 25, -25, 1},
                {-25, 25, -25, 1},
                {-25, -25, 25, 1},
                {25, -25, 25, 1},
                {25, 25, 25, 1},
                {-25, 25, 25, 1}
        };

        cubeEdges = new int[][]{
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        cubeFaces = new int[][]{
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {0, 1, 5, 4},
                {2, 3, 7, 6},
                {0, 3, 7, 4},
                {1, 2, 6, 5}
        };
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);

             
       //rotacion en y 
      // double[][] rotationMatrix = {
       // {Math.cos(angle), 0, -Math.sin(angle), 0},
      //  {0, 1, 0, 0},
      //  {Math.sin(angle), 0, Math.cos(angle), 0},
      //  {0, 0, 0, 1}
//};

//rotacion en x
double[][] rotationMatrix = {
        {1, 0, 0, 0},
        {0, Math.cos(angle),Math.sin(angle), 0},
        {0, -Math.sin(angle),Math.cos(angle), 0},
        {0, 0, 0, 1}
};

        double[][] transformationMatrix = rotationMatrix;

        double[][] cubeTransformed = new double[cubeVertices.length][4];

        for (int i = 0; i < cubeVertices.length; i++) {
            cubeTransformed[i] = multiplyMatrixAndPoint(transformationMatrix, cubeVertices[i]);
        }

        drawCustomFilledCube(cubeTransformed, cubeEdges, cubeFaces, cubeColor, borderColor);

        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += Math.toRadians(65); // Aumentar el ángulo en radianes
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Cube Animation (x or y)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        RotatingCubeNoTras animation = new RotatingCubeNoTras();
        frame.add(animation);
        frame.setVisible(true);
    }

    private double[] multiplyMatrixAndPoint(double[][] matrix, double[] point) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * point[j];
            }
            result[i] = sum;
        }

        return result;
    }

    private void drawCustomFilledCube(double[][] vertices, int[][] edges, int[][] faces, Color fillColor, Color borderColor) {
        graPixel.setColor(fillColor);

        for (int[] face : faces) {
            int[] xPoints = new int[face.length];
            int[] yPoints = new int[face.length];

            for (int i = 0; i < face.length; i++) {
                xPoints[i] = (int) (vertices[face[i]][0] + WIDTH / 2);
                yPoints[i] = (int) (vertices[face[i]][1] + HEIGHT / 2);
            }

            drawFilledPolygon(xPoints, yPoints, fillColor);
        }

        graPixel.setColor(borderColor);
        for (int[] edge : edges) {
            int x1 = (int) (vertices[edge[0]][0] + WIDTH / 2);
            int y1 = (int) (vertices[edge[0]][1] + HEIGHT / 2);
            int x2 = (int) (vertices[edge[1]][0] + WIDTH / 2);
            int y2 = (int) (vertices[edge[1]][1] + HEIGHT / 2);

            drawBresenhamLine(x1, y1, x2, y2);
        }
    }

    private void drawFilledPolygon(int[] xPoints, int[] yPoints, Color fillColor) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Encontrar los límites verticalmente
        for (int y : yPoints) {
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        // Iterar verticalmente dentro de los límites de la figura
        for (int y = minY; y <= maxY; y++) {
            ArrayList<Integer> intersections = new ArrayList<>();

            // Encontrar intersecciones con las aristas
            for (int i = 0; i < xPoints.length; i++) {
                int j = (i + 1) % xPoints.length;

                int yi = yPoints[i];
                int yj = yPoints[j];

                if ((yi <= y && yj > y) || (yj <= y && yi > y)) {
                    double x = (double) (xPoints[i] + (double) (y - yi) / (double) (yj - yi) * (xPoints[j] - xPoints[i]));

                    // Evitar que las intersecciones estén fuera de los límites horizontales
                    if (x >= 0 && x < WIDTH) {
                        intersections.add((int) x);
                    }
                }
            }

            // Ordenar las intersecciones y dibujar líneas horizontales entre pares de intersecciones
            Collections.sort(intersections);
            for (int i = 0; i < intersections.size(); i += 2) {
                int x1 = intersections.get(i);
                int x2 = intersections.get(i + 1);

                for (int x = x1; x <= x2; x++) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }

    private void drawBresenhamLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, borderColor);

            if ((x1 == x2) && (y1 == y2)) break;

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

    private double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        int m1Rows = matrix1.length;
        int m1Cols = matrix1[0].length;
        int m2Rows = matrix2.length;
        int m2Cols = matrix2[0].length;

        if (m1Cols != m2Rows) {
            throw new IllegalArgumentException("Matrices cannot be multiplied");
        }

        double[][] result = new double[m1Rows][m2Cols];

        for (int i = 0; i < m1Rows; i++) {
            for (int j = 0; j < m2Cols; j++) {
                for (int k = 0; k < m1Cols; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }
}
