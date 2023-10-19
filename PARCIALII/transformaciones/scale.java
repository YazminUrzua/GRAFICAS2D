package PARCIALII.transformaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Scale extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private int centerX, centerY;
    private int radius = 20; // Radio inicial del círculo
    private double[][] circleVertices; // Matriz de vértices del círculo
    private double[][] transformationMatrix; // Matriz de transformación para escalar
    private Color backgroundColor = Color.BLACK;
    private Color circleColor = Color.GREEN;
    private Timer timer;
    private int numCircles = 5; // Cantidad de círculos en la hilera
    private int circleSpacing = 30; // Espacio entre círculos


    public Scale() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        centerX = WIDTH / 2;
        centerY = HEIGHT / 2;

        // Inicializa la matriz de transformación para escalar
        transformationMatrix = new double[][] {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {0.0, 0.0, 1.0}
        };

        timer = new Timer(100, this); // Temporizador para actualizar la animación
        timer.start();

        // Inicializa la matriz de vértices del círculo
        initializeCircleVertices();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);

        // Aplica la matriz de transformación al círculo
        double[][] scaledCircleVertices = new double[circleVertices.length][3];
        for (int i = 0; i < circleVertices.length; i++) {
            scaledCircleVertices[i] = multiplyMatrixAndPoint(transformationMatrix, circleVertices[i]);
        }

        // Dibuja el círculo escalado y lo rellena
        fillCircle(scaledCircleVertices, circleColor);

        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Incrementa los factores de escala en cada iteración para aumentar el tamaño del círculo
        transformationMatrix[0][0] += 0.1;
        transformationMatrix[1][1] += 0.1;

        // Actualiza el radio del círculo
        radius += 5;

        // Vuelve a dibujar la animación
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Filled Circle Scaling Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        Scale animation = new Scale();
        frame.add(animation);

        frame.setVisible(true);
    }

    private void initializeCircleVertices() {
        int numVertices = 360; // Cantidad de vértices para aproximar el círculo
        circleVertices = new double[numVertices][3];

        for (int i = 0; i < numVertices; i++) {
            double angle = Math.toRadians(i * (360.0 / numVertices));
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            circleVertices[i] = new double[]{x, y, 1.0};
        }
    }

    private void fillCircle(double[][] vertices, Color fillColor) {
        int xc = centerX;
        int yc = centerY;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                double distance = Math.sqrt(x * x + y * y);
                if (distance <= radius) {
                    int px = xc + x;
                    int py = yc + y;
                    putPixel(px, py, fillColor);
                }
            }
        }
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
}
