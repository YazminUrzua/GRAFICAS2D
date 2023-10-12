package PARCIALII.mallado;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class TriangleMeshInCircle extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int numTriangles = 36;  // Número de triángulos
        int radius = Math.min(width, height) / 2;

        Point2D center = new Point2D.Double(width / 2.0, height / 2.0);

        for (int i = 0; i < numTriangles; i++) {
            double angle1 = (2 * Math.PI * i) / numTriangles;
            double angle2 = (2 * Math.PI * (i + 1)) / numTriangles;

            Point2D point1 = new Point2D.Double(center.getX() + radius * Math.cos(angle1),
                    center.getY() + radius * Math.sin(angle1));
            Point2D point2 = new Point2D.Double(center.getX() + radius * Math.cos(angle2),
                    center.getY() + radius * Math.sin(angle2));
            Point2D point3 = center;

            Path2D triangle = new Path2D.Double();
            triangle.moveTo(point1.getX(), point1.getY());
            triangle.lineTo(point2.getX(), point2.getY());
            triangle.lineTo(point3.getX(), point3.getY());
            triangle.closePath();

            // Dibujar el triángulo
            g2d.setColor(Color.BLUE);
            g2d.fill(triangle);
            g2d.setColor(Color.BLACK);
            g2d.draw(triangle);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Triangle Mesh in Circle");
        TriangleMeshInCircle triangleMesh = new TriangleMeshInCircle();
        frame.add(triangleMesh);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
