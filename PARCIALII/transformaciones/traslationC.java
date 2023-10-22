package PARCIALII.transformaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class traslationC extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private Color backgroundColor = Color.WHITE;
    private Color fillColorTri = Color.CYAN;

    private double[][] triangleVertices;
    private double translateXTri = 0;
    private double translateYTri = 400;
    private double prevXTri = 0;
    private double prevYTri = 0;
    private boolean increasing = true;

    private double scaleFactor = 1.0; // Factor de escala inicial
    private double scaleSpeed = 0.02; // Velocidad de cambio de escala

    public traslationC() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();
        Timer timer = new Timer(100, this);
        timer.start();

        translateXTri = -200;
        translateYTri = 0;

        triangleVertices = new double[][]{
            {0, -20, 1}, // P1
            {20, 20, 1}, // P2
            {-20, 20, 1} // P3
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
        drawPhantom();

        g.drawImage(buffer, 0, 0, this);
    }

    public void drawPhantom() {
        graPixel.setColor(fillColorTri);
        int[] xPointsTri = new int[triangleVertices.length];
        int[] yPointsTri = new int[triangleVertices.length];

        double[][] translationMatrix = {
            {1, 0, translateXTri},
            {0, 1, translateYTri},
            {0, 0, 1}
        };

        for (int i = 0; i < triangleVertices.length; i++) {
            double[] transformedVertex = multiplyMatrixAndPoint(translationMatrix, triangleVertices[i]);
            xPointsTri[i] = (int) (transformedVertex[0] + (WIDTH / 2));
            yPointsTri[i] = (int) (transformedVertex[1] + (HEIGHT / 2));
        }

        fillPolygon(xPointsTri, yPointsTri, fillColorTri);

        // Dibuja los ojos en el centro del triángulo
        int centerX = (xPointsTri[0] + xPointsTri[1] + xPointsTri[2]) / 3;
        int eyeY = (yPointsTri[0] + yPointsTri[1] + yPointsTri[2]) / 3;

        int eyeX1 = centerX - 10;
        int eyeX2 = centerX + 5;

        int eyeWidth = 5;
        int eyeHeight = 5;

        graPixel.setColor(Color.BLACK);
        graPixel.fillOval(eyeX1, eyeY, eyeWidth, eyeHeight);
        graPixel.fillOval(eyeX2, eyeY, eyeWidth, eyeHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PhantomTras();
    }

    private int stepTri = 0;

    public void PhantomTras() {
        int speedTri = 15;

        switch (stepTri) {
            case 0:
                translateXTri += speedTri;
                if (translateXTri >= 200) {
                    stepTri = 1;
                }
                break;
            case 1:
                translateYTri += speedTri;
                if (translateYTri >= 200) {
                    stepTri = 2;
                }
                break;
            case 2:
                translateXTri -= speedTri;
                if (translateXTri <= -200) {
                    stepTri = 3;
                }
                break;
            case 3:
                translateYTri -= speedTri;
                if (translateYTri <= -200) {
                    stepTri = 4;
                    fillColorTri = Color.GREEN;
                }
                break;
            case 4:
                if (scaleFactor < 10.0) { // Límite de escala
                    scaleFactor += scaleSpeed; // Incremento de escala gradual
                    for (int i = 0; i < triangleVertices.length; i++) {
                        triangleVertices[i][0] *= 1 + scaleSpeed; // Aumenta la coordenada X
                        triangleVertices[i][1] *= 1 + scaleSpeed; // Aumenta la coordenada Y
                    }
                }
                break;
        }

        prevXTri = translateXTri;
        prevYTri = translateYTri;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Translation Animation with Triangles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        traslationC animation = new traslationC();
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

    private void fillPolygon(int[] xPoints, int[] yPoints, Color fillColor) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int y : yPoints) {
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        for (int y = minY; y <= maxY; y++) {
            int x1 = Integer.MAX_VALUE;
            int x2 = Integer.MIN_VALUE;

            for (int i = 0; i < xPoints.length; i++) {
                int j = (i + 1) % xPoints.length;
                int xi = xPoints[i];
                int xj = xPoints[j];
                int yi = yPoints[i];
                int yj = yPoints[j];

                if ((yi <= y && yj > y) || (yj <= y && yi > y)) {
                    int x = (int) (xi + (double) (y - yi) / (double) (yj - yi) * (xj - xi));
                    if (x < x1) x1 = x;
                    if (x > x2) x2 = x;
                }
            }

            if (x1 <= x2) {
                for (int x = x1; x <= x2; x++) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }
}