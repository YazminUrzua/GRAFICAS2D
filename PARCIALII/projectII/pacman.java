package PARCIALII.projectII;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class pacman extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private Color backgroundColor = Color.decode("#04050D");
    private Timer timer;
    private int centerX, centerY;
    private int radius = 2; // Radio inicial del círculo
    private double[][] circleVertices; // Matriz de vértices del círculo
    private double[][] transformationMatrix; // Matriz de transformación para escalar
    private Color circleColor = Color.GREEN;
    private int numCircles = 5; // Cantidad de círculos en la hilera
    private int circleSpacing = 100; // Espacio entre círculos


    public pacman() {
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
         // Aplica la matriz de transformación al círculo
         double[][] scaledCircleVertices = new double[circleVertices.length][3];
         for (int i = 0; i < circleVertices.length; i++) {
             scaledCircleVertices[i] = multiplyMatrixAndPoint(transformationMatrix, circleVertices[i]);
         }
         int x = circleSpacing;

         for (int i = 0; i < numCircles; i++) {
             graPixel.setColor(circleColor);
             graPixel.fillOval(x - radius, HEIGHT / 2 - radius, radius * 2, radius * 2);
             x += circleSpacing;
         }

        g.drawImage(buffer, 0, 0, this);
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


    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int ancho = width / 2;
        int alt = height / 2;
    
        // Dibuja los cuatro lados del rectángulo
        for (int x = centerX - ancho; x <= centerX + ancho; x++) {
            putPixel(x, centerY - alt, Color.blue);
            putPixel(x, centerY + alt, Color.blue);
        }
        for (int y = centerY - alt; y <= centerY + alt; y++) {
            putPixel(centerX - ancho, y, Color.blue);
            putPixel(centerX + ancho, y, Color.blue);
        }
    
    
    }
    public void drawPolygon(int[] xPoints, int[] yPoints, int numPoints, Color color) {
        // Dibuja los lados del polígono
        for (int i = 0; i < numPoints; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % numPoints];
            int y2 = yPoints[(i + 1) % numPoints];
            drawLine(x1, y1, x2, y2, color);
        }
    }
    

    
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        // Implementa un algoritmo de dibujo de línea
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;
    
        while (true) {
            putPixel(x1, y1, color);
    
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
    public void actionPerformed(ActionEvent e) {
        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);
       fondo();
        
             // Incrementa los factores de escala en cada iteración para aumentar el tamaño del círculo
        transformationMatrix[0][0] += 0.1;
        transformationMatrix[1][1] += 0.1;

        // Incrementa el radio del círculo para la próxima iteración
        radius += 5;

        if (radius >= 15) {
            // Cuando el círculo alcanza un cierto tamaño, reinicia el radio y la escala
            transformationMatrix[0][0] = 1.0;
            transformationMatrix[1][1] = 1.0;
            radius = 5;

            // Aumenta la posición X para el siguiente círculo
            centerX += circleSpacing;
        }
        // Actualiza la animación
        repaint();
    }

    public void fondo(){
        drawRectangle(400, 360, 190, 90);
        drawRectangle(400, 360, 180, 80);

        int[] xPoints1 = {309,495,495,411, 411, 390, 390, 309};
        int[] yPoints1 = {171,171,197, 197,271,271, 197, 197};
        int numPoints1 = xPoints1.length;
        drawPolygon(xPoints1, yPoints1, numPoints1, Color.blue);

        int[] xPoints2 = {561,584,584,561, 561, 479, 479, 561};
        int[] yPoints2 = {177,177,344, 344,272,272, 254, 254};
        int numPoints2 = xPoints1.length;
        drawPolygon(xPoints2, yPoints2, numPoints2, Color.blue);

        


    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        pacman animation = new pacman();
        frame.add(animation);
       
        frame.setVisible(true);
    }
}