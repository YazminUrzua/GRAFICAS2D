package GRAFICAS2D.casita;


import javax.swing.JFrame;
import java.awt.*;


public class casita extends javax.swing.JFrame{
    JFrame panel = new JFrame();

        public  casita() {
        init();
        }

    public void init(){
        setTitle("Casita");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //Backgraound 
        int w = getWidth(), h = getHeight();
        // Gradiente de fondo de morado a naranja
        Color morado = new Color(148, 0, 211);
        Color naranja = new Color(255, 165, 0);
        GradientPaint gp = new GradientPaint(0, 0, morado, w, h, naranja);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);

 
        //Ground (fill and borders)
        g.setColor(new Color(0x006414)); // Color verde oscuro
        g.fillRect(0, 430, 500, 80);
        g.setColor(new Color(0, 128, 0)); 
        g.drawRect(0, 430, 500, 80);


        //House's walls (fill and borders)
        {
        g.setColor(new Color(0xDAC9DF));;
        g.fillRect(150, 260, 210, 200);
        g.setColor(new Color(0xDAC9DF));;
        g.drawRect(150, 260, 210, 200);
        }
        {
        g.setColor(new Color(0xDAC9DF));;
        g.fillRect(250, 240, 200, 200);
        g.setColor(new Color(0xDAC9DF));;
        g.drawRect(250, 240, 200, 200);
        }

//Front walls
        {
        g.setColor(new Color(0xDAC9DF));
        g.fillRect(195, 370, 60, 100);
        }

         {
            int[] xTecho = {280, 250, 250, 280};
            int[] yTecho = {370, 370, 471, 462};
            g.setColor(new Color(0x81638B));
            g.fillPolygon(xTecho, yTecho, 4);
            }
        {
        g.setColor(new Color(0x81638B));
        g.fillRect(192, 380, 3,90);
        }

        {
        int[] xPoints = {230, 360, 150};
        int[] yPoints = {172, 260, 260};
        g.setColor(new Color(0xDAC9DF));
        g.fillPolygon(xPoints, yPoints, 3); // 
        }

        {
            int[] xTecho = {360, 465, 465, 360};
            int[] yTecho = {250, 255, 440, 460};
            g.setColor(new Color(0x81638B));
            g.fillPolygon(xTecho, yTecho, 4);
            }
//Roof
        {
            int[] xTecho = {230, 390, 480, 360};
            int[] yTecho = {170, 170, 260, 280};
            g.setColor(Color.black);
            g.fillPolygon(xTecho, yTecho, 4);
            }

            {
            int[] xTecho = {230, 135, 100, 125};
            int[] yTecho = {170, 280, 280, 280};
            g.setColor(Color.black);
            g.fillPolygon(xTecho, yTecho, 4);
            }

            // Luna
            {
            g.setColor(Color.WHITE);
            g.fillOval(20, 50, 70, 70);
            g.setColor(new Color(170, 0, 178));
            g.fillOval(49, 58, 48, 53);
             }

            //Moon
            {
            g.setColor(Color.WHITE); 
            g.fillOval(20, 50, 70, 70);
                {   
                    g.setColor(new Color(170, 0, 178)); 
                    g.fillOval(49, 58, 48, 53);
                }
            }
            //Door's roof
            {
            int[] xTecho = {260, 220, 250, 290};
            int[] yTecho = {350, 350, 390, 380};
            g.setColor(Color.black);
            g.fillPolygon(xTecho, yTecho, 4);
            }
            {
            int[] xTecho = {220, 220, 185, 190};
            int[] yTecho = {350, 350, 390, 380};
            g.setColor(Color.black);
            g.fillPolygon(xTecho, yTecho, 4);
            }
            //Door
            {
            g.setColor(new Color(139, 69, 19)); 
            g.fillRect(205, 405, 30,65);
            }
            //Window
             {   
            g.setColor(new Color(0x3DB6D9)); 
            g.fillOval(210, 240, 48, 53);
             }
            //window's door
             {   
            g.setColor(new Color(0x3DB6D9)); 
            g.fillOval(210, 365, 20, 20);
             }




             //tulon
               {   
            g.setColor(Color.WHITE); 
            g.fillOval(110, 415,20, 20);
             }
               {   
            g.setColor(Color.WHITE); 
            g.fillOval(110, 425, 20, 30);
             }
              {   
            g.setColor(Color.black); 
            g.fillOval(110, 450, 20,5);
             }
              {   
            g.setColor(Color.BLACK); 
            g.fillOval(105, 433, 10,20);
             }
        {
        int[] xPoints = {100, 115, 115};
        int[] yPoints = {425, 415, 430};
        g.setColor(Color.BLACK);
        g.fillPolygon(xPoints, yPoints, 3); // 
        }
        {
        int[] xPoints = {125, 130, 135};
        int[] yPoints = {416, 415, 425};
        g.setColor(Color.BLACK);
        g.fillPolygon(xPoints, yPoints, 3); // 
        }
        {   
            g.setColor(Color.white); 
            g.fillOval(125, 422, 8,10);
             }
          {   
            g.setColor(Color.BLACK); 
            g.fillOval(125, 422, 4,4);
             }
             {   
            g.setColor(Color.BLACK); 
            g.fillOval(120, 420, 3,3);
             }
              {   
            g.setColor(Color.BLACK); 
            g.fillOval(125, 418, 3,3);
             }
              {
            g.setColor(Color.BLACK); 
            g.fillRect(95, 445, 20,3);
            
            }
        //Tree
            {
            g.setColor(new Color(139, 69, 19)); 
            g.fillRect(50, 290, 20,170);
            }
            {
        int[] xPoints = {20, 60, 100};
        int[] yPoints = {400, 160, 400};
        g.setColor(new Color(0x009929));
        g.fillPolygon(xPoints, yPoints, 3); // 
        }
      
        

    }
     public static void main(String[] args) {
        new casita();
    }
    
    
}
