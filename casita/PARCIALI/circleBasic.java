package PARCIALI;

import java.awt.Color;

public class circleBasic {
    private PARCIALI.pixel pixel;

    public circleBasic() {
        pixel = new PARCIALI.pixel();
    }

    public void drawCircle(int centerX, int centerY, int radius) {
        Color color = Color.BLUE;

        for (int x = centerX - radius; x <= centerX + radius; x++) {
            
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                int dx = x - centerX;
                int dy = y - centerY;
                
                if (dx * dx + dy * dy <= radius * radius) {
                     pixel.putPixel(x, y, color);
                }
                
            }
        } pixel.putPixel(centerX, centerY, Color.ORANGE);
    }

    public static void main(String[] args) {
        circleBasic drawer = new circleBasic();
            
        int centerX = 50;
        int centerY = 50;
        int radius = 40;

        drawer.drawCircle(centerX, centerY, radius);
    }
}
