package PARCIALII.fills;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int ancho = width / 2;
        int alt = height / 2;

        for (int x = centerX - ancho; x <= centerX + ancho; x++) {
            putPixel(x, centerY - alt, Color.BLACK);
            putPixel(x, centerY + alt, Color.BLACK);
        }
        for (int y = centerY - alt; y <= centerY + alt; y++) {
            putPixel(centerX - ancho, y, Color.BLACK);
            putPixel(centerX + ancho, y, Color.BLACK);
        }

        putPixel(centerX, centerY, Color.BLACK);
        floodFilling(centerX, centerY, width, height, Color.cyan);
    }

//Relleno para el rectangulo
    public void floodFilling(int x, int y, int width, int height, Color c){
        for(int i = x; i< x+ width; i++){
            for(int u = y; u< y+height; u++){
                putPixel(i, u, c);
            }
        }
    }

    public static void main(String[] args) {
 
        SwingUtilities.invokeLater(() -> {
       
            floodFillRe draw =  new floodFillRe();
            draw.drawRectangle(200, 150, 100, 100);
        });
    }
}
