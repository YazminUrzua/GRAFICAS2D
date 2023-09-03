package PARCIALI;

import java.awt.Color;
import java.awt.Frame;
import java.util.Scanner;


public class RectLine extends Frame {
    private PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public RectLine() {

        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel
    }

    public void algorithmRect(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double a = (double) dy / dx;
        double b = y1 - a * x1;

        for (int x = x1; x <= x2; x++) {
            int y = (int) (a * x + b);
            pixel.putPixel(x, y, Color.green);
        }
    }

    public static void main(String args[]) {
        RectLine frame = new RectLine();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter coordinates of 1st point");
        int x1 = in.nextInt()*10;

        int y1 = in.nextInt()*10;
        System.out.println("Enter coordinates of 2st point");
        int x2 = in.nextInt()*10;
        int y2 = in.nextInt()*10;

        frame.algorithmRect(x1, y1, x2, y2);
    }


}
