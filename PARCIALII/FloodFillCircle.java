package PARCIALII;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FloodFillCircle {
    private PARCIALI.pixel pixel; // Instance of the Pixel class

    public FloodFillCircle() {
        pixel = new PARCIALI.pixel();
    }

    public void drawAndFillCircle(int xc, int yc, int radius) {
        drawCircle(xc, yc, radius); // Draw the circle
        floodFill(xc, yc, Color.ORANGE, Color.BLUE); // Fill the circle
    }

    public void drawCircle(int xc, int yc, int radius) {
        for (int x = -radius; x <= radius; x++) {
            int y = (int) Math.round(Math.sqrt(radius * radius - x * x));

            // Eight quadrants to ensure completeness
            pixel.putPixel(xc + x, yc + y, Color.ORANGE);
            pixel.putPixel(xc - x, yc + y, Color.ORANGE);
            pixel.putPixel(xc + x, yc - y, Color.ORANGE);
            pixel.putPixel(xc - x, yc - y, Color.ORANGE);
            pixel.putPixel(xc + y, yc + x, Color.ORANGE);
            pixel.putPixel(xc - y, yc + x, Color.ORANGE);
            pixel.putPixel(xc + y, yc - x, Color.ORANGE);
            pixel.putPixel(xc - y, yc - x, Color.ORANGE);
            floodFill(xc, yc, Color.ORANGE, Color.BLUE); // Fill the circle
        }

        // Paint the center coordinates
        pixel.putPixel(xc, yc, Color.BLACK);
         
    }

    public void floodFill(int x, int y, Color targetColor, Color fillColor) {
        if (!targetColor.equals(pixel.getPixelColor(x, y))) {
            return;
        }

        java.util.Stack<int[]> stack = new java.util.Stack<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] currentPixel = stack.pop();
            int currentX = currentPixel[0];
            int currentY = currentPixel[1];

            if (!targetColor.equals(pixel.getPixelColor(currentX, currentY))) {
                continue;
            }

            pixel.putPixel(currentX, currentY, fillColor);

            int[][] neighbors = {
                {currentX + 1, currentY},
                {currentX - 1, currentY},
                {currentX, currentY + 1},
                {currentX, currentY - 1}
            };

            for (int[] neighbor : neighbors) {
                int neighborX = neighbor[0];
                int neighborY = neighbor[1];

                if (targetColor.equals(pixel.getPixelColor(neighborX, neighborY))) {
                    stack.push(new int[]{neighborX, neighborY});
                }
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4); // Use a single thread for this example

        FloodFillCircle frame = new FloodFillCircle();

        // Submit the drawAndFillCircle task for concurrent execution
       executor.submit(() -> frame.drawAndFillCircle(150, 150, 40));
        executor.submit(() -> frame.drawCircle(150,150,40));

        // Shutdown the executor when done
        executor.shutdown();
    }
}
