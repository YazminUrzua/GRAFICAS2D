package PARCIALII.projectII;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteExample extends JPanel {

    private BufferedImage spriteSheet;
    private BufferedImage currentFrame;

    public SpriteExample() {
        try {
            // Carga la imagen sprite desde un archivo
            spriteSheet = ImageIO.read(new File("/Users/yazminurzuac/Desktop/6P/GRAFICAS2D/PARCIALII/triangulosRombos/pngegg.png"));
            // Define el cuadro inicial que deseas mostrar (coordenadas x, y, ancho, alto)
            currentFrame = spriteSheet.getSubimage(10, 10, 32, 32);
            

        } catch (IOException e) {
            e.printStackTrace();
              //spriteSheet = ImageIO.read(new File("/Users/yazminurzuac/Desktop/6P/GRAFICAS2D/PARCIALII/triangulosRombos/pngegg.png"));
            // Define el cuadro inicial que deseas mostrar (coordenadas x, y, ancho, alto)
            currentFrame = spriteSheet.getSubimage(10, 10, 32, 32);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja el cuadro actual en el JPanel
        g.drawImage(currentFrame, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sprite Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        SpriteExample spritePanel = new SpriteExample();
        frame.add(spritePanel);

        frame.setVisible(true);
    }
}
