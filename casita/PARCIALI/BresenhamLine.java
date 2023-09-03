package PARCIALI;
import java.awt.Color;
import java.util.Scanner;

public class BresenhamLine extends javax.swing.JFrame {
    private PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public BresenhamLine() {
        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel 
    }
    public void drawBresenhamLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int err = dx - dy;
        int xStep = x1 < x2 ? 1 : -1;
        int yStep = y1 < y2 ? 1 : -1;

        while (x1 != x2 || y1 != y2) {
            pixel.putPixel(x1, y1, Color.red);
            int err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += xStep;
            }

            if (err2 < dx) {
                err += dx;
                y1 += yStep;
            }
        }

        pixel.putPixel(x2, y2, Color.cyan);
    }

    public static void main(String[] args) {
       
        BresenhamLine lineDrawer = new BresenhamLine();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter coordinates of 1st point");
        int x1 = in.nextInt() * 10;
        int y1 = in.nextInt() * 10;

        System.out.println("Enter coordinates of 2nd point");
        int x2 = in.nextInt() * 10;
        int y2 = in.nextInt() * 10;

        lineDrawer.drawBresenhamLine(x1, y1, x2, y2);
    }
}
