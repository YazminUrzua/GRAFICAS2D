package PARCIALI.figures;

import java.awt.Color;

public class draw extends javax.swing.JFrame {
    private pixel pixel; // Instancia de la clase Pixel
  
    public draw() {

       pixel = new pixel(); // Crear una instancia de la clase Pixel
      
    }

    public void algorithmRect(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double a = (double) dy / dx;
        double b = y1 - a * x1;

        for (int x = x1; x <= x2; x++) {
            int y = (int) (a * x + b);
            pixel.putPixel(x, y, Color.blue);
        }
    }

    public void drawEllipse(int centerX, int centerY, int semiMajorAxis, int semiMinorAxis) {
        //para establecer las veces que se dara la vuelta 
        for (double theta = 0; theta <= 2 * Math.PI; theta += 0.01) {
            double x = centerX + semiMajorAxis * Math.cos(theta);
            double y = centerY + semiMinorAxis * Math.sin(theta);
            pixel.putPixel((int) x, (int) y, Color.magenta);
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(centerX, centerY, Color.BLACK);
    }

    public void drawCircle(int xc, int yc, int radio) {
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));
         
            //son ocho cuadrantes para que salga completo
            pixel.putPixel(xc + x, yc + y, Color.ORANGE);
            pixel.putPixel(xc - x, yc + y, Color.ORANGE);
            pixel.putPixel(xc + x, yc - y, Color.ORANGE);
            pixel.putPixel(xc - x, yc - y, Color.ORANGE);
            pixel.putPixel(xc + y, yc + x, Color.ORANGE);
            pixel.putPixel(xc - y, yc + x, Color.ORANGE);
            pixel.putPixel(xc + y, yc - x, Color.ORANGE);
            pixel.putPixel(xc - y, yc - x, Color.ORANGE);
            //pinta las coordenanas del centro
            pixel.putPixel(xc, yc, Color.BLACK);

        }
        
    }

    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        // Dibuja los cuatro lados del rectángulo
        for (int x = centerX - halfWidth; x <= centerX + halfWidth; x++) {
            pixel.putPixel(x, centerY - halfHeight, Color.red);
            pixel.putPixel(x, centerY + halfHeight, Color.red);
        }
        for (int y = centerY - halfHeight; y <= centerY + halfHeight; y++) {
            pixel.putPixel(centerX - halfWidth, y, Color.red);
            pixel.putPixel(centerX + halfWidth, y, Color.red);
        }

        // Pinta las coordenadas del centro
        pixel.putPixel(centerX, centerY, Color.BLACK);
    }


    public static void main(String args[]) {
        draw frame = new draw();

        frame.algorithmRect(30,80, 160, 260);
        frame.algorithmRect(170, 100, 330, 100);
        frame.algorithmRect(460, 260, 580, 50);
        frame.algorithmRect(590, 260, 750, 260);
        //circulos
        frame.drawCircle(220, 480, 10);
        frame.drawCircle(220, 480, 40);
        frame.drawCircle(220, 480, 80);
        frame.drawCircle(220, 480, 120);
        //Elipse
        frame.drawEllipse(750, 480, 10, 5);
        frame.drawEllipse(750, 480, 30, 10);
        frame.drawEllipse(750, 480, 60, 20);
        frame.drawEllipse(750, 480, 120, 40);
        //Rectangulo
        frame.drawRectangle(450, 480, 120, 80);
        frame.drawRectangle(450, 480, 100, 60);
        frame.drawRectangle(450, 480, 80, 40);
        frame.drawRectangle(450, 480, 60, 20);
        frame.drawRectangle(450, 480, 40, 10);

    }


}
