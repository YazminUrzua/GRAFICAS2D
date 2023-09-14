package PARCIALI.figures;
import java.awt.Color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Drawfiguras extends javax.swing.JFrame{
    JFrame panel = new JFrame();
    private BufferedImage buffer;
    private Graphics graPixel;
    
    public Drawfiguras() {
 
    setTitle("Figuras");
    setSize(900,700);
    setResizable(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    setLocationRelativeTo(null);
        
    buffer = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
    graPixel = (Graphics2D) buffer.createGraphics();


    }
    

    public void putPixel( int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
       
    }

    
    public void paint(Graphics g){
        super.paint(g);
        putPixel(80,80, Color.RED);

    }

    public void algorithmRect(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double a = (double) dy / dx;
        double b = y1 - a * x1;

        for (int x = x1; x <= x2; x++) {
            int y = (int) (a * x + b);
            putPixel(x, y, Color.blue);
        }
    }

    public void drawEllipse(int centerX, int centerY, int semiMajorAxis, int semiMinorAxis) {
        //para establecer las veces que se dara la vuelta 
        for (double theta = 0; theta <= 2 * Math.PI; theta += 0.01) {
            double x = centerX + semiMajorAxis * Math.cos(theta);
            double y = centerY + semiMinorAxis * Math.sin(theta);
            putPixel((int) x, (int) y, Color.magenta);
        }

        // Pinta las coordenadas del centro
        putPixel(centerX, centerY, Color.BLACK);
    }

    public void drawCircle(int xc, int yc, int radio) {
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));
         
            //son ocho cuadrantes para que salga completo
            putPixel(xc + x, yc + y, Color.ORANGE);
            putPixel(xc - x, yc + y, Color.ORANGE);
            putPixel(xc + x, yc - y, Color.ORANGE);
            putPixel(xc - x, yc - y, Color.ORANGE);
            putPixel(xc + y, yc + x, Color.ORANGE);
            putPixel(xc - y, yc + x, Color.ORANGE);
            putPixel(xc + y, yc - x, Color.ORANGE);
            putPixel(xc - y, yc - x, Color.ORANGE);
            //pinta las coordenanas del centro
            putPixel(xc, yc, Color.BLACK);

        }
        
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

        // Pinta las coordenadas del centro
        putPixel(centerX, centerY, Color.BLACK);
    }
}