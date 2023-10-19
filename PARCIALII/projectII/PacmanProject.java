
package PARCIALII.projectII;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class PacmanProject extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private Color backgroundColor = Color.decode("#04050D");
    private Timer timer;
   

    public PacmanProject() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        timer = new Timer(100, this); // Temporizador para actualizar la animación
        timer.start();


    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(buffer, 0, 0, this);
    }


    public void drawRectangle(int centerX, int centerY, int width, int height) {
        int ancho = width / 2;
        int alt = height / 2;
    
        // Dibuja los cuatro lados del rectángulo
        for (int x = centerX - ancho; x <= centerX + ancho; x++) {
            putPixel(x, centerY - alt, Color.blue);
            putPixel(x, centerY + alt, Color.blue);
        }
        for (int y = centerY - alt; y <= centerY + alt; y++) {
            putPixel(centerX - ancho, y, Color.blue);
            putPixel(centerX + ancho, y, Color.blue);
        }
    
    
    }
    public void drawPolygon(int[] xPoints, int[] yPoints, int numPoints, Color color) {
        // Dibuja los lados del polígono
        for (int i = 0; i < numPoints; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % numPoints];
            int y2 = yPoints[(i + 1) % numPoints];
            drawLine(x1, y1, x2, y2, color);
        }
    }
    

    
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        // Implementa un algoritmo de dibujo de línea
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;
    
        while (true) {
            putPixel(x1, y1, color);
    
            if (x1 == x2 && y1 == y2) {
                break;
            }
    
            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);
       fondo();
        // Actualiza la animación
        repaint();
    }

    public void fondo(){
        drawRectangle(400, 380, 190, 90);
        drawRectangle(400, 380, 180, 80);

        int[] xPoints1 = {309,495,495,411, 411, 390, 390, 309};
        int[] yPoints1 = {171,171,197, 197,271,271, 197, 197};
        int numPoints1 = xPoints1.length;
        drawPolygon(xPoints1, yPoints1, numPoints1, Color.blue);

        int[] xPoints2 = {561,584,584,561, 561, 479, 479, 561};
        int[] yPoints2 = {177,177,344, 344,272,272, 254, 254};
        int numPoints2 = xPoints2.length;
        drawPolygon(xPoints2, yPoints2, numPoints2, Color.blue);

        int[] xPoints3 = {218,241,241,324, 324, 241, 241, 218};
        int[] yPoints3 = {177,177,254, 254,272,272, 344, 344};
        int numPoints3 = xPoints3.length;
        drawPolygon(xPoints3, yPoints3, numPoints3, Color.blue);

        int[] xPoints4 = {3, 3, 161, 161, 22, 22, 391, 391, 418,418, 780, 780, 644, 644, 798, 798, 663, 663, 798, 798, 3, 3, 143, 143};
        int[] yPoints4 = {340,349,349,247,247,17, 17, 120, 120,17, 17, 249, 249, 349, 349, 340, 340, 262, 262, 5, 5, 260, 260, 340};
        int numPoints4 = xPoints4.length;
        drawPolygon(xPoints4, yPoints4, numPoints4, Color.blue);

        drawRectangle(683, 90, 90, 60);
        drawRectangle(534, 90, 110, 60);
        drawRectangle(683, 190, 90, 30);
        drawRectangle(117, 90, 90, 60);
        drawRectangle(276, 90, 110, 60);
        drawRectangle(117, 190, 90, 30);
        drawRectangle(230, 450, 20, 90);
        drawRectangle(570, 450, 20, 90);

        int[] xPoints5 = {309,495,495,411, 411, 390, 390, 309};
        int[] yPoints5 = {480, 480, 505, 505, 580, 580, 505, 505};
        int numPoints5 = xPoints5.length;
        drawPolygon(xPoints5, yPoints5, numPoints5, Color.blue);

        drawRectangle(270, 570, 90, 20);
        drawRectangle(530, 570, 90, 20);

        int[] xPoints6 = {75,160,160,140, 140, 75};
        int[] yPoints6 = {560, 560, 620, 620,580, 580};
        int numPoints6 = xPoints6.length;
        drawPolygon(xPoints6, yPoints6, numPoints6, Color.blue);

        int[] xPoints7 = {650,730,730,670, 670, 650};
        int[] yPoints7 = {560, 560, 580, 580, 620, 620};
        int numPoints7 = xPoints7.length;
        drawPolygon(xPoints7, yPoints7, numPoints7, Color.blue);

        int[] xPoints8 = {309,495,495,411, 411, 390, 390, 309};
        int[] yPoints8 = {630, 630, 655, 655, 730, 730, 655, 655};
        int numPoints8 = xPoints8.length;
        drawPolygon(xPoints8, yPoints8, numPoints8, Color.blue);

        int[] xPoints9 = {560, 585, 585, 730, 730, 470, 470, 560};
        int[] yPoints9 = {635, 635, 710, 710, 730, 730, 710, 710};
        int numPoints9 = xPoints9.length;
        drawPolygon(xPoints9, yPoints9, numPoints9, Color.blue);

        int[] xPoints10 = {370, 430, 430, 370};
        int[] yPoints10 = {334, 334, 338, 338};
        int numPoints10 = xPoints10.length;
        drawPolygon(xPoints10, yPoints10, numPoints10, Color.white);

        int[] xPoints11 = {798, 645, 645, 780, 780, 730, 730, 780, 780, 22, 22, 70, 70, 22, 22, 161, 161, 3, 3, 145, 145, 3, 3, 795, 795, 660, 660, 798};
        int[] yPoints11 = {405, 405, 505, 505, 630, 630, 650, 650, 765, 765, 650, 650, 630, 630, 505, 505, 405, 405, 415, 415, 490, 490, 795, 798, 490, 490, 415, 415};
        int numPoints11 = xPoints11.length;
        drawPolygon(xPoints11, yPoints11, numPoints11, Color.blue);

        int[] xPoints12 = {215, 240, 240, 330, 330, 75, 75, 215};
        int[] yPoints12 = {635, 635, 710, 710, 730, 730, 710, 710};
        int numPoints12 = xPoints12.length;
        drawPolygon(xPoints12, yPoints12, numPoints12, Color.blue);

    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        PacmanProject animation = new PacmanProject();
        frame.add(animation);
       
        frame.setVisible(true);
    }
}