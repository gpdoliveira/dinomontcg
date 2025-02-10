package io.github.antoniosrt;

import com.badlogic.gdx.audio.Music;

import static io.github.antoniosrt.UtilHelper.*;

public class Partida {
    private Jogador jogador1;
    private Jogador jogador2;
    private int turno;
    private float timeLeft; // Tempo restante para o turno
    private float timeLeftMax = 30.0f;
    private Boolean jogadaTurno = false;


    public Partida(String nomeJogador1, String nomeJogador2) {
        this.jogador1 = new Jogador(nomeJogador1);
        this.jogador2 = new Jogador(nomeJogador2);
        this.turno = 1;
        this.timeLeft = timeLeftMax; // 30 segundos para cada turno
        jogador1.iniciarJogo();
        jogador2.iniciarJogo();
    }

    public void updateTurnTime(float delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            endTurn();
        }
//        System.out.println("Tempo restante: " + timeLeft);
    }

    public void startTurn() {
        timeLeft = timeLeftMax; // Reiniciar o tempo do turno
    }

    private void endTurn() {
        if (getJogada()) {
            return;
        }
        startTurn(); // Iniciar próximo turno
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public Boolean getJogada() {
        return jogadaTurno;
    }

    public boolean validarJogadas() {
        //validar jogadas
        if ((jogador1.getMao().getTotalCartas() == 2 && jogador1.getJogada()) && (jogador2.getJogada() && jogador2.getMao().getTotalCartas() == 2)) {
            jogadaTurno = true;
            return true;
        }
        jogadaTurno = false;
        return false;
    }
    public int getTurno(){
        return turno;
    }
    public void setJogadaTurno(Boolean jogada){
        jogadaTurno = jogada;
    }
    public int combate(Music embaralhamento,Music combateCartasMusic) throws InterruptedException {
//        Thread.sleep(5000);
        int resultado = combateCartas(embaralhamento,combateCartasMusic);
        if(resultado == 1){
            System.out.println("Jogador 1 venceu o combate");
        }
        else if(resultado == -1){
            System.out.println("Jogador 2 venceu o combate");
        }
        else{
            System.out.println("Empate");
        }
        int resultadoVitoria = verificarVitoria();
        if(resultadoVitoria !=0){
            return resultadoVitoria;
        }
        return 0;
    }

    public void powerUp(Jogador jogadorGanhador, Jogador jogadorPerdedor) {
        int elementoPerdeuPonto = jogadorPerdedor.perdePontosPowerUp();
        if (elementoPerdeuPonto != -1) {
            jogadorGanhador.setVitorias(elementoPerdeuPonto);
            System.out.println("Jogador " + jogadorPerdedor.getNome() + " perdeu um ponto de " + getElementoNome(elementoPerdeuPonto));
        }
    }
    public int combateCartas(Music embaralhamento,Music combateCartasMusic) throws InterruptedException {
        Carta cartaJogador1 = jogador1.getMao().getCartaJogada();
        Carta cartaJogador2 = jogador2.getMao().getCartaJogada();
        if(cartaJogador1.getElemento() == cartaJogador2.getElemento()){
             validaEmpatePorForcaCarta(cartaJogador1,cartaJogador2);
        }
        //Pega por elemento da carta do jogador 1
        switch (cartaJogador1.getElemento()){
            //agua ganha e terra perde
            case VENTO:
                if(cartaJogador2.getElemento() == AGUA){
                    //meu elemento eh vento e perdeu pra agua jogador 2 ganha com o elemento dele
                    pontuarVitoria(jogador2,cartaJogador2.getElemento());
                }
                else if(cartaJogador2.getElemento() == TERRA){
                    pontuarVitoria(jogador1,VENTO);
                }
                break;

          //agua vence ar e perde para terra
          case  AGUA:
                if(cartaJogador2.getElemento() == VENTO){
                    pontuarVitoria(jogador1,AGUA);
                }
                else if(cartaJogador2.getElemento() == TERRA){
                    //meu elemento eh agua e perdeu pra terra jogador 2 ganha com o elemento dele
                    pontuarVitoria(jogador2,cartaJogador2.getElemento());
                }
                break;
           case TERRA:
                if(cartaJogador2.getElemento() == VENTO){
                    pontuarVitoria(jogador2,cartaJogador2.getElemento());
                }
                else if(cartaJogador2.getElemento() == AGUA){
                    pontuarVitoria(jogador1,TERRA);
                }
                break;
        }
        embaralhamento.play();
        iniciarNovaRodada();
        return -1;
    }

    public void pontuarVitoria(Jogador jogador,int elemento){
        jogador.setVitorias(elemento);
    }
    public int verificarVitoria(){
        for(int i = 0; i < 3; i++){
            if(jogador1.getVitorias(i) == 3){
                return 1;
            }
            else if(jogador2.getVitorias(i) == 3){
                return 2;
            }
        }
        return 0;
    }
    public void iniciarNovaRodada(){
        turno++;
        timeLeft = timeLeftMax;
        jogador1.comprarCarta();
        jogador2.comprarCarta();
        jogador1.setJogada(false);
        jogador2.setJogada(false);
        jogadaTurno = false;
    }
    public float getTimeLeftMax(){
        return timeLeftMax;
    }
    public int validaEmpatePorForcaCarta(Carta cartaJog1,Carta cartaJog2){
        if(cartaJog1.getValor() == cartaJog2.getValor()){
            return 0;
        }
        if(cartaJog1.getValor() > cartaJog2.getValor()){
            pontuarVitoria(jogador1,cartaJog1.getElemento());
            return 1;
        }
        else if(cartaJog1.getValor() < cartaJog2.getValor()){
            pontuarVitoria(jogador2,cartaJog2.getElemento());
            return 2;
        }
        return -1;
    }
}
