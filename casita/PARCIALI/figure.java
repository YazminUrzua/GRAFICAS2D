package PARCIALI;
import javax.swing.JFrame;
import java.awt.*;


public class figure extends javax.swing.JFrame{
    JFrame panel = new JFrame();

        public figure() {
        init();
        }

    public void init(){
        setTitle("Geometric figures");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void paint(Graphics g){
        super.paint(g);

        //Square (fill and borders)
        g.setColor(Color.GREEN);
        g.fillRect(250, 200, 200, 200);
        g.setColor(Color.GREEN);
        g.drawRect(250, 200, 200, 200);
       
        
        //Rectangle (fill and borders)
        g.setColor(Color.BLUE);
        g.fillRect(30, 100, 350, 60);
        g.setColor(Color.BLACK);
        g.drawRect(30, 100, 350, 60);

        
        //Circle
        g.setColor(Color.RED);
        g.drawOval(100, 200, 100, 100);
 
    }
     public static void main(String[] args) {
        new figure();
    }
    
    
}
