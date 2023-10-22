package PARCIALII.transformaciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class traslation extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private Color backgroundColor = Color.WHITE;
    private Color ghostColor1 = Color.CYAN;
    private Color ghostColor2 = Color.MAGENTA;

    private double[][] ghostVertices1;
    private double[][] ghostVertices2;
 
    private double translateX2 = 200;
    private double translateY2 = 0;


       private double translateX1 = -200;
    private double translateY1 = 0;
    public traslation() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();
        Timer timer = new Timer(100, this);
        timer.start();

         translateX1 = -200;
     translateY1 = 300;

     
     translateX2 = 200;
     translateY2 = 200;

        ghostVertices1 = new double[][]{
            {0, -20, 1},
            {20, 20, 1},
            {-20, 20, 1}
        };

        ghostVertices2 = new double[][]{
            {0, -20, 1},
            {20, 20, 1},
            {-20, 20, 1}
        };
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);

        // Dibuja el primer fantasma
        graPixel.setColor(ghostColor1);
        drawGhost(ghostVertices1, translateX1, translateY1);

        // Dibuja el segundo fantasma
        graPixel.setColor(ghostColor2);
        drawGhost(ghostVertices2, translateX2, translateY2);

        g.drawImage(buffer, 0, 0, this);
    }

    public void drawGhost(double[][] vertices, double translateX, double translateY) {
        Path2D.Double path = new Path2D.Double();
        path.moveTo(vertices[0][0] + translateX, vertices[0][1] + translateY);

        for (int i = 1; i < vertices.length; i++) {
            path.lineTo(vertices[i][0] + translateX, vertices[i][1] + translateY);
        }

        path.closePath();
        graPixel.fill(path);

        int eyeX1 = (int) (vertices[0][0] + translateX - 8);
        int eyeY1 = (int) (vertices[0][1] + translateY - 6);
        int eyeX2 = (int) (vertices[0][0] + translateX + 8);
        int eyeY2 = (int) (vertices[0][1] + translateY - 6);
        int eyeRadius = 4;

        graPixel.setColor(Color.WHITE);
        graPixel.fillOval(eyeX1, eyeY1, eyeRadius * 2, eyeRadius * 2);
        graPixel.fillOval(eyeX2, eyeY2, eyeRadius * 2, eyeRadius * 2);

        graPixel.setColor(Color.BLACK);
        graPixel.drawOval(eyeX1, eyeY1, eyeRadius * 2, eyeRadius * 2);
        graPixel.drawOval(eyeX2, eyeY2, eyeRadius * 2, eyeRadius * 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        animateGhosts();
        repaint();
    }

    public void animateGhosts() {
        // Animación del primer fantasma
        translateX1 += 5;
        if (translateX1 > WIDTH)
            translateX1 = -40;

        // Animación del segundo fantasma
        translateX2 -= 5;
        if (translateX2 < -40)
            translateX2 = WIDTH;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ghost Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        traslation animation = new traslation();
        frame.add(animation);

        frame.setVisible(true);
    }

}
