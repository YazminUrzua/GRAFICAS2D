package PARCIALI;

import java.awt.Color;
import java.util.Scanner;

public class middlePoint extends javax.swing.JFrame {
    private PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public middlePoint() {
        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel 
    }
    public void drawMidpointLine(int x1, int y1, int x2, int y2) {
        int am = (x1 + x2) / 2;
        int bm = (y1 + y2) / 2;
        int dx = x2 - x1;
        int dy = y2 - y1;
        double a = (double) dy / dx;
        double b = y1 - a * x1;
        
        //bucle for draw the pixels
        for (int x = x1; x <= x2; x++) {
            int y = (int) (a * x + b);
            pixel.putPixel(x, y, Color.cyan);
        }
        //draw the middle point
         pixel.putPixel(am,bm, Color.red);

        }

    public static void main(String args[]) {
        middlePoint frame = new middlePoint();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter coordinates of 1st point");
        int x1 = in.nextInt() * 10;
        int y1 = in.nextInt() * 10;

        System.out.println("Enter coordinates of 2nd point");
        int x2 = in.nextInt() * 10;
        int y2 = in.nextInt() * 10;

        frame.drawMidpointLine(x1, y1, x2, y2);
    }

}