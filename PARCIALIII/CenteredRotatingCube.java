package PARCIALIII;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenteredRotatingCube extends JFrame {
//chida
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private double angleX = 0.02;
    private double angleY = 0.02;

    public CenteredRotatingCube() {
        setTitle("Centered Rotating Cube");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CenteredCubePanel centeredCubePanel = new CenteredCubePanel();
        add(centeredCubePanel);

        Timer timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centeredCubePanel.rotateCube();
            }
        });
        timer.start();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CenteredRotatingCube::new);
    }

    private class CenteredCubePanel extends JPanel {

        private int[][] cubeVertices = {
                {-50, -50, -50},
                {50, -50, -50},
                {50, 50, -50},
                {-50, 50, -50},
                {-50, -50, 50},
                {50, -50, 50},
                {50, 50, 50},
                {-50, 50, 50}
        };

        private int[][] cubeEdges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        private double[][] cubeRotated = new double[cubeVertices.length][3];

        public CenteredCubePanel() {
            // Inicializar la matriz de rotación
            for (int i = 0; i < cubeVertices.length; i++) {
                cubeRotated[i][0] = cubeVertices[i][0];
                cubeRotated[i][1] = cubeVertices[i][1];
                cubeRotated[i][2] = cubeVertices[i][2];
            }
        }

        public void rotateCube() {
            // Rotar el cubo en los ejes X e Y
            for (int i = 0; i < cubeRotated.length; i++) {
                double x = cubeRotated[i][0];
                double y = cubeRotated[i][1];
                double z = cubeRotated[i][2];

                cubeRotated[i][0] = x * Math.cos(angleY) - z * Math.sin(angleY);
                cubeRotated[i][2] = x * Math.sin(angleY) + z * Math.cos(angleY);

                x = cubeRotated[i][0];
                z = cubeRotated[i][2];

                cubeRotated[i][1] = y * Math.cos(angleX) - z * Math.sin(angleX);
                cubeRotated[i][2] = y * Math.sin(angleX) + z * Math.cos(angleX);
            }

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Dibujar las aristas del cubo
            g2d.setColor(Color.BLACK);
            for (int[] edge : cubeEdges) {
                int x1 = (int) cubeRotated[edge[0]][0] + centerX;
                int y1 = (int) cubeRotated[edge[0]][1] + centerY;
                int x2 = (int) cubeRotated[edge[1]][0] + centerX;
                int y2 = (int) cubeRotated[edge[1]][1] + centerY;
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }
}
