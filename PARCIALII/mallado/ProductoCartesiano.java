package PARCIALII.mallado;

import java.util.ArrayList;
import java.util.List;

public class ProductoCartesiano {
    public static void main(String[] args) {
        int[] conjuntoA = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] conjuntoB = {3, 4, 5, 6, 7, 8, 9, 10};

        List<Pair> productoCartesiano = new ArrayList<>();

        for (int elementoA : conjuntoA) {
            for (int elementoB : conjuntoB) {
                productoCartesiano.add(new Pair(elementoA, elementoB));
            }
        }

        // Imprimir el producto cartesiano
        for (Pair par : productoCartesiano) {
            System.out.println("(" + par.x + ", " + par.y + ")");
        }
    }
}

class Pair {
    int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
