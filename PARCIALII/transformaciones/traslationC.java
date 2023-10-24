package PARCIALII.transformaciones;
import PARCIALII.transformaciones.traslation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class traslationC extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private Color backgroundColor = Color.WHITE;
    private Color fillColorTri = Color.CYAN;
    private double ovalCenterX = 300;
    private double ovalCenterY = 300;
    private int ovalRadius = 60;
    private Color ovalColor = Color.RED;

    private double[][] triangleVertices;
    private double translateXTri = 0;
    private double translateYTri = 400;

    private double scaleFactor = 1.0;
    private double scaleSpeed = 0.02;
    private double[][] ghostVertices;
    private int sizePh = 15;

    public traslationC() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();
        Timer timer = new Timer(100, this);
        
        timer.start();

        translateXTri = -200;
        translateYTri = 0;

        // Define los vértices para el rectángulo (en lugar del triángulo)
 ghostVertices = new double[][] {
    {-sizePh, -sizePh, 1},
    {sizePh, -sizePh, 1},
    {sizePh, sizePh, 1},
    {-sizePh, sizePh, 1}
};

        };
    

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);
        drawTriangle();
        f();
        drawPixelatedOval(ovalCenterX, ovalCenterY, ovalRadius, ovalColor);
        


        g.drawImage(buffer, 0, 0, this);
    }

    public void drawTriangle() {
        graPixel.setColor(fillColorTri);
        int[] xPointsTri = new int[triangleVertices.length];
        int[] yPointsTri = new int[triangleVertices.length];

        double[][] translationMatrix = {
            {1, 0, translateXTri},
            {0, 1, translateYTri},
            {0, 0, 1}
        };

        for (int i = 0; i < triangleVertices.length; i++) {
            double[] transformedVertex = multiplyMatrixAndPoint(translationMatrix, triangleVertices[i]);
            xPointsTri[i] = (int) (transformedVertex[0] + (WIDTH / 2));
            yPointsTri[i] = (int) (transformedVertex[1] + (HEIGHT / 2));
        }

        fillPolygonT(xPointsTri, yPointsTri, fillColorTri);

        // Llama a la función curve para dibujar la curva encima del triángulo
        curve();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PhantomTras();
    }

    private int stepTri = 0;

    public void PhantomTras() {
        int speedTri = 15;

        switch (stepTri) {
            case 0:
                translateXTri += speedTri;
                if (translateXTri >= 200) {
                    stepTri = 1;
                }
                break;
            case 1:
                translateYTri += speedTri;
                if (translateYTri >= 200) {
                    stepTri = 2;
                }
                break;
            case 2:
                translateXTri -= speedTri;
                if (translateXTri <= -200) {
                    stepTri = 3;
                }
                break;
            case 3:
                translateYTri -= speedTri;
                if (translateYTri <= -200) {
                    stepTri = 4;
                    fillColorTri = Color.GREEN;
                }
                break;
            case 4:
                if (scaleFactor < 10.0) {
                    scaleFactor += scaleSpeed;
                    for (int i = 0; i < triangleVertices.length; i++) {
                        triangleVertices[i][0] *= 1 + scaleSpeed;
                        triangleVertices[i][1] *= 1 + scaleSpeed;
                    }
                }
                break;
        }

        repaint();
    }

    public void curve() {
        int numPoints = 800;
        double π = Math.PI;
        int prevX = 0;
        int prevY = 0;

        for (int i = 0; i < numPoints; i++) {
            double x = (i / (double) (numPoints - 1)) * -π;
            double y = Math.sin(x);

            double xTranslated = x * 50 + 300;
            double yTranslated = y * 50 + 200;

            int xCoord = (int) xTranslated;
            int yCoord = (int) yTranslated;

            if (i > 0) {
                drawBresenhamLine(prevX, prevY, xCoord, yCoord,  Color.RED);
            }

            prevX = xCoord;
            prevY = yCoord;
        }

        int[] xPointsTri = new int[triangleVertices.length];
        int[] yPointsTri = new int[triangleVertices.length];

        for (int i = 0; i < triangleVertices.length; i++) {
            xPointsTri[i] = (int) (triangleVertices[i][0] + (WIDTH / 2));
            yPointsTri[i] = (int) (triangleVertices[i][1] + (HEIGHT / 2));
        }

        int[] xPointsCurve = new int[numPoints];
        int[] yPointsCurve = new int[numPoints];

        for (int i = 0; i < numPoints; i++) {
            xPointsCurve[i] = (int) (i * 600.0 / (numPoints - 1));
            yPointsCurve[i] = (int) (i < numPoints / 2 ? 200 : 200 + Math.sin(-π * (i - numPoints / 2) / (numPoints / 2)) * 50);
        }

        fillPolygon(xPointsCurve, yPointsCurve, numPoints, fillColorTri);
    }

    private double[] multiplyMatrixAndPoint(double[][] matrix, double[] point) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * point[j];
            }
            result[i] = sum;
        }

        return result;

    }
   public void drawPixelatedOval(double centerX, double centerY, int radius, Color color) {
        for (int x = (int) (centerX - radius); x <= centerX + radius; x++) {
            for (int y = (int) (centerY - radius); y <= centerY + radius; y++) {
                int dx = x - (int) centerX;
                int dy = y - (int) centerY;
                if (dx * dx + dy * dy <= radius * radius) {
                    putPixel(x, y, color);
                }
            }
        }
    }


    private void fillPolygonT(int[] xPoints, int[] yPoints, Color fillColor) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int y : yPoints) {
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        for (int y = minY; y <= maxY; y++) {
            int x1 = Integer.MAX_VALUE;
            int x2 = Integer.MIN_VALUE;

            for (int i = 0; i < xPoints.length; i++) {
                int j = (i + 1) % xPoints.length;
                int xi = xPoints[i];
                int xj = xPoints[j];
                int yi = yPoints[i];
                int yj = yPoints[j];

                if ((yi <= y && yj > y) || (yj <= y && yi > y)) {
                    int x = (int) (xi + (double) (y - yi) / (double) (yj - yi) * (xj - xi));
                    if (x < x1) x1 = x;
                    if (x > x2) x2 = x;
                }
            }

            if (x1 <= x2) {
                for (int x = x1; x <= x2; x++) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }


    private void fillPolygon(int[] xPoints, int[] yPoints,int numPoints, Color fillColor) {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int y : yPoints) {
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        for (int y = minY; y <= maxY; y++) {
            int x1 = Integer.MAX_VALUE;
            int x2 = Integer.MIN_VALUE;

            for (int i = 0; i < xPoints.length; i++) {
                int j = (i + 1) % xPoints.length;
                int xi = xPoints[i];
                int xj = xPoints[j];
                int yi = yPoints[i];
                int yj = yPoints[j];

                if ((yi <= y && yj > y) || (yj <= y && yi > y)) {
                    int x = (int) (xi + (double) (y - yi) / (double) (yj - yi) * (xj - xi));
                    if (x < x1) x1 = x;
                    if (x > x2) x2 = x;
                }
            }

            if (x1 <= x2) {
                for (int x = x1; x <= x2; x++) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }

    public void drawPolygon(int[] xPoints, int[] yPoints, int numPoints, Color color) {
        for (int i = 0; i < numPoints; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % numPoints];
            int y2 = yPoints[(i + 1) % numPoints];
            drawBresenhamLine(x1, y1, x2, y2, color);
        }
    }

    public void drawBresenhamLine(int x1, int y1, int x2, int y2, Color color) {
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
    public void f(){
        int[] xPoints = {60, 70, 80, 90, 100, 110, 110, 100, 90, 80, 70, 60};
        int[] yPoints = {110, 100, 110, 110, 100, 110, 100, 90, 100, 100, 110, 100};
        int numPoints = xPoints.length;
        Color color = Color.CYAN; // Puedes ajustar el color según tus preferencias
        
        drawPolygon(xPoints, yPoints, numPoints, color);
        
        // Ahora, dibuja la boca del fantasmita
        int[] mouthXPoints = {80, 85, 90};
        int[] mouthYPoints = {110, 105, 110};
        int numMouthPoints = mouthXPoints.length;
        Color mouthColor = Color.BLACK; // Puedes ajustar el color de la boca
        
        drawPolygon(mouthXPoints, mouthYPoints, numMouthPoints, mouthColor);
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Translation Animation with Triangles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        traslationC animation = new traslationC();
        frame.add(animation);

        frame.setVisible(true);
    }
}
