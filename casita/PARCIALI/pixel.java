package PARCIALI;
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
        init();
    }

    public void init(){
        
        setTitle("Pixel");
        setSize(500,500);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
         
        

        buffer = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        graPixel = (Graphics2D) buffer.createGraphics();
    }

    public void putPixel( int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
       
    }

    
    public void paint(Graphics g){
        super.paint(g);
        putPixel(0,0, Color.RED);

    }
    public static void main(String[] args) {
        
        new pixel();
    }
}
