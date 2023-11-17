package PARCIALIII;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class poinstProjection extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public poinstProjection() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    // Algoritmo de Bresenham para dibujar una línea
    public void drawLineBresenham(int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, c);

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

    public void curvaResorte() {
        int numPuntos = 100;
        double[] tz = new double[numPuntos];
        double t = (8 * Math.PI) / numPuntos;
    
        for (int i = 0; i < numPuntos; i++) {
            tz[i] = t * i;
        }
    
        double[] x = new double[numPuntos];
        double[] y = new double[numPuntos];
    
        for (int i = 0; i < numPuntos; i++) {
            x[i] = Math.cos(tz[i]);
            y[i] = Math.sin(tz[i]);
        }
    
        double[] vp = {1, 1, 15};
        double[] u = new double[numPuntos];
        double[] xp = new double[numPuntos];
        double[] yp = new double[numPuntos];
    
        for (int i = 0; i < numPuntos - 1; i++) {
            u[i] = -(tz[i] / vp[2]);
            xp[i] = (x[i] + (vp[0] * u[i])) * 25 + getWidth() / 2; // Ajuste y escala
            yp[i] = (y[i] + (vp[1] * u[i])) * 25 + getHeight() / 2; // Ajuste y escala
             //xp[i] = (x[i] + (vp[0] * u[i])) ; // Ajuste y escala
            //yp[i] = (y[i] + (vp[1] * u[i])) ; // Ajuste y escala

            System.out.println();
            System.out.println("z["+i+"]: "+ u[i]);
            System.out.println("xp[" + i + "]: " + xp[i]);
            System.out.println("yp[" + i + "]: " + yp[i]);
    
            putPixel((int) xp[i], (int) yp[i], Color.red);
        }

        for (int i = 0; i < numPuntos - 1; i++) {
    
            if(!(i == numPuntos - 2)) {
                try {
                    Thread.sleep(10);
                    drawLineBresenham((int) xp[i], (int) yp[i], (int) xp[i+1], (int) yp[i+1], Color.BLUE);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                repaint();
            }

            else 
                break;
        }
    }    

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }
    public static void main(String[] args) throws Exception {
        
        poinstProjection app = new poinstProjection();
        app.setVisible(true);

        app.curvaResorte();
    }
}
