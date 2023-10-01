package PARCIALII;
import java.awt.Color;

public class TranslationCircleAnimationWithThreads {
    private PARCIALI.pixel pixel; // Instancia de la clase Pixel
    private int xc, yc, radio; // Coordenadas y radio del círculo
    private int deltaX, deltaY; // Cantidades de desplazamiento en x e y

    public TranslationCircleAnimationWithThreads() {
        pixel = new PARCIALI.pixel(); // Inicializa la instancia para manejar píxeles
        pixel.setBackground(Color.WHITE); // Establece el fondo de la ventana en blanco
        xc = 80; // Coordenada x del centro del círculo
        yc = 80; // Coordenada y del centro del círculo
        radio = 40; // Radio del círculo
        deltaX = 1; // Cantidad de desplazamiento en x en cada iteración
        deltaY = 1; // Cantidad de desplazamiento en y en cada iteración
    }

    public void drawCircle() {
        // Bucle para borrar los puntos del círculo anterior (dibujados en blanco)
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));
            pixel.putPixel(xc + x, yc + y, Color.WHITE);
            pixel.putPixel(xc - x, yc + y, Color.WHITE);
            pixel.putPixel(xc + x, yc - y, Color.WHITE);
            pixel.putPixel(xc - x, yc - y, Color.WHITE);
            pixel.putPixel(xc + y, yc + x, Color.WHITE);
            pixel.putPixel(xc - y, yc + x, Color.WHITE);
            pixel.putPixel(xc + y, yc - x, Color.WHITE);
            pixel.putPixel(xc - y, yc - x, Color.WHITE);
        }

        // Calcula las nuevas coordenadas del centro
        xc += deltaX;
        yc += deltaY;

        // Bucle para dibujar el círculo trasladado en las nuevas coordenadas (naranja)
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));
            pixel.putPixel(xc + x, yc + y, Color.ORANGE);
            pixel.putPixel(xc - x, yc + y, Color.ORANGE);
            pixel.putPixel(xc + x, yc - y, Color.ORANGE);
            pixel.putPixel(xc - x, yc - y, Color.ORANGE);
            pixel.putPixel(xc + y, yc + x, Color.ORANGE);
            pixel.putPixel(xc - y, yc + x, Color.ORANGE);
            pixel.putPixel(xc + y, yc - x, Color.ORANGE);
            pixel.putPixel(xc - y, yc - x, Color.ORANGE);
        }
    }

    public void startAnimation() {
        // Crea un nuevo hilo para la animación
        Thread animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    drawCircle(); // Llama a drawCircle para actualizar el círculo
                    try {
                        Thread.sleep(20); // Controla la velocidad de la animación
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        animationThread.start(); // Inicia el hilo de la animación
    }

    public static void main(String[] args) {
        // Crea una instancia de TranslationCircleAnimationWithThreads
        TranslationCircleAnimationWithThreads animation = new TranslationCircleAnimationWithThreads();
        // Inicia la animación
        animation.startAnimation();
    }
}
