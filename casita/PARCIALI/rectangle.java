package PARCIALI;

import java.awt.Color;
import java.util.Scanner;

public class rectangle {
    private PARCIALI.pixel pixel; // Instancia de la clase Pixel


    public rectangle() {
        pixel = new PARCIALI.pixel(); // Crear una instancia de la clase Pixel 
    }

   
    public static void main(String[] args) {
      
       Scanner in = new Scanner(System.in);
        System.out.println("Enter coordinates of 1st point");
        int x1 = in.nextInt() * 10;
        int y1 = in.nextInt() * 10;

        System.out.println("Enter coordinates of 2nd point");
        int x2 = in.nextInt() * 10;
        int y2 = in.nextInt() * 10;

    }
}

