
package PARCIALII;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class tras extends javax.swing.JFrame {
    private BufferedImage buffer;
    private Graphics graPixel;
    private int xc, yc, radio; // Coordenadas y radio del círculo
    private int deltaX, deltaY; // Cantidades de desplazamiento en x e y

    public tras() {
        init();

        xc = 80; // Coordenada x del centro del círculo
        yc = 80; // Coordenada y del centro del círculo
        radio = 40; // Radio del círculo
        deltaX = 1; // Cantidad de desplazamiento en x en cada iteración
        deltaY = 1; // Cantidad de desplazamiento en y en cada iteración
    }

    public void init() {
        setTitle("Pixel");
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
        // Obtén el color del píxel en la ubicación (x, y) del buffer de la imagen
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            int rgb = buffer.getRGB(x, y);
            return new Color(rgb);
        } else {
            // Devuelve un valor predeterminado (por ejemplo, Color.BLACK) o maneja el error de tu elección
            return Color.BLACK;
        }
    }

    public void drawCircle() {
        // Bucle para borrar los puntos del círculo anterior (dibujados en blanco)
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));
            putPixel(xc + x, yc + y, Color.BLACK);
            putPixel(xc - x, yc + y, Color.BLACK);
            putPixel(xc + x, yc - y, Color.BLACK);
            putPixel(xc - x, yc - y, Color.BLACK);
            putPixel(xc + y, yc + x, Color.BLACK);
            putPixel(xc - y, yc + x, Color.BLACK);
            putPixel(xc + y, yc - x, Color.BLACK);
            putPixel(xc - y, yc - x, Color.BLACK);
        }
        // Calcula las nuevas coordenadas del centro
        xc += deltaX;
        yc += deltaY;

        // Bucle para dibujar el círculo trasladado en las nuevas coordenadas (naranja)
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));
            putPixel(xc + x, yc + y, Color.ORANGE);
            putPixel(xc - x, yc + y, Color.ORANGE);
            putPixel(xc + x, yc - y, Color.ORANGE);
            putPixel(xc - x, yc - y, Color.ORANGE);
            putPixel(xc + y, yc + x, Color.ORANGE);
            putPixel(xc - y, yc + x, Color.ORANGE);
            putPixel(xc + y, yc - x, Color.ORANGE);
            putPixel(xc - y, yc - x, Color.ORANGE);
        }
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
        // Crea una instancia de TranslationCircleAnimationWithThreads
        tras animation = new tras();
        // Inicia la animación
        animation.startAnimation();
    }
}
