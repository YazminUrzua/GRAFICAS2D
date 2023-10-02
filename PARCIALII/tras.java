package PARCIALII;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class tras extends javax.swing.JFrame {
    private BufferedImage buffer;
    private Graphics graPixel;
    private int xc, yc, radio; // Coordenadas y radio del círculo
    private int deltaX, deltaY; // Cantidades de desplazamiento en x e y
    private Color backgroundColor = Color.BLACK; // Color de fondo

    public tras() {
        init();

        xc = 80; // Coordenada x del centro del círculo
        yc = 80; // Coordenada y del centro del círculo
        radio = 40; // Radio del círculo
        deltaX = 1; // Cantidad de desplazamiento en x en cada iteración
        deltaY = 1; // Cantidad de desplazamiento en y en cada iteración
    }

    public void init() {
        setTitle("TRASLATION");
        setSize(500, 500);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.getGraphics();
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

    public void fillCircle(int xc, int yc, int radio, Color fillColor) {
        for (int y = yc - radio; y <= yc + radio; y++) {
            for (int x = xc - radio; x <= xc + radio; x++) {
                double distance = Math.sqrt((x - xc) * (x - xc) + (y - yc) * (y - yc));
                if (distance <= radio) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }

    public void eraseCircle(int xc, int yc, int radio, Color fillColor) {
        fillCircle(xc, yc, radio, fillColor);
    }

    public void drawCircle() {
        eraseCircle(xc, yc, radio, backgroundColor); // Borra el círculo anterior

        // Calcula las nuevas coordenadas del centro
        xc += deltaX;
        yc += deltaY;

        // Rellena el círculo trasladado en las nuevas coordenadas con el color especificado (naranja)
        fillCircle(xc, yc, radio, Color.ORANGE);
    }

    public void startAnimation() {
        // Crea un nuevo hilo para la animación
        Thread animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    drawCircle(); // Llama a drawCircle para actualizar el círculo
                    repaint(); // Repinta la ventana con el nuevo frame
                    try {
                        Thread.sleep(20); // Controla la velocidad de la animación
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        animationThread.start(); // Inicia el hilo de la animación
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this); // Dibuja el buffer en la ventana
    }

    public static void main(String[] args) {
        // Crea una instancia de tras
        tras animation = new tras();
        // Inicia la animación
        animation.startAnimation();
    }
}
