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
        paint(g);
        putPixel(10,10, Color.RED);

    }

    public Color getPixelColor(int x, int y) {
        // Obtén el color del píxel en la ubicación (x, y) del buffer de la imagen
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            int rgb = buffer.getRGB(x, y);
            return new Color(rgb);
        } else {
            // Devuelve un valor predeterminado (por ejemplo, Color.BLACK) o maneja el error de tu elección
            return Color.BLACK;
        }
    }

    public static void main(String[] args) {
        
        new pixel();
    }


}
