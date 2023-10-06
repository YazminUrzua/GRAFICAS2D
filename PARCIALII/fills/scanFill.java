package PARCIALII.fills;
import static java.awt.Color.blue;
import java.awt.Color;

public class scanFill {
    private PARCIALI.pixel pixel; // Instancia de la clase Pixel


public scanFill(){
    pixel = new PARCIALI.pixel();
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
            fillCircle(xc, yc, radio, blue);


        }
       
    }
//Scanline
    public void fillCircle(int xc, int yc, int radio, Color fillColor) {
        for (int y = yc - radio; y <= yc + radio; y++) {
            for (int x = xc - radio; x <= xc + radio; x++) {
                double distance = Math.sqrt((x - xc) * (x - xc) + (y - yc) * (y - yc));
                if (distance <= radio) {
                    pixel.putPixel(x, y, fillColor);
                }
            }
        }
    }


    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int ancho = width / 2;
        int alt = height / 2;


        // Dibuja los cuatro lados del rectángulo
        for (int x = centerX - ancho; x <= centerX + ancho; x++) {
            pixel.putPixel(x, centerY - alt, Color.blue);
            pixel.putPixel(x, centerY + alt, Color.blue);
        }
        for (int y = centerY - alt; y <= centerY + alt; y++) {
            pixel.putPixel(centerX - ancho, y, Color.blue);
            pixel.putPixel(centerX + ancho, y, Color.blue);
        }


        // Pinta las coordenadas del centro
        pixel.putPixel(centerX, centerY, Color.BLACK);
        fillRectangle(centerX, centerY, width, height, Color.green);
    }


    public void fillRectangle(int centerX, int centerY, int width, int height, Color fillColor) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
   
        for (int y = centerY - halfHeight; y <= centerY + halfHeight; y++) {
            for (int x = centerX - halfWidth; x <= centerX + halfWidth; x++) {
                pixel.putPixel(x, y, fillColor);
            }
        }
    }


    public static void main(String[] args) {   
        scanFill frame = new scanFill();
        frame.drawRectangle(200,200, 80, 40);
        frame.drawCircle(80, 80, 40);
    }
}
