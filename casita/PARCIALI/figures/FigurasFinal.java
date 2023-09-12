package PARCIALI.figures;

public class FigurasFinal {
   

    public static void main(String[] args) {
       
            Drawfiguras frame = new Drawfiguras();
            //frame.setVisible(false);
    
            frame.algorithmRect(30,80, 160, 260);
            frame.algorithmRect(170, 100, 330, 100);
            frame.algorithmRect(460, 260, 580, 50);
            frame.algorithmRect(590, 260, 750, 260);
            //circulos
            frame.drawCircle(220, 480, 10);
            frame.drawCircle(220, 480, 40);
            frame.drawCircle(220, 480, 80);
            frame.drawCircle(220, 480, 120);
            //Elipse
            frame.drawEllipse(750, 480, 10, 5);
            frame.drawEllipse(750, 480, 30, 10);
            frame.drawEllipse(750, 480, 60, 20);
            frame.drawEllipse(750, 480, 120, 40);
            //Rectangulo
            frame.drawRectangle(450, 480, 120, 80);
            frame.drawRectangle(450, 480, 100, 60);
            frame.drawRectangle(450, 480, 80, 40);
            frame.drawRectangle(450, 480, 60, 20);
            frame.drawRectangle(450, 480, 40, 10);
    
        }
    
    }

