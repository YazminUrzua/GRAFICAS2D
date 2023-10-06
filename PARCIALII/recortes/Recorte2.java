package PARCIALII.recortes;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Recorte2 implements MouseMotionListener, MouseListener {
    private PARCIALI.pixel pixel;
    private int xc, yc, radio;
    private int xmin, xmax, ymin, ymax;
    private boolean dibujandoCirculo;
    private int xf, yf;

    public Recorte2() {
        pixel = new PARCIALI.pixel();
        pixel.addMouseMotionListener(this);
        pixel.addMouseListener(this);
    }

    public void drawRectangle(int xmin, int xmax, int ymin, int ymax) {
        // Dibuja los cuatro lados del rectángulo
        for (int x = xmin; x <= xmax; x++) {
            pixel.putPixel(x, ymin, Color.blue);
            pixel.putPixel(x, ymax, Color.blue);
        }
        for (int y = ymin; y <= ymax; y++) {
            pixel.putPixel(xmin, y, Color.blue);
            pixel.putPixel(xmax, y, Color.blue);
        }

        // Pinta las coordenadas del centro
        int centerX = (xmin + xmax) / 2;
        int centerY = (ymin + ymax) / 2;
        pixel.putPixel(centerX, centerY, Color.BLACK);
    }

    public void drawCircle(int xc, int yc, int radio) {
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));

            if (xc + x >= xmin && xc + x <= xmax && yc + y >= ymin && yc + y <= ymax) {
                pixel.putPixel(xc + x, yc + y, Color.magenta);
            }

            if (xc - x >= xmin && xc - x <= xmax && yc + y >= ymin && yc + y <= ymax) {
                pixel.putPixel(xc - x, yc + y, Color.magenta);
            }

            if (xc + x >= xmin && xc + x <= xmax && yc - y >= ymin && yc - y <= ymax) {
                pixel.putPixel(xc + x, yc - y, Color.magenta);
            }

            if (xc - x >= xmin && xc - x <= xmax && yc - y >= ymin && yc - y <= ymax) {
                pixel.putPixel(xc - x, yc - y, Color.magenta);
            }

            if (xc + y >= xmin && xc + y <= xmax && yc + x >= ymin && yc + x <= ymax) {
                pixel.putPixel(xc + y, yc + x, Color.magenta);
            }

            if (xc - y >= xmin && xc - y <= xmax && yc + x >= ymin && yc + x <= ymax) {
                pixel.putPixel(xc - y, yc + x, Color.magenta);
            }

            if (xc + y >= xmin && xc + y <= xmax && yc - x >= ymin && yc - x <= ymax) {
                pixel.putPixel(xc + y, yc - x, Color.magenta);
            }

            if (xc - y >= xmin && xc - y <= xmax && yc - x >= ymin && yc - x <= ymax) {
                pixel.putPixel(xc - y, yc - x, Color.magenta);
            }
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(xc, yc, Color.BLACK);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (dibujandoCirculo) {
            // Cuando el usuario hace clic mientras dibuja un círculo,
            // se completa el círculo y se dibuja.
            dibujandoCirculo = false;
            xf = e.getX();
            yf = e.getY();
            drawCircle(xc, yc, (int) Math.round(Math.sqrt((xf - xc) * (xf - xc) + (yf - yc) * (yf - yc))));
        } else {
            // Cuando el usuario hace clic dentro del área delimitada,
            // comienza a dibujar un círculo.
            xc = e.getX();
            yc = e.getY();
            dibujandoCirculo = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // No se necesita implementar esta función para este ejemplo
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No se necesita implementar esta función para este ejemplo
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // No se necesita implementar esta función para este ejemplo
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // No se necesita implementar esta función para este ejemplo
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // No se necesita implementar esta función para este ejemplo
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // No se necesita implementar esta función para este ejemplo
    }

    public static void main(String[] args) {
        //para que el ususario pueda dibujar los circulos tiene que hacer doble click dentro de la 
        //figura (rectangulo)
        
        Recorte2 drawer = new Recorte2();
        int xmin = 100;
        int xmax = 400;
        int ymin = 50;
        int ymax = 450;

        drawer.xmin = xmin;
        drawer.xmax = xmax;
        drawer.ymin = ymin;
        drawer.ymax = ymax;
    

        drawer.drawRectangle(xmin, xmax, ymin, ymax);

        
    }
}
