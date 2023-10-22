package PARCIALII.curvas;

import java.awt.Color;

public class sun {
    private final PARCIALI.pixel pixel; // Instancia de la clase Pixel
    private double rotationAngle; // Ángulo de rotación
    private int prevX = 0; // Coordenada X del punto previo
    private int prevY = 0; // Coordenada Y del punto previo

    public sun() {
        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel
        rotationAngle = 0; // Inicializa el ángulo de rotación
    }

    // Algoritmo de Bresenham modificado para dibujar líneas entre puntos
    public void drawBresenhamLine(int x1, int y1, int x2, int y2, Color color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int err = dx - dy;
        int xStep = x1 < x2 ? 1 : -1;
        int yStep = y1 < y2 ? 1 : -1;

        while (x1 != x2 || y1 != y2) {
            pixel.putPixel(x1, y1, color);
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

    // Función para aplicar una transformación a un punto (x, y) usando una matriz de transformación
    private void transformPoint(double[] point, double[][] transformationMatrix) {
        double x = point[0];
        double y = point[1];
        double w = point[2];
        point[0] = x * transformationMatrix[0][0] + y * transformationMatrix[0][1] + w * transformationMatrix[0][2];
        point[1] = x * transformationMatrix[1][0] + y * transformationMatrix[1][1] + w * transformationMatrix[1][2];
        point[2] = x * transformationMatrix[2][0] + y * transformationMatrix[2][1] + w * transformationMatrix[2][2];
    }

    public void drawRotatingSun() {
        int Points = 1000; // Aumenta el número de puntos para una curva más suave
        double π = Math.PI;

        for (int i = 0; i < Points; i++) {
            double t = (i / (double) (Points - 1)) * 14 * π; // Rango de t [0, 14π]
            double x = 17 * Math.cos(t) + 7 * Math.cos((17.0 / 7.0) * t);
            double y = 17 * Math.sin(t) - 7 * Math.sin((17.0 / 7.0) * t);

            // Aplicar rotación y traslación a cada punto usando matrices homogéneas
            double[] point = {x, y, 1}; // Agregar el componente w

            // Matriz de rotación
            double cosTheta = Math.cos(rotationAngle);
            double sinTheta = Math.sin(rotationAngle);
            double[][] rotationMatrix = {
                {cosTheta, -sinTheta, 0},
                {sinTheta, cosTheta, 0},
                {0, 0, 1}
            };

            double translateX = 300; // Nueva posición en X
            double translateY = 200; // Nueva posición en Y
            double[][] translationMatrix = {
                {1, 0, translateX},
                {0, 1, translateY},
                {0, 0, 1}
            };

            // Multiplicar matrices de traslación y rotación
            double[][] transformationMatrix = multiplyMatrices(translationMatrix, rotationMatrix);

            transformPoint(point, transformationMatrix);

            x = point[0];
            y = point[1];

            // Mapear los valores de x e y a las coordenadas de la ventana
            int xCoord = (int) x;
            int yCoord = (int) y;

            if (i > 0) {
                drawBresenhamLine(prevX, prevY, xCoord, yCoord, Color.ORANGE);
            }

            prevX = xCoord;
            prevY = yCoord;
        }

        // Actualizar el ángulo de rotación para la próxima iteración
        rotationAngle += 0.01; // Ajusta la velocidad de rotación según sea necesario
    }

    private double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        double[][] result = new double[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                double sum = 0;
                for (int k = 0; k < cols1; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        sun sunDrawer = new sun();
        while (true) {
            sunDrawer.drawRotatingSun();
            sunDrawer.pixel.repaint(); // Actualizar el dibujo en la ventana
            try {
                Thread.sleep(100); // Pausa más larga para reducir el parpadeo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
