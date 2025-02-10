package io.github.antoniosrt;

public class MaoJogador {
    private Carta[] cartas;
    private int totalCartas = 3;

    private Carta cartaJogada;

    private int cartasTotalAtual = 0;

    public MaoJogador() {
        cartas = new Carta[totalCartas];
    }

    public void adicionarCarta(Carta carta,int i) {
            if (cartas[i] == null) {
                cartas[i] = carta;
        }
    }

    public Carta[] getCartas() {
        return cartas;
    }

    public void removerCarta(int index) {
        cartas[index] = null;
    }

    public int getTotalCartas() {
        int total = 0;
        for (int i = 0; i < totalCartas; i++) {
            if (cartas[i] != null) {
                total++;
            }
        }
        return total;
    }

    public Carta comprarCarta(Baralho baralho) {
        if(cartasTotalAtual > 3){
            System.out.println("Mão cheia");
            return null;
        }
        cartasTotalAtual++;
        Carta carta = baralho.retirarCarta();
         for (int i = 0; i < totalCartas; i++) {
             if(cartas[i] == null){
                 adicionarCarta(carta,i);
                 return cartas[i];
             }
         }
        return carta;
    }

    public Carta jogarCarta(int index) {
        cartaJogada = cartas[index];
        cartas[index] = null;
        cartasTotalAtual--;
        return cartaJogada;
    }
    public void encherMao(Baralho baralho){
        if(cartasTotalAtual > 3){
            return;
        }
        for (int i = 0; i < totalCartas; i++) {
            adicionarCarta(comprarCarta(baralho),i);
        }
    }

    public Carta getCartaJogada(){
        return cartaJogada;
    }
}
