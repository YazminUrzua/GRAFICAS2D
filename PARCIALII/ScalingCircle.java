package PARCIALII;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ScalingCircle extends JFrame {
    private BufferedImage buffer;
    private int xc, yc, radio;
    private double scaleFactor = 1.0; // Factor de escala inicial

    public ScalingCircle() {
        init();

        xc = 250; // Coordenada x del centro del círculo
        yc = 250; // Coordenada y del centro del círculo
        radio = 40; // Radio del círculo
    }

    public void init() {
        setTitle("Scaling Circle");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    public void clearBuffer() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                putPixel(x, y, Color.WHITE);
            }
        }
    }

    public void drawCircle() {
        clearBuffer(); // Borra el frame anterior

        int scaledRadius = (int) (radio * scaleFactor);

        // Bucle para dibujar la circunferencia con escala en los 8 cuadrantes
        for (int x = -scaledRadius; x <= scaledRadius; x++) {
            int y = (int) Math.sqrt(scaledRadius * scaledRadius - x * x);
            
            // Dibuja los píxeles escalados en naranja en los 8 cuadrantes
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this); // Dibuja el buffer en la ventana
    }

    public void startAnimation() {
        while (true) {
            drawCircle(); // Llama a drawCircle para actualizar la circunferencia
            repaint(); // Repinta la ventana con el nuevo frame
            try {
                Thread.sleep(20); // Controla la velocidad de la animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scaleFactor += 0.02; // Incrementa el factor de escala para la animación
            if (scaleFactor > 2.0) {
                scaleFactor = 1.0; // Restablece el factor de escala después de un ciclo completo
            }
        }
    }

    public static void main(String[] args) {
        ScalingCircle animation = new ScalingCircle();
        animation.startAnimation();
    }
}
