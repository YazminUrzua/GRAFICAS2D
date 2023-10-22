package PARCIALII.projectII;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class pacman extends JPanel implements ActionListener {
    private BufferedImage buffer;
    private Graphics2D graPixel;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private Color backgroundColor = Color.decode("#04050D");
    private Timer timer;
    private int centerX, centerY;
    private int radius = 2; // Radio inicial del círculo
    private double[][] circleVertices; // Matriz de vértices del círculo
    private double[][] transformationMatrix; // Matriz de transformación para escalar
    private Color circleColor = new Color(0xd4badc);
    private int circleSpacing = 90; // Espacio entre círculos
    private int circleSpacingCenter = 130;
    private double rotationAngle; // Ángulo de rotación
    private int prevX = 0; // Coordenada X del punto previo
    private int prevY = 0; // Coordenada Y del punto previo
    private int prevX2 = 0; // Coordenada X del punto previo
    private int prevY2 = 0; // Coordenada Y del punto previo
    private int prevX3 = 0; // Coordenada X del punto previo
    private int prevY3 = 0; // Coordenada Y del punto previo
    private int prevX4 = 0; // Coordenada X del punto previo
    private int prevY4 = 0; // Coordenada Y del punto previo
    private int prevX5 = 0; // Coordenada X del punto previo
    private int prevY5 = 0; // Coordenada Y del punto previo
    private Timer labelTimer;  // Temporizador para controlar la duración del JLabel
    private JLabel label;  // Agregamos un JLabel
    private Color shapeColor = new Color(0xffff00); // Color de "Pacman"
   // private Color trailColor = backgroundColor;
    private ArrayList<Point> trail = new ArrayList<>();
    private double translateX = 0;
    private double translateY = 360;
    private static final int CIRCLE_RADIUS = 30;
     // Guarda las coordenadas anteriores
     private int prevXP = (int) translateX;
     private int prevYP = (int) translateY;

    private double[][] triangleVertices;
    private Color fillColorTri = new Color(0x0404e2);
    private Color fillColorTri2 = new Color(0x0404e2);
      private Color fillColorTri3 = new Color(0x0404e2);
    private double translateXTri= 0;
    private double translateYTri = 0;
    private double prevXTri = 0;
    private double prevYTri = 0;
    private double[][] ghostVertices2;
     private double[][] ghostVertices3;
    private double translateXPh2 = 0;
    private double translateYPh2 = 0;
     private double translateXPh3 = 0;
    private double translateYPh3= 0;
     

    public pacman() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        centerX = WIDTH / 2;
        centerY = HEIGHT / 2;

        // Inicializa la matriz de transformación para escalar
        transformationMatrix = new double[][] {
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0}
        };
//para el fantasma
        translateXTri = 0;
        translateYTri = 120;

//para el fantasma 2          
     translateXPh2 = -200;
     translateYPh2 = 120;


//para el fantasma 2          
     translateXPh3 = 0;
     translateYPh3 = 120;

        triangleVertices = new double[][]{
            {0, -20, 1}, // P1
            {20, 20, 1}, // P2
            {-20, 20, 1} // P3
        };


        ghostVertices2 = new double[][]{
            {0, -20, 1},
            {20, 20, 1},
            {-20, 20, 1}
        };

         ghostVertices3 = new double[][]{
            {0, -20, 1},
            {20, 20, 1},
            {-20, 20, 1}
        };


        timer = new Timer(100, this); // Temporizador para actualizar la animación
        timer.start();

        // Inicializa la matriz de vértices del círculo
        initializeCircleVertices();

         // Carga la fuente  desde el archivo TTF
         Font customFont = loadCustomFont("/GRAFICAS2D/PARCIALII/projectII/04B_08__.TTF", 48); // Reemplaza "PressStart2P.ttf" con la ubicación de tu archivo TTF

        
     setLayout(new BorderLayout()); // Usamos un BorderLayout
     label = new JLabel("Press start Game");
     label.setFont(customFont);
     label.setHorizontalAlignment(JLabel.CENTER); // Centrar el texto horizontalmente
     label.setVerticalAlignment(JLabel.CENTER); // Centrar el texto verticalmente
     label.setForeground(new Color(0xFFB350));  
     label.setBackground(new Color(0x000020));   
     label.setOpaque(true);         
     add(label, BorderLayout.CENTER);


        // Configura el temporizador del primer JLabel
        labelTimer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setVisible(false);
                labelTimer.stop();

                // Crea y muestra el segundo JLabel después de 1 minuto y medio
                Timer secondLabelTimer = new Timer(0, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        labelTimer.stop();
                        JLabel secondLabel = new JLabel("Game Over");
                        secondLabel.setFont(customFont);
                        secondLabel.setHorizontalAlignment(JLabel.CENTER);
                        secondLabel.setVerticalAlignment(JLabel.CENTER);
                        secondLabel.setForeground(new Color(0xFFB350));
                        secondLabel.setBackground(new Color(0x000020));
                        secondLabel.setOpaque(true);
                        add(secondLabel, BorderLayout.CENTER);
                        revalidate(); // Vuelve a validar la disposición para mostrar el segundo JLabel
                    }
                });

                secondLabelTimer.setInitialDelay(90000); // Inicia después de 1 minuto y medio (90,000 ms)
                secondLabelTimer.setRepeats(false);  // El temporizador se ejecuta solo una vez
                secondLabelTimer.start();
            }
        });

        labelTimer.setRepeats(false);  // El temporizador se ejecuta solo una vez
        labelTimer.start();
    }

    private Font loadCustomFont(String fontFileName, int fontSize) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(fontFileName));
            return customFont.deriveFont(Font.PLAIN, fontSize);
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, usa una fuente predeterminada
            return new Font("04B_08", Font.BOLD, fontSize);
        }
    }

    public void putPixel(int x, int y, Color c) {
        // Ajusta las coordenadas y tamaños de los rectángulos
        boolean inRectangle1 = x >= 2 && x <= 60 && y >= 300 && y <= 430;
        boolean inRectangle2 = x >= 740 && x <= 860 && y >= 300 && y <= 430;
        
        if (!(inRectangle1 || inRectangle2)) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }
    


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Aplica la matriz de transformación al círculo
        double[][] scaledCircleVertices = new double[circleVertices.length][3];
        for (int i = 0; i < circleVertices.length; i++) {
            scaledCircleVertices[i] = multiplyMatrixAndPoint(transformationMatrix, circleVertices[i]);
        }

       int xLeft = circleSpacing; 
        leftCircle(xLeft,1);  
        xLeft += circleSpacing; 
        
        int xRight = getWidth() - circleSpacing;
        rightCircle(xRight,1);  
        xRight -= circleSpacing; 

        //int xCenter = getWidth() / 2;
        int xCenter = circleSpacingCenter;
        centerCircle(xCenter, 1);
        drawPacman((int) translateX, (int) translateY, CIRCLE_RADIUS, shapeColor);

        drawRotatingSun();
        drawPhantom();
        drawPhantom2();
        drawPhantom3();
    

        g.drawImage(buffer, 0, 0, this);
       
    }
//relleno
    private void fillCircles(int[] xCenters, int[] sizes, Color fillColor) {
         
        int xc = centerX;
        int yc = centerY;
    
        for (int i = 0; i < xCenters.length; i++) {
            int xCenter = xCenters[i];
            int size = sizes[i];
    
            for (int y = -size; y <= size; y++) {
                for (int x = -size; x <= size; x++) {
                    double distance = Math.sqrt(x * x + y * y);
                    if (distance <= size) {
                        int px = xCenter + x;
                        int py = 42 + y;
                        int py2 = 530 + y;
                        int py3 = 230 + y;
                        int py4 = 142 + y;
                        int py5 = 750 + y;
                        int py6 = 605 + y;
                        int py7 = 675 + y;

                        putPixel(px, py, fillColor);
                        putPixel(px, py2, fillColor);
                        putPixel(px, py3, fillColor);
                        putPixel(px, py4, fillColor);
                        putPixel(px, py5, fillColor);
                        putPixel(px, py6, fillColor);
                        putPixel(px, py7, fillColor);
                    }
                }
            }
        }
    }

    private void fillPolygonTri(int[] xPoints, int[] yPoints, Color fillColor) {
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


    public void drawPhantom() {
       
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

        fillPolygonTri(xPointsTri, yPointsTri, fillColorTri);

        // Dibuja los ojos en el centro del triángulo
        int centerX = (xPointsTri[0] + xPointsTri[1] + xPointsTri[2]) / 3;
        int eyeY = (yPointsTri[0] + yPointsTri[1] + yPointsTri[2]) / 3;

        int eyeX1 = centerX - 10;
        int eyeX2 = centerX + 5;

        int eyeWidth = 5;
        int eyeHeight = 5;

        graPixel.setColor(Color.BLACK);
        graPixel.fillOval(eyeX1, eyeY, eyeWidth, eyeHeight);
        graPixel.fillOval(eyeX2, eyeY, eyeWidth, eyeHeight);
    }

        public void drawPhantom2() {
       
        int[] xPoints2 = new int[ghostVertices2.length];
        int[] yPoints2 = new int[ghostVertices2.length];

        double[][] translationMatrixPh2 = {
            {1, 0, translateXPh2},
            {0, 1, translateYPh2},
            {0, 0, 1}
        };

        for (int i = 0; i < ghostVertices2.length; i++) {
            double[] transformedVertex2 = multiplyMatrixAndPoint(translationMatrixPh2, ghostVertices2[i]);
            xPoints2[i] = (int) (transformedVertex2[0] + (WIDTH / 2));
            yPoints2[i] = (int) (transformedVertex2[1] + (HEIGHT / 2));
        }

        fillPolygonTri(xPoints2, yPoints2, fillColorTri2);

        // Dibuja los ojos en el centro del triángulo
        int centerX = (xPoints2[0] + xPoints2[1] + xPoints2[2]) / 3;
        int eyeY = (yPoints2[0] + yPoints2[1] + yPoints2[2]) / 3;

        int eyeX1 = centerX - 10;
        int eyeX2 = centerX + 5;

        int eyeWidth = 5;
        int eyeHeight = 5;

        graPixel.setColor(Color.BLACK);
        graPixel.fillOval(eyeX1, eyeY, eyeWidth, eyeHeight);
        graPixel.fillOval(eyeX2, eyeY, eyeWidth, eyeHeight);
    }

    public void drawPhantom3() {
       
        int[] xPoints3 = new int[ghostVertices3.length];
        int[] yPoints3 = new int[ghostVertices3.length];

        double[][] translationMatrixPh3 = {
            {1, 0, translateXPh3},
            {0, 1, translateYPh3},
            {0, 0, 1}
        };

        for (int i = 0; i < ghostVertices3.length; i++) {
            double[] transformedVertex3 = multiplyMatrixAndPoint(translationMatrixPh3, ghostVertices3[i]);
            xPoints3[i] = (int) (transformedVertex3[0] + (WIDTH / 2));
            yPoints3[i] = (int) (transformedVertex3[1] + (HEIGHT / 2));
        }

        fillPolygonTri(xPoints3, yPoints3, fillColorTri3);

        // Dibuja los ojos en el centro del triángulo
        int centerX = (xPoints3[0] + xPoints3[1] + xPoints3[2]) / 3;
        int eyeY = (yPoints3[0] + yPoints3[1] + yPoints3[2]) / 3;

        int eyeX1 = centerX - 10;
        int eyeX2 = centerX + 5;

        int eyeWidth = 5;
        int eyeHeight = 5;

        graPixel.setColor(Color.BLACK);
        graPixel.fillOval(eyeX1, eyeY, eyeWidth, eyeHeight);
        graPixel.fillOval(eyeX2, eyeY, eyeWidth, eyeHeight);
    }



  private int stepTri = 0;

  
  public void PhantomTras(){
       int speedTri = 6;
  
          switch (stepTri) {
             case 0: // Mover hacia arriba
                  translateYTri -= speedTri;
                  if (translateYTri <= -100) {
                      stepTri = 1; // Siguiente paso: izquierda (reiniciar el triángulo)
                      fillColorTri = new Color(0xff69b4);
                  }

                   translateYPh2 -= speedTri;
                  if (translateYPh2 <= -95) {
                      stepTri = 1; // Siguiente paso: izquierda (reiniciar el triángulo)
                     
                      fillColorTri = new Color(0xff69b4);
                  }
                  break;
                  case 1: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= -130) {
                      stepTri = 2; // Siguiente paso: abajo
                  }
                  break;
                 case 2: // Mover hacia abajo
                  translateYTri += speedTri;
                  if (translateYTri >= 125) {
                      stepTri = 3; // Siguiente paso: derecha
                  }
                  break;
                   case 3: // Mover hacia la derecha
                  translateXTri += speedTri;
                  if (translateXTri >= -35) {
                      stepTri = 4; // Siguiente paso: abajo
                  }
                  break;
                  case 4: // Mover hacia abajo
                  translateYTri += speedTri+2;
                  if (translateYTri >= 200) {
                      stepTri = 5; // Siguiente paso: derecha
                  }
                  break;
                 case 5: // Mover hacia la izquierda
                  translateXTri -= speedTri+3;
                  if (translateXTri <= -212) {
                      stepTri = 6; // Siguiente paso: abajo
                     fillColorTri = new Color(0x0404e2);
                  }break;
                  case 6: // Mover diagonal hacia arriba a la derecha
                    translateXTri += speedTri;
                    translateYTri -= speedTri ;
                  if (translateXTri >= 5) {
                    stepTri = 7; // Siguiente paso: Mover hacia abajo
                 }
                 break;
                  case 7: // Mover hacia arriba
                  translateYTri -= speedTri;
                  if (translateYTri <= -95) {
                      stepTri = 8; // Siguiente paso: derecha (reiniciar el triángulo)
                      fillColorTri = new Color(0xff69b4);
                  }
                  break;
                  case 8: // Mover hacia la derecha
                  translateXTri += speedTri;
                  if (translateXTri >= 130) {
                      stepTri = 9; // Siguiente paso: abajo
                  }
                  break;
                  case 9: // Mover hacia abajo
                  translateYTri += speedTri;
                  if (translateYTri >= 120) {
                      stepTri = 10; // Siguiente paso: izquierda
                  }
                  break;
                  case 10: // Mover hacia la derecha
                  translateXTri += speedTri;
                  if (translateXTri >= 350) {
                      stepTri = 11; // Siguiente paso: abajo
                  }
                  break;
                  case 11: // Mover hacia abajo
                  translateYTri += speedTri;
                  if (translateYTri >= 200) {
                      stepTri = 12; // Siguiente paso: izquierda
                  }
                  break; 
                 case 12: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= 300) {
                      stepTri = 13; // Siguiente paso: abajo
                  }
                  break; 
                 case 13: // Mover hacia abajo
                  translateYTri += speedTri;
                  if (translateYTri >= 280) {
                      stepTri = 14; // Siguiente paso: izquierda
                  }
                  break;
                   case 14: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= 220) {
                      stepTri = 15; // Siguiente paso: abajo
                  }
                  break;  
                 case 15: // Mover hacia arriba
                  translateYTri -= speedTri;
                  if (translateYTri <= -240) {
                      stepTri = 16; // Siguiente paso: derecha (reiniciar el triángulo)
                  }
                  break;
                 case 16: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= -200) {
                      stepTri = 17; // Siguiente paso: arriba
                  }
                  break; 
                 case 17: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= -130) {
                      stepTri = 18; // Siguiente paso: abajo
                  }
                  break;
                 case 18: // Mover hacia abajo
                  translateYTri += speedTri;
                  if (translateYTri >= 125) {
                      stepTri = 19; // Siguiente paso: derecha
                  }
                  break;
                   case 19: // Mover hacia la derecha
                  translateXTri += speedTri;
                  if (translateXTri >= -35) {
                      stepTri = 20; // Siguiente paso: abajo
                  }
                  break;
                  case 20: // Mover hacia abajo
                  translateYTri += speedTri+2;
                  if (translateYTri >= 200) {
                      stepTri = 21; // Siguiente paso: derecha
                  }
                  break;  
                case 21: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= -200) {
                      stepTri = 22; // Siguiente paso: arriba
                     
                  }
                  break;
                 case 22: // Mover hacia abajo
                  translateYTri += speedTri+2;
                  if (translateYTri >= 280) {
                      stepTri = 23; // Siguiente paso: derecha
                  }
                  break;  
                case 23: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= -350) {
                      stepTri = 24; // Siguiente paso: arriba 
                  }
                  break; 
                  case 24: // Mover hacia abajo
                  translateYTri += speedTri+2;
                  if (translateYTri >= 340) {
                      stepTri = 25; // Siguiente paso: derecha
                  }
                 break; 
                case 25: // Mover hacia la derecha
                  translateXTri += speedTri;
                  if (translateXTri >= 350) {
                      stepTri = 26; // Siguiente paso: abajo
                  }
                  break;
                case 26: // Mover hacia arriba
                  translateYTri -= speedTri;
                  if (translateYTri <= 280) {
                      stepTri = 27; // Siguiente paso: derecha (reiniciar el triángulo)
                  }
                  break;
                case 27: // Mover hacia la izquierda
                  translateXTri -= speedTri;
                  if (translateXTri <= 220) {
                      stepTri = 28; // Siguiente paso: arriba
                      
                  }
                  break;
                case 28: // Mover hacia arriba
                  translateYTri -= speedTri;
                  if (translateYTri <= -30) {
                      stepTri = 29; // Siguiente paso: derecha (reiniciar el triángulo)
                  }  
                  break;   
                  case 29: // Mover hacia la derecha
                  translateXTri += speedTri;
                  if (translateXTri >= 400) {
                      stepTri = 100; // Siguiente paso: abajo
                  }
                  break;    
            
          }
  
          prevXTri = translateXTri;
          prevYTri = translateYTri;
          repaint();
  }

private int stepTri2 = 0;
  
  public void PhantomTras2(){
       int speedTri2 = 6;
  
          switch (stepTri2) {
             case 0: // Mover hacia arriba
                   translateYPh2 -= speedTri2;
                  if (translateYPh2 <= 395) {
                      stepTri2 = 1; // Siguiente paso: izquierda (reiniciar el triángulo)
                      fillColorTri2 = new Color(0x9f90ea);
                     
                  }
                  break;
             case 1: // Mover hacia la derecha
                  translateXPh2 += speedTri2;
                  if (translateXPh2 >= 135) {
                      stepTri2 = 2; // Siguiente paso: abajo
                  }
                  break;
             case 2: // Mover hacia abajo
                  translateYPh2 += speedTri2;
                  if (translateYPh2 >= -20) {
                      stepTri2 = 3; // Siguiente paso: derecha
                  }
                  break;
            case 3: // Mover hacia la derecha
                  translateXPh2 += speedTri2;
                  if (translateXPh2 >= 210) {
                      stepTri2 = 4; // Siguiente paso: abajo
                  }
                  break;  
             case 4: // Mover hacia arriba
                   translateYPh2 -= speedTri2;
                  if (translateYPh2 <= -255) {
                      stepTri2 = 5; // Siguiente paso: izquierda (reiniciar el triángulo)
                     
                  }
                  break; 
             case 5: // Mover hacia la izquierda
                  translateXPh2 -= speedTri2;
                  if (translateXPh2 <= -130) {
                      stepTri2 = 6; // Siguiente paso: abajo
                  }
                  break; 
             case 6: // Mover hacia abajo
                  translateYPh2 += speedTri2;
                  if (translateYPh2 >= -180) {
                      stepTri2 = 7; // Siguiente paso: derecha
                  }
                  break;                    
            case 7: // Mover hacia la derecha
                  translateXPh2 += speedTri2;
                  if (translateXPh2 >= -50) {
                      stepTri2 = 8; // Siguiente paso: abajo
                  }
                  break; 
              case 8: // Mover hacia abajo
                  translateYPh2 += speedTri2;
                  if (translateYPh2 >= -100) {
                      stepTri2 = 9; // Siguiente paso: derecha
                  }
                  break; 
             case 9: // Mover hacia la izquierda
                  translateXPh2 -= speedTri2;
                  if (translateXPh2 <= -130) {
                      stepTri2 = 10; // Siguiente paso: abajo
                  }
                  break;
             case 10: // Mover hacia abajo
                  translateYPh2 += speedTri2;
                  if (translateYPh2 >= 120) {
                      stepTri2 = 12; // Siguiente paso: derecha
                  }
                  break; 
            case 12: // Mover hacia la derecha
                  translateXPh2 += speedTri2;
                  if (translateXPh2 >= -40) {
                      stepTri2 = 13; // Siguiente paso: abajo
                  }
                  break; 
            case 13: // Mover hacia abajo
                  translateYPh2 += speedTri2;
                  if (translateYPh2 >= 200) {
                      stepTri2 = 14; // Siguiente paso: derecha
                  }
                  break;
             case 14: // Mover hacia la derecha
                  translateXPh2 += speedTri2;
                  if (translateXPh2 >= 210) {
                      stepTri2 = 15; // Siguiente paso: abajo
                  }
                  break;  
             case 15: // Mover hacia arriba
                   translateYPh2 -= speedTri2-3;
                  if (translateYPh2 <= 140) {
                      stepTri2 = 16; // Siguiente paso: izquierda (reiniciar el triángulo)   
                  }
                  break;  
            case 16: // Mover hacia la derecha
                  translateXPh2 += speedTri2-3;
                  if (translateXPh2 >= 350) {
                      stepTri2 = 17; // Siguiente paso: abajo
                  }
                  break;  
            case 17: // Mover hacia abajo
                  translateYPh2 += speedTri2;
                  if (translateYPh2 >= 200) {
                      stepTri2 = 18; // Siguiente paso: derecha
                  }
                  break;   
            case 18: // Mover hacia la izquierda
                  translateXPh2 -= speedTri2-3;
                  if (translateXPh2 <= 300) {
                      stepTri2 = 19; // Siguiente paso: abajo
                  }
                  break;
            case 19: // Mover hacia abajo
                  translateYPh2 += speedTri2-2;
                  if (translateYPh2 >= 280) {
                      stepTri2 = 20; // Siguiente paso: derecha
                  }
                  break; 
             case 20: // Mover hacia la izquierda
                  translateXPh2 -= speedTri2-3;
                  if (translateXPh2 <= 220) {
                      stepTri2 = 21; // Siguiente paso: abajo
                      fillColorTri2 = new Color(0x0404e2);
                  }
                  break;   
            case 21: // Mover hacia arriba y hacia la izquierda
                  translateYPh2 -= speedTri2 - 1;
                  translateXPh2 -= speedTri2 - 1;
                  if (translateYPh2 <= -10 && translateXPh2 <= 300) {
                      stepTri2 = 22; // Siguiente paso: abajo
                    
                  }
                  break;
              case 22: // Mover hacia la derecha
                  translateXPh2 += speedTri2-3;
                  if (translateXPh2 >= 2) {
                      stepTri2 = 23; // Siguiente paso: abajo
                  }
                  break;        
                  
             case 23: // Mover hacia arriba
                  translateYPh2 -= speedTri2;
                  if (translateYPh2 <= -95) {
                      stepTri2 = 1; // Siguiente paso: izquierda (reiniciar el triángulo)
                     
                      fillColorTri2 = new Color(0x8787fb);
                  break;  
                  }     
          }
          repaint();
  }


private int stepTri3 = 0;
private double scaleFactor = 1.0; // Factor de escala inicial
private double scaleSpeed = 0.02; // Velocidad de cambio de escala

  
  public void PhantomTras3(){
       int speedTri3 = 6;
  
          switch (stepTri3) {
             case 0: // Mover hacia arriba
                   translateYPh3 -= speedTri3;
                  if (translateYPh3 <= -95) {
                      stepTri3 = 1; // Siguiente paso: izquierda (reiniciar el triángulo)
                      fillColorTri3 = new Color(0x5dc1b9);
                     
                  }
                  break;
             case 1: // Mover hacia la izquierda
                  translateXPh3 -= speedTri3;
                  if (translateXPh3 <= -40) {
                      stepTri3 = 2; // Siguiente paso: abajo
                  }
                  break; 
            case 2: // Mover hacia arriba
                   translateYPh3 -= speedTri3;
                  if (translateYPh3 <= -180) {
                      stepTri3 = 3; // Siguiente paso: izquierda (reiniciar el triángulo)
                     
                  }
                  break;  
            case 3: // Mover hacia la izquierda
                  translateXPh3 -= speedTri3;
                  if (translateXPh3 <= -130) {
                      stepTri3 = 4; // Siguiente paso: abajo
                  }
                  break;
            case 4: // Mover hacia arriba
                   translateYPh3 -= speedTri3;
                  if (translateYPh3 <= -250) {
                      stepTri3 = 5; // Siguiente paso: izquierda (reiniciar el triángulo)
                     
                  }
                  break; 
             case 5: // Mover hacia la derecha
                  translateXPh3 += speedTri3;
                  if (translateXPh3 >= 210) {
                      stepTri3 = 6; // Siguiente paso: abajo
                  }
                  break; 
             case 6: // Mover hacia abajo
                  translateYPh3 += speedTri3;
                  if (translateYPh3 >= 130) {
                      stepTri3 = 7; // Siguiente paso: derecha
                  }
                  break; 
              case 7: // Mover hacia la izquierda
                  translateXPh3 -= speedTri3;
                  if (translateXPh3 <= 50) {
                      stepTri3 = 8; // Siguiente paso: abajo
                  }
                  break; 
               case 8: // Mover hacia abajo
                  translateYPh3 += speedTri3;
                  if (translateYPh3 >= 200) {
                      stepTri3 = 9; // Siguiente paso: derecha
                  }
                  break;  
               case 9: // Mover hacia la izquierda
                  translateXPh3 -= speedTri3;
                  if (translateXPh3 <= -200) {
                      stepTri3 = 10; // Siguiente paso: abajo
                  }
                  break;  
                     
            case 10: // Mover hacia arriba
                   translateYPh3 -= speedTri3;
                  if (translateYPh3 <= -250) {
                      stepTri3 = 11; // Siguiente paso: izquierda (reiniciar el triángulo)
                     
                  }
                  break; 
            case 11: // Mover hacia la izquierda
                  translateXPh3 -= speedTri3;
                  if (translateXPh3 <= -350) {
                      stepTri3 = 12; // Siguiente paso: abajo
                  }
                  break;  
             case 12: // Mover hacia arriba
                   translateYPh3 -= speedTri3;
                  if (translateYPh3 <= -350) {
                      stepTri3 = 13; // Siguiente paso: izquierda (reiniciar el triángulo)
                     
                  }
                  break; 
            case 13: // Mover hacia abajo
                  translateYPh3 += speedTri3;
                  if (translateYPh3 >= -185) {
                      stepTri3 = 14; // Siguiente paso: derecha
                  }
                  break; 
            case 14: // Mover hacia la derecha
                  translateXPh3 += speedTri3;
                  if (translateXPh3 >= -220) {
                      stepTri3 = 15; // Siguiente paso: abajo
                  }
                  break; 
             case 15: // Mover hacia abajo
                  translateYPh3 += speedTri3;
                  if (translateYPh3 >= 200) {
                      stepTri3 = 16; // Siguiente paso: derecha
                  }
                  break; 
            case 16: // Mover hacia la derecha
                  translateXPh3 += speedTri3;
                  if (translateXPh3 >= 220) {
                      stepTri3 = 17; // Siguiente paso: abajo
                  }
                  break; 
                  case 17: // Mover hacia arriba
                  translateYPh3 -= speedTri3 + 3;
                  if (translateYPh3 <= -250) {
                      stepTri3 = 18; // Siguiente paso: izquierda (reiniciar el triángulo)
                  }
                  break;
              case 18: // Mover hacia la izquierda
                  translateXPh3 -= speedTri3 + 4;
                  if (translateXPh3 <= -200) {
                      stepTri3 = 19; // Siguiente paso: abajo
                      fillColorTri3 = new Color(0x0404e2);
                  }/* 
                  if (scaleFactor < 30.0) { // Límite de escala
                      System.out.println("if de escala");
                      scaleFactor += scaleSpeed; // Incremento de escala gradual
                      for (int i = 0; i < ghostVertices3.length; i++) {
                          ghostVertices3[i][0] *= 1 + scaleSpeed; // Aumenta la coordenada X
                          ghostVertices3[i][1] *= 1 + scaleSpeed; // Aumenta la coordenada Y
                      }
                  }*/
                  break;
                  case 19: // Mover hacia arriba y hacia la izquierda
                  translateYPh3 += speedTri3- 3;
                  translateXPh3 += speedTri3 - 3;
                  if (translateYPh3 >= 0 && translateXPh3 <= 650) {
                      stepTri3 = 20; // Siguiente paso: abajo
                  }
                 
                break;
                case 20: // Mover hacia la izquierda
                  translateXPh3 -= speedTri3 + 2;
                  if (translateXPh3 <= 5) {
                      stepTri3 = 21; // Siguiente paso: abajo
                  }
                       if (scaleFactor < 20.0) { // Nueva escala final de 30
                    System.out.println("if de escala");
                    scaleFactor += scaleSpeed * 20.0; // Incremento de escala 30 veces más rápido
                    for (int i = 0; i < ghostVertices3.length; i++) {
                        ghostVertices3[i][0] *= 1 + scaleSpeed * 20.0; // Aumenta la coordenada X
                        ghostVertices3[i][1] *= 1 + scaleSpeed * 20.0; // Aumenta la coordenada Y
                       
                    }
                
                
                  }
                  break;
                 case 21: // Mover hacia arriba
                  translateYPh3 -= speedTri3 + 7;
                  if (translateYPh3 <= -50) {
                      stepTri3 = 22; // Siguiente paso: izquierda (reiniciar el triángulo)
                       fillColorTri3 = new Color(0xff0000);
                  }
                  break; 
               case 22: // Mover hacia abajo
                  translateYPh3 += speedTri3+7;
                  if (translateYPh3 >= 185) {
                      stepTri3 = 21; // Siguiente paso: derecha
                  }
                  break;     
                 
                                                                                                    
              
                  }     
          
          repaint();
  }


//relleno 
    private void fillCirclesCenter(int[] xCenters, int[] sizes, Color fillColor) {
        for (int i = 0; i < xCenters.length; i++) {
            int xCenter = xCenters[i];
            int size = sizes[i];
    
            for (int y = -size; y <= size; y++) {
                for (int x = -size; x <= size; x++) {
                    double distance = Math.sqrt(x * x + y * y);
                    if (distance <= size) {
                        int px = xCenter + x;
                        int py = 300 + y;
                       
                        putPixel(530+x, py, fillColor);//300y
                        putPixel(620+x, py, fillColor);//300y
                        putPixel(270+x, py, fillColor);//300y
                        putPixel(180+x, py, fillColor);//300y
                        putPixel(405+x, py, fillColor);//300y

                        putPixel(530+x, py+150, fillColor);//300y
                        putPixel(620+x, py+150, fillColor);//300y
                        putPixel(270+x, py+150, fillColor);//300y
                        putPixel(180+x, py+150, fillColor);//300y
                        

                        putPixel(50+x, py+270, fillColor);//42
                        putPixel(50+x, py-105, fillColor);//42
                        putPixel(755+x, py+270, fillColor);//42
                        putPixel(755+x, py-105, fillColor);//42

                        putPixel(95+x, py+80, fillColor);//42
                        putPixel(710+x, py+80, fillColor);//42
                        putPixel(180+x, py+80, fillColor);//42
                        putPixel(620+x, py+80, fillColor);//42
                        putPixel(530+x, py+80, fillColor);//42
                        putPixel(270+x, py+80, fillColor);//42
                    }
                }
            }
        }
    }
    

//circulos left
    public void leftCircle(int xLeft, int yc) {
        // Coordenadas Y de los centros de los círculos
        int[] yCenters = {yc, yc - circleSpacing, yc - 2 * circleSpacing, yc - 3 * circleSpacing};
        int[] xCenters = {xLeft, xLeft + circleSpacing, xLeft + 2 * circleSpacing, xLeft + 3 * circleSpacing}; // Coordenadas X de los centros de los círculos
        int[] sizes = {radius, radius, radius, radius}; // Tamaños de los círculos
    
        fillCircles(xCenters, sizes, circleColor);
    }
//circulos right
    public void rightCircle(int xRight, int yc) {
        // Coordenadas Y de los centros de los círculos
        int[] yCenters = {yc, yc - circleSpacing, yc - 2 * circleSpacing, yc - 3 * circleSpacing};
        int[] xCenters = {xRight, xRight - circleSpacing, xRight - 2 * circleSpacing, xRight - 3 * circleSpacing}; // Coordenadas X de los centros de los círculos
        int[] sizes = {radius, radius, radius, radius}; // Tamaños de los círculos
    
        fillCircles(xCenters, sizes, circleColor);
    }

    public void centerCircle(int xCenter, int yc) {
        // Coordenadas Y de los centros de los círculos
        
        int[] yCenters = {yc, yc - circleSpacingCenter, yc - 2 * circleSpacingCenter, yc - 3 * circleSpacingCenter};
        int[] xCenters = {xCenter, xCenter, xCenter, xCenter}; // Coordenadas X de los centros de los círculos
        int[] sizes = {radius, radius, radius, radius}; // Tamaños de los círculos
    
        fillCirclesCenter(xCenters, sizes, circleColor);
        
    }
    
    // Función para aplicar una transformación a un punto (x, y) usando una matriz de transformación
    private void transformPoint(double[] point, double[][] transformationMatrix) {
        double x = point[0];
        double y = point[1];
        double w = point[2];
        point[0] = x * transformationMatrix[0][0] + y * transformationMatrix[0][1] + w * transformationMatrix[0][2];
        point[1] = x * transformationMatrix[1][0] + y * transformationMatrix[1][1] + w * transformationMatrix[1][2];
        point[2] = x * transformationMatrix[2][0] + y * transformationMatrix[2][1] + w * transformationMatrix[2][2];
    }

    public void drawRotatingSun() {
        int Points = 1000; // Aumenta el número de puntos para una curva más suave
        double π = Math.PI;
    
        for (int i = 0; i < Points; i++) {
            double t = (i / (double) (Points - 1)) * 20 * π;
            double x = 17 * Math.cos(t) + 7 * Math.cos((17.0 / 7.0) * t);
            double y = 17 * Math.sin(t) - 7 * Math.sin((17.0 / 7.0) * t);
    
            double[] point = {x, y, 1}; // Agregar el componente w
            double[] point2 = {x, y, 1}; // Agregar el componente w
            double[] point3 = {x, y, 1}; // Agregar el componente w
            double[] point4 = {x, y, 1}; // Agregar el componente w
            double[] point5 = {x, y, 1}; // Agregar el componente w
    
            double cosTheta = Math.cos(rotationAngle);
            double sinTheta = Math.sin(rotationAngle);
            double[][] rotationMatrix = {
                {cosTheta, -sinTheta, 0},
                {sinTheta, cosTheta, 0},
                {0, 0, 1}
            };
    
            // Primera figura en la posición (755, 90) ROJA
            double translateX1 = 620;
            double translateY1 = 145;
            double[][] translationMatrix1 = {
                {1, 0, translateX1},
                {0, 1, translateY1},
                {0, 0, 1}
            };
    
            // Segunda figura en la posición (405, 455)NARANJA
            double translateX2 = 620;
            double translateY2 = 675;
            double[][] translationMatrix2 = {
                {1, 0, translateX2},
                {0, 1, translateY2},
                {0, 0, 1}
            };
    
            // Tercera figura en la posición (48, 90) VERDE
            double translateX3 = 55;
            double translateY3 = 533;
            double[][] translationMatrix3 = {
                {1, 0, translateX3},
                {0, 1, translateY3},
                {0, 0, 1}
            };
    
            // Cuarta figura en la posición (x4, y4)AQUA
            double translateX4 = 530; // Define la posición en X para la cuarta figura
            double translateY4 = 450; // Define la posición en Y para la cuarta figura
            double[][] translationMatrix4 = {
                {1, 0, translateX4},
                {0, 1, translateY4},
                {0, 0, 1}
            };
    
            // Quinta figura en la posición (x5, y5)
            double translateX5 = 50; // Define la posición en X para la quinta figura
            double translateY5 = 145; // Define la posición en Y para la quinta figura
            double[][] translationMatrix5 = {
                {1, 0, translateX5},
                {0, 1, translateY5},
                {0, 0, 1}
            };
    
            // Multiplicar matrices de traslación y rotación para todas las figuras
            double[][] transformationMatrix1 = multiplyMatrices(translationMatrix1, rotationMatrix);
            double[][] transformationMatrix2 = multiplyMatrices(translationMatrix2, rotationMatrix);
            double[][] transformationMatrix3 = multiplyMatrices(translationMatrix3, rotationMatrix);
            double[][] transformationMatrix4 = multiplyMatrices(translationMatrix4, rotationMatrix);
            double[][] transformationMatrix5 = multiplyMatrices(translationMatrix5, rotationMatrix);
    
            transformPoint(point, transformationMatrix1);
            x = point[0];
            y = point[1];
    
            // Mapear los valores de x e y a las coordenadas de la ventana para la primera figura
            int xCoord1 = (int) x;
            int yCoord1 = (int) y;
    
            // Repetir el proceso para la segunda figura
            transformPoint(point2, transformationMatrix2);
            x = point2[0];
            y = point2[1];
            int xCoord2 = (int) x;
            int yCoord2 = (int) y;
    
            // Repetir el proceso para la tercera figura
            transformPoint(point3, transformationMatrix3);
            x = point3[0];
            y = point3[1];
            int xCoord3 = (int) x;
            int yCoord3 = (int) y;
    
            // Repetir el proceso para la cuarta figura
            transformPoint(point4, transformationMatrix4);
            x = point4[0];
            y = point4[1];
            int xCoord4 = (int) x;
            int yCoord4 = (int) y;
    
            // Repetir el proceso para la quinta figura
            transformPoint(point5, transformationMatrix5);
            x = point5[0];
            y = point5[1];
            int xCoord5 = (int) x;
            int yCoord5 = (int) y;
    
            if (i > 0) {
                // Dibujar la primera figura
                drawBresenhamLine(prevX, prevY, xCoord1, yCoord1, new Color(0xFF3D00));//ROJA
                
                // Dibujar la segunda figura
                drawBresenhamLine(prevX2, prevY2, xCoord2, yCoord2, new Color(0xF50057));//NARANJA
    
                // Dibujar la tercera figura
                drawBresenhamLine(prevX3, prevY3, xCoord3, yCoord3, new Color(0x76ff03));//VERDE
    
                // Dibujar la cuarta figura
                drawBresenhamLine(prevX4, prevY4, xCoord4, yCoord4, new Color(0x00bfa5));//AQUUA
    
                // Dibujar la quinta figura
                drawBresenhamLine(prevX5, prevY5, xCoord5, yCoord5, new Color(0x2979ff));//AZUL
            }
    
            prevX = xCoord1; // Puedes usar prevX y prevY de manera diferente para cada figura si es necesario
            prevY = yCoord1;
    
            prevX2 = xCoord2; // Puedes usar prevX y prevY de manera diferente para cada figura si es necesario
            prevY2 = yCoord2;
    
            prevX3 = xCoord3; // Puedes usar prevX y prevY de manera diferente para cada figura si es necesario
            prevY3 = yCoord3;
    
            prevX4 = xCoord4; // Puedes usar prevX y prevY de manera diferente para cada figura si es necesario
            prevY4 = yCoord4;
    
            prevX5 = xCoord5; // Puedes usar prevX y prevY de manera diferente para cada figura si es necesario
            prevY5 = yCoord5;
        }
    
        // Actualizar el ángulo de rotación para la próxima iteración
        rotationAngle += 0.01; // Ajusta la velocidad de rotación según sea necesario
    }
    
    

    private double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        double[][] result = new double[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                double sum = 0;
                for (int k = 0; k < cols1; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    private void initializeCircleVertices() {
        int numVertices = 360; // Cantidad de vértices para aproximar el círculo
        circleVertices = new double[numVertices][3];

        for (int i = 0; i < numVertices; i++) {
            double angle = Math.toRadians(i * (360.0 / numVertices));
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            circleVertices[i] = new double[]{x, y, 1.0};
        }
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
            drawBresenhamLine(x1, y1, x2, y2, color);
        }
    }


//linea Bresenham
    public void drawBresenhamLine(int x1, int y1, int x2, int y2, Color color) {
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

    public void drawLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int err = dx - dy;
        int xStep = x1 < x2 ? 1 : -1;
        int yStep = y1 < y2 ? 1 : -1;

        while (x1 != x2 || y1 != y2) {
            putPixel(x1, y1, Color.ORANGE);
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
    }

    private void drawPacman(int x, int y, int radius, Color color) {
        int centerX = x + radius / 2;
        int centerY = y + radius / 2;
    
        for (int i = x; i < x + radius; i++) {
            for (int j = y; j < y + radius; j++) {
                int dx = i - centerX;
                int dy = j - centerY;
                double distance = Math.sqrt(dx * dx + dy * dy);
                
                if (distance <= radius / 2) {
                    // Dentro del círculo de "Pacman"
                    putPixel(i, j, color);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        graPixel.setColor(backgroundColor);
        graPixel.fillRect(0, 0, WIDTH, HEIGHT);

        
        // Incrementa los factores de escala en cada iteración para aumentar el tamaño del círculo
        transformationMatrix[0][0] += 0.1;
        transformationMatrix[1][1] += 0.1;

        // Incrementa el radio del círculo para la próxima iteración
        radius += 2;

        if (radius >= 5) {
            // Cuando el círculo alcanza un cierto tamaño, reinicia el radio y la escala
            transformationMatrix[0][0] = 1.0;
            transformationMatrix[1][1] = 1.0;
            radius = 2;

            // Aumenta la posición X para el siguiente círculo
            centerX += circleSpacing;
        }
        // Actualiza la animación
        movimiento();
        PhantomTras();
        PhantomTras2();
        PhantomTras3();
        fondo();

        repaint();
    }

    private int step = 0;
    private int colorCycle = 0;


    public void movimiento(){

        int speed = 7;

        // Guarda las coordenadas anteriores antes de actualizarlas
        prevXP = (int) translateX;
        prevYP = (int) translateY;

        switch (step) {
            case 0: // Mover hacia la derecha
                translateX += speed;
                if (translateX >= 260) {
                    step = 1; // Siguiente paso: abajo
                    shapeColor = Color.yellow;
                    
                }
                break;
            case 1: // Mover hacia abajo
                translateY += speed;
                if (translateY >= 520) {
                    step = 2; // Siguiente paso: izquierda
                }
                break;
            case 2: // Mover hacia la izquierda
                translateX -= speed;
                if (translateX <= 40) {
                    step = 3; // Siguiente paso: abajo
                     if (colorCycle % 2 == 0) {
                        shapeColor = new Color(0x76ff03);//verde
                    } 
                    colorCycle++;
                }
                break;
            case 3: // Mover hacia abajo
                translateY += speed;
                if (translateY >= 590) {
                     System.out.println("3");
                    step = 4; // Siguiente paso: derecha (reiniciar el movimiento)
                     
                }
                break;
            case 4: // Mover hacia la derecha
                translateX += speed;
                if (translateX >= 80) {
                    System.out.println("4");
                    step = 5; // Siguiente paso: abajo
                }
                break; 
            case 5: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 650) {
                     System.out.println("5");
                    step = 6; // Siguiente paso: derecha (reiniciar el movimiento)
                    
                }
                break;    
             case 6: // Mover hacia la derecha
                  translateX += speed;
                if (translateX >= 170) {
                    System.out.println("6");
                    step = 7; // Siguiente paso: arriba
                }
                break; 
            case 7: // Mover hacia la arriba
                  translateY -= speed;
                if (translateY <= 130) {
                     System.out.println("7");
                    step = 8; // Siguiente paso: derecha (reiniciar el movimiento)
                }
                break; 
             case 8: // Mover hacia la derecha
                  translateX += speed;
                if (translateX >= 250) {
                     System.out.println("8");
                    step = 9; // Siguiente paso: abajo
                    shapeColor = Color.YELLOW;
                   
                    colorCycle++;
                }
                break;   
              case 9: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 215) {
                     System.out.println("9");
                    step = 10; // Siguiente paso: derecha (reiniciar el movimiento)
                }
                break;  

            case 10: // Mover hacia la derecha
                  translateX += speed;
                if (translateX >= 340) {
                     System.out.println("10");
                    step = 11; // Siguiente paso: abajo
                }
                break;  
             case 11: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 290) {
                     System.out.println("11");
                    step = 12; // Siguiente paso: derecha (reiniciar el movimiento)
                }
                break;   
             case 12: // Mover hacia la izquierda
                translateX -= speed;
                if (translateX <= 255) {
                    System.out.println("12");
                    step = 13; // Siguiente paso: abajo
                }
                break;     
              case 13: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 520) {
                     System.out.println("13");
                    step = 14; // Siguiente paso: derecha (reiniciar el movimiento)
                }
                break;    
             case 14: // Mover hacia la izquierda
                translateX -= speed;
                if (translateX <= 165) {
                    System.out.println("14");
                    step = 15; // Siguiente paso: abajo
                }
                break;
            case 15: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 580) {
                     System.out.println("15");
                    step = 16; // Siguiente paso: derecha (reiniciar el movimiento)
                }
                break;     
             case 16: // Mover hacia la derecha
                translateX += speed;
                if (translateX >= 520) {
                    System.out.println("16");
                    step = 17; // Siguiente paso: abajo
                   
                }
                break; 
                 case 17: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 660) {
                     System.out.println("19");
                    step = 18; // Siguiente paso: derecha (reiniciar el movimiento)
                    
                }
                break;
                case 18: // Mover hacia la izquierda
                translateX -= speed;
                if (translateX <= 420) {
                    System.out.println("20");
                    step = 19; // Siguiente paso: abajo
                   
                }
                break; 
                  case 19: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 730) {
                     System.out.println("19");
                    step = 20; // Siguiente paso: derecha (reiniciar el movimiento)
                    
                }
                break;

                case 20: // Mover hacia la derecha
                  translateX += speed;
                if (translateX >= 730) {
                     System.out.println("20");
                    step = 21; // Siguiente paso: arriba
                }
                break;  
                 case 21: // Mover hacia la arriba
                  translateY -= speed;
                if (translateY <= 660) {
                     System.out.println("21");
                    step = 22; // Siguiente paso: izquierda (reiniciar el movimiento)
                    
                }
                break;
                case 22: // Mover hacia la izquierda
                translateX -= speed;
                if (translateX <= 600) {
                    System.out.println("22");
                    step = 23; // Siguiente paso: abajo
                     shapeColor = new Color(0xF50057);//rojo
                    colorCycle++;
                   
                }
                break; 
                
              case 23: // Mover hacia la arriba
                  translateY -= speed;
                if (translateY <= 140) {
                     System.out.println("23");
                    step = 24; // Siguiente paso: izquierda (reiniciar el movimiento)
                    shapeColor =  new Color(0xFF3D00);
                    colorCycle++;
                   
                   
                }
                break; 
                case 24: // Mover hacia la izquierda
                translateX -= speed;
                if (translateX <= 50) {
                    System.out.println("24");
                    step = 25; // Siguiente paso: abajo
                    shapeColor = new Color(0x2979ff);
                   
                }
                break; 
                 case 25: // Mover hacia la arriba
                  translateY -= speed;
                if (translateY <= 30) {
                     System.out.println("25");
                    step = 26; // Siguiente paso: derecha (reiniciar el movimiento)
                    
                }
                break;
                 case 26: // Mover hacia la derecha
                translateX += speed;
                if (translateX >= 170) {
                    System.out.println("26");
                    step = 27;// Siguiente paso: abajo
                   
                }
                break;
                  case 27: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 350) {
                     System.out.println("19");
                    step = 28; // Siguiente paso: derecha (reiniciar el movimiento)
                    
                }
                break;
                case 28: // Mover hacia la derecha
                translateX += speed;
                if (translateX >= 250) {
                    System.out.println("26");
                    step = 29;// Siguiente paso: abajo
                   
                }
                break;  
                 case 29: // Mover hacia la abajo
                  translateY += speed;
                if (translateY >= 430) {
                     System.out.println("19");
                    step = 30; // Siguiente paso: derecha (reiniciar el movimiento)
                     shapeColor = Color.YELLOW;
                   
                    colorCycle++;
                }
                break;  
                case 30: // Mover hacia la derecha
                translateX += speed;
                if (translateX >= 505) {
                    System.out.println("24");
                    step = 31; // Siguiente paso: arriba
                     shapeColor = new Color(0x00bfa5);
                     colorCycle++;
                    
                }
                break;
             case 31: // Mover hacia la arriba
                  translateY -= speed;
                if (translateY <= 370) {
                     System.out.println("25");
                    step = 32; // Siguiente paso: derecha (reiniciar el movimiento)
                    
                }
                break;  
            case 32: // Mover hacia la derecha
                translateX += speed;
                if (translateX >= 795) {
                    System.out.println("24");
                    step = 332;
                   
                }
                break; 
                       
        }

        // Agregar la posición actual al rastro
        trail.add(new Point(prevXP, prevYP));
        

        repaint();
    }


    public void fondo(){
     
        drawRectangle(400, 380, 190, 90);
        //rectangulo con recorte
        drawRectangle(400, 380, 180, 80);
        //recorteRectangle(400, 380, 180, 80,graPixel);

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
         // Agregar código para reproducir audio
       playSound("/Users/yazminurzuac/Desktop/6P/GRAFICAS2D/PARCIALII/projectII/pacmanAudio.wav");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        pacman animation = new pacman();
        frame.add(animation);

        frame.setVisible(true);
    }

    
    // Método para reproducir un archivo de sonido
    public static void playSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
}