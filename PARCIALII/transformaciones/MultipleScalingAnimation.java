package PARCIALII.transformaciones;

import java.awt.Color;

public class MultipleScalingAnimation {
    private final PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public MultipleScalingAnimation() {
        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel
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

    // Método para aplicar una matriz de escala homogénea a un punto (x, y)
    public void applyScalingMatrix(double[][] scalingMatrix, double[] point) {
        double x = point[0];
        double y = point[1];
        double w = point[2];

        point[0] = scalingMatrix[0][0] * x / w;
        point[1] = scalingMatrix[1][1] * y / w;
    }

    public void drawScalingAnimation() {
        int numPoints = 50; // Aumenta el número de puntos para una curva más suave
        double π = Math.PI;

        int[][] positions = {
            {30, 400},
            {600, 400},
            {400, 100},
            {400, 700}
        };

        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE};

        for (int j = 0; j < positions.length; j++) {
            int[] position = positions[j];

            // Definir la matriz de escala homogénea inicial
            double[][] scalingMatrix = {
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0}
            };

            for (int step = 0; step < 100; step++) {
                // Actualizar la matriz de escala en cada paso
                double scaleFactor = 1.0 - 0.01 * step; // 0.01 es la velocidad de la animación
                scalingMatrix[0][0] = scaleFactor;
                scalingMatrix[1][1] = scaleFactor;

                int prevX = 0;
                int prevY = 0;

                for (int i = 0; i < numPoints; i++) {
                    double t = (i / (double) (numPoints - 1)) * 2 * π;
                    double r = 2;
                    double x = r * Math.cos(t) / (1 + Math.pow(Math.sin(t), 2));
                    double y = r * Math.cos(t) * Math.sin(t) / (1 + Math.pow(Math.sin(t), 2));

                    // Aplicar la matriz de escala
                    double[] point = {x, y, 1};
                    applyScalingMatrix(scalingMatrix, point);
                    x = point[0];
                    y = point[1];

                    // Aplicar traslación para centrar en el centro del frame
                    double xTranslated = x * 50 + position[0];
                    double yTranslated = y * 50 + position[1];
                    // Mapear los valores de x e y a las coordenadas de la ventana
                    int xCoord = (int) xTranslated;
                    int yCoord = (int) yTranslated;

                    if (i > 0) {
                        drawBresenhamLine(prevX, prevY, xCoord, yCoord, colors[j]);
                    }

                    prevX = xCoord;
                    prevY = yCoord;
                }

                // Pequeña pausa para la animación
                try {
                    Thread.sleep(50); // Ajusta el valor para cambiar la velocidad
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

               // pixel.clear(); // Borra el frame anterior para la nueva iteración
            }
        }
    }

    public static void main(String[] args) {
        MultipleScalingAnimation scalingAnimation = new MultipleScalingAnimation();
        scalingAnimation.drawScalingAnimation();
    }
}
