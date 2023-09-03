package PARCIALI;
import java.awt.Color;
import java.awt.Frame;
import java.util.Scanner;


public class DDA extends javax.swing.JFrame {

    private PARCIALI.pixel pixel; // Instancia de la clase Pixel

    public DDA() {
        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel 
    }

    public void DDALine(int x1, int y1, int x2, int y2) {
        double dx, dy, x, y, steps;
        int k;
    
        dx = x2 - x1;
        dy = y2 - y1;
    
        if (Math.abs(dy) <= Math.abs(dx)) {
            steps = Math.abs(dx);
        } else {
            steps = Math.abs(dy);
        }
    
        dx = dx / steps;
        dy = dy / steps;
        x = x1;
        y = y1;
        k = 1;
    
        // Draw the first point
        pixel.putPixel((int) x1, (int) y1, Color.RED);
    
        if (x1 <= x2) {
            // Draw the line from left to right
            while (k <= steps) {
                x += dx;
                y += dy;
                k++;
    
                // Draw the points in red
                pixel.putPixel((int) x, (int) y, Color.RED);
            }
        } else {
            // Draw the line from right to left
            while (k <= steps) {
                x -= dx;
                y -= dy;
                k++;
    
                // Draw the points in red
                pixel.putPixel((int) x, (int) y, Color.blue);
            }
        }
    }


    public static void main(String args[]) {
        DDA frame = new DDA();

        Scanner in = new Scanner(System.in);
        System.out.println("Enter coordinates of 1st point");
        int x1 = in.nextInt()*10;
        int y1 = in.nextInt()*10;

        System.out.println("Enter coordinates of 2nd point");
        int x2 = in.nextInt()*10;
        int y2 = in.nextInt()*10;

        frame.DDALine(x1, y1, x2, y2);
    }

}
