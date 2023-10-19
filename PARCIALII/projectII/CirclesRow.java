package PARCIALII.projectII;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class CirclesRow extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 200;
    private int numCircles = 5; // Cantidad de círculos en la fila
    private int circleSpacing = 20; // Espacio entre círculos
    private int circleRadius = 20; // Radio de los círculos
    private Color backgroundColor = Color.BLACK;
    private Color circleColor = Color.GREEN;
    private Timer timer;

    public CirclesRow() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        timer = new Timer(100, this); // Temporizador para actualizar la animación
        timer.start();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);

        // Dibuja los círculos en una fila
        int x = circleSpacing;

        for (int i = 0; i < numCircles; i++) {
            graPixel.setColor(circleColor);
            graPixel.fillOval(x - circleRadius, HEIGHT / 2 - circleRadius, circleRadius * 2, circleRadius * 2);
            x += circleSpacing;
        }

        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circles Row Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        CirclesRow animation = new CirclesRow();
        frame.add(animation);

        frame.setVisible(true);
    }

    public void drawRow() {
        repaint();
    }
}
