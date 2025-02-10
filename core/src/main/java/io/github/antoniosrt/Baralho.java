package io.github.antoniosrt;

import java.util.Random;

public class Baralho {

    private int totalCartas = 30;
    private Carta[] cartas;
    private Random random;

    public Baralho() {
        cartas = new Carta[totalCartas];
        int i = 0;
        //0 - vento 1 - agua 2 - terra
        for (int tipo = 0; tipo < 3; tipo++) {
            for (int valor = 1; valor < 11; valor++) {
                String path = UtilHelper.getElementoPath(tipo);
                cartas[i] = new Carta(tipo, valor, i, path + "/" + valor + ".png");
                i++;
            }
        }
        cartas = embaralharCartas();
    }

    public Carta retirarCarta() {
        if (totalCartas == 0) return null; // Verifica se ainda há cartas no baralho
        Carta cartaTopo = cartas[totalCartas - 1]; // Acessa o índice correto
        cartas[totalCartas - 1] = null;
        // Define a posição como nula após retirar a carta
        this.totalCartas--;
        return cartaTopo;
    }

    public Carta[] embaralharCartas() {
        random = new Random();
        Carta[] cartasEmbaralhadas = new Carta[totalCartas];
        System.arraycopy(cartas, 0, cartasEmbaralhadas, 0, totalCartas); // Copiar cartas para novo array
        for (int i = totalCartas - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta temp = cartasEmbaralhadas[i];
            cartasEmbaralhadas[i] = cartasEmbaralhadas[j];
            cartasEmbaralhadas[j] = temp;
        }
        return cartasEmbaralhadas;
    }

    public int getTotalCartas() {
        return this.totalCartas;
    }


}
