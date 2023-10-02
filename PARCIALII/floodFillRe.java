package PARCIALII;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class floodFillRe extends javax.swing.JFrame {
    private BufferedImage buffer;
    private int width = 500; // Ancho de la ventana
    private int height = 500; // Alto de la ventana

    public floodFillRe() {
        init();
    }

    public void init() {
        setTitle("Flood Fill");
        setSize(width, height);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Dibuja un rectángulo con el método proporcionado
        drawRectangle(200, 150, 100, 100);

        // Punto de inicio para el algoritmo de inundación (dentro del rectángulo)
        int startX = 250;
        int startY = 200;

        // Color nuevo para rellenar
        Color newColor = Color.RED;

        // Llama al algoritmo de inundación para rellenar el rectángulo
        floodFill(startX, startY, newColor);

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    public Color getPixelColor(int x, int y) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            int rgb = buffer.getRGB(x, y);
            return new Color(rgb);
        } else {
            return Color.BLACK;
        }
    }

    public void floodFill(int startX, int startY, Color newColor) {
        int originalColor = getPixelColor(startX, startY).getRGB();

        if (originalColor == newColor.getRGB()) {
            return;
        }

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int x = (int) point.getX();
            int y = (int) point.getY();

            if (getPixelColor(x, y).getRGB() == originalColor) {
                putPixel(x, y, newColor);

                queue.add(new Point(x + 1, y));
                queue.add(new Point(x - 1, y));
                queue.add(new Point(x, y + 1));
                queue.add(new Point(x, y - 1));
            }
        }
    }

    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int ancho = width / 2;
        int alt = height / 2;

        for (int x = centerX - ancho; x <= centerX + ancho; x++) {
            putPixel(x, centerY - alt, Color.BLUE);
            putPixel(x, centerY + alt, Color.BLUE);
        }
        for (int y = centerY - alt; y <= centerY + alt; y++) {
            putPixel(centerX - ancho, y, Color.BLUE);
            putPixel(centerX + ancho, y, Color.BLUE);
        }

        putPixel(centerX, centerY, Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new floodFillRe();
        });
    }
}
