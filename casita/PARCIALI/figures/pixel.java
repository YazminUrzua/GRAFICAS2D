package PARCIALI.figures;
import static java.awt.Color.blue;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class pixel extends javax.swing.JFrame{
    JFrame panel = new JFrame();
    private BufferedImage buffer;
    private Graphics graPixel;
    
    public pixel() {
 
    setTitle("Figuras");
    setSize(900,700);
    setResizable(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    setLocationRelativeTo(null);
        
    buffer = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
    graPixel = (Graphics2D) buffer.createGraphics();


    }
    

    public void putPixel( int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
       
    }

    
    public void paint(Graphics g){
        super.paint(g);
        putPixel(80,80, Color.RED);

    }
   public static void main(String[] args) {
        
        new pixel();
    } 
}
