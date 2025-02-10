package io.github.antoniosrt;

public class Jogador {
    private String nome;
    private int[] vitorias = {0, 0, 0};
    private int[] derrotas = {0, 0, 0};
    private int[] empates = {0, 0, 0};
    private Boolean jogadaTurno = false;
    private Boolean powerUp = false;
    private MaoJogador Mao;

    private Baralho baralho;

    public Jogador(String nome) {
        this.nome = nome;
        this.baralho = new Baralho();
    }

    public int perdePontosPowerUp() {
        if (powerUp) {
            return -1;
        }
        powerUp = true;
        int[] vitoriasExistentes = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            //verifica se tem alguma vitoria
            if (vitorias[i] > 0) {
                vitoriasExistentes[i] = 1;
            }
        }
        //se nao tiver nenhuma vitoria retorna -1
        if (vitoriasExistentes[0] == 0 && vitoriasExistentes[1] == 0 && vitoriasExistentes[2] == 0) {
            return -1;
        }
        //se pelo menos um dos elementos tiver vitoria seleciona random entre os que tem e tira um deles
        int index = (int) (Math.random() * 3);
        while (vitoriasExistentes[index] == 0) {
            index = (int) (Math.random() * 3);
        }
        vitorias[index]--;
        return index;
    }

    public void selecionarCarta(int indexCarta) {
        this.jogadaTurno = true;
        this.Mao.jogarCarta(indexCarta);
        System.out.println("Jogador " + this.nome + " jogou carta " + indexCarta);
        System.out.println("Carta jogada: " + this.Mao.getCartaJogada().getElemento());
        System.out.println("Total de cartas na mão: " + this.Mao.getTotalCartas());
        return;
    }

    public String getNome() {
        return nome;
    }


    public void iniciarJogo() {
        baralho.embaralharCartas();
        this.Mao = new MaoJogador();
        this.Mao.encherMao(baralho);
//        System.out.println("Mão do jogador " + this.nome + ": Quantidade de cartas: " + this.Mao.getTotalCartas());
    }

    public void comprarCarta() {
        this.Mao.comprarCarta(baralho);
    }

    public Carta[] getCartas() {
        return this.Mao.getCartas();
    }

    public MaoJogador getMao() {
        return this.Mao;
    }

    public void setJogada(Boolean jogada) {
        this.jogadaTurno = jogada;
    }

    public Boolean getJogada() {
        return this.jogadaTurno;
    }

    public String getPontosVitoria(int index) {
        return Integer.toString(vitorias[index]);
    }

    public void setVitorias(int index) {
        this.vitorias[index]++;
    }

    public int getVitorias(int index) {
        return vitorias[index];
    }
}
