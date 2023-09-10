package PARCIALI;

import java.awt.Color;

public class simpleCircle {
    private PARCIALI.pixel pixel;

    public simpleCircle() {
       pixel = new PARCIALI.pixel();
    }

    public void drawCircle(int xc, int yc, int radio) {
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));
            // Dibuja los puntos simétricos en los cuatro cuadrantes
           /*  pixel.putPixel(xc + x, yc + y, Color.ORANGE);
            pixel.putPixel(xc + x, yc - y, Color.ORANGE);
            pixel.putPixel(xc - x, yc + y, Color.ORANGE);
            pixel.putPixel(xc - x, yc - y, Color.ORANGE);
*/
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

    public static void main(String[] args) {
        simpleCircle drawer = new simpleCircle();

        //int centerX = 80;
        //int centerY = 80;
        //int radius = 40;


        int centerX ;
        int centerY ;
        int radius ;

        //drawer.drawCircle(centerX, centerY, radius);
         drawer.drawCircle(80, 80, 40);
    }
}
