package PARCIALII;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RotatingRectangleWithFixedCorner extends JPanel implements Runnable {
    private int fixedX, fixedY; // Coordenadas de la esquina fija del rectángulo
    private int rectangleWidth, rectangleHeight; // Ancho y alto del rectángulo
    private double angle; // Ángulo de rotación en radianes
    private double angularSpeed; // Velocidad angular de rotación
    private BufferedImage buffer; // Imagen para dibujar

    public RotatingRectangleWithFixedCorner() {
        fixedX = 250; // Coordenada x de la esquina fija
        fixedY = 250; // Coordenada y de la esquina fija
        rectangleWidth = 120; // Ancho del rectángulo
        rectangleHeight = 60; // Alto del rectángulo
        angle = 0; // Ángulo de rotación inicial
        angularSpeed = Math.toRadians(2); // Velocidad angular en radianes por frame (2 grados por frame)

        buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    public void clearBuffer() {
        for (int x = 0; x < buffer.getWidth(); x++) {
            for (int y = 0; y < buffer.getHeight(); y++) {
                putPixel(x, y, Color.WHITE);
            }
        }
    }

    public void drawRectangleWithFixedCorner() {
        clearBuffer(); // Borra el frame anterior

        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        // Calcula las coordenadas de las esquinas del rectángulo rotado
        int x1 = fixedX;
        int y1 = fixedY;

        int x2 = fixedX + (int) (rectangleWidth * cosAngle);
        int y2 = fixedY - (int) (rectangleWidth * sinAngle);

        int x3 = fixedX - (int) (rectangleHeight * sinAngle);
        int y3 = fixedY - (int) (rectangleHeight * cosAngle);

        int x4 = x2 + x3 - x1;
        int y4 = y2 + y3 - y1;

        // Dibuja el rectángulo rotado (naranja) en el buffer
        for (int i = 0; i < rectangleWidth; i++) {
            int xStart = x1 + (int) (i * cosAngle);
            int yStart = y1 - (int) (i * sinAngle);

            int xEnd = x3 + (int) (i * cosAngle);
            int yEnd = y3 - (int) (i * sinAngle);

            putPixel(xStart, yStart, Color.ORANGE);
            putPixel(xEnd, yEnd, Color.ORANGE);
        }

        for (int i = 0; i < rectangleHeight; i++) {
            int xStart = x3 + (int) (i * sinAngle);
            int yStart = y3 + (int) (i * cosAngle);

            int xEnd = x2 - (int) (i * sinAngle);
            int yEnd = y2 - (int) (i * cosAngle);

            putPixel(xStart, yStart, Color.ORANGE);
            putPixel(xEnd, yEnd, Color.ORANGE);
        }

        // Actualiza el ángulo de rotación
        angle += angularSpeed;

        // Si el ángulo supera una vuelta completa, se reinicia
        if (angle >= Math.PI * 2) {
            angle = 0;
        }

        // Dibuja el buffer en el JPanel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja el buffer en el JPanel
        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void run() {
        while (true) {
            drawRectangleWithFixedCorner(); // Actualiza el rectángulo y lo dibuja
            try {
                Thread.sleep(20); // Controla la velocidad de la animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        RotatingRectangleWithFixedCorner animation = new RotatingRectangleWithFixedCorner();
        JFrame frame = new JFrame("Rotating Rectangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(animation);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);

        // Crea un hilo para la animación y comienza a ejecutarlo
        Thread animationThread = new Thread(animation);
        animationThread.start();
    }
}
