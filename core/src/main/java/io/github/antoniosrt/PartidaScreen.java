package io.github.antoniosrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.audio.Music;
import java.util.Random;

public class PartidaScreen implements Screen {
    private Music somEmbaralhamento;
    private Music somCombateCartas;

    private final Main game;
    private SpriteBatch batch;
    private Texture image, image_questao, correto, errado;
    private BitmapFont font;
    private Partida partida;
    private boolean cartasSelecionadas = false;
    private boolean cartasRenderizadas = false;
    private boolean powerup1 = true, powerup2 = true;
    private int quiz = 0;
    private Botao botaoP1, botaoP2;

    private Questao questao;
    private Botao BotaoA, BotaoB, BotaoC, BotaoD;
    private int[] vetorquest = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int[] resp_corretas = {4, 1, 3, 2, 4, 4, 3, 4, 2};
    private int n_quest, respcorreta;
    private String TexturePath = ("QUIZ/");

    private int acertou = -1;
    private long tempoDeEspera = 0;
    private long tempoEsperaQuiz = 0;
    private Boolean quizVisivel = false;

    public PartidaScreen(Main game, int classe) {
        this.game = game;
        batch = new SpriteBatch();
        image = new Texture(Gdx.files.internal("playmat.png"));
        font = new BitmapFont();
        somEmbaralhamento = Gdx.audio.newMusic(Gdx.files.internal("musics/embaralhamento.mp3"));
        somEmbaralhamento.setVolume(0.7f);
        somCombateCartas = Gdx.audio.newMusic(Gdx.files.internal("musics/combateJogo.mp3"));
        somCombateCartas.setVolume(0.7f);
        botaoP2 = new Botao("powerupbuttons/theyon.png", 120, 60);
        botaoP2.setButtonX(300);
        botaoP2.setButtonY(-120);
        botaoP1 = new Botao("powerupbuttons/ouron.png", 120, 60);
        botaoP1.setButtonX(-250);
        botaoP1.setButtonY(-20);

        Random random = new Random();
        do {
            n_quest = random.nextInt(vetorquest.length + 1);
        } while (n_quest == 0);

        respcorreta = resp_corretas[n_quest-1];

        TexturePath = TexturePath + n_quest + ".png";
        System.out.println("Questao: " + n_quest + " Resposta correta: " + respcorreta);


        questao = new Questao(n_quest, respcorreta, TexturePath);
        image_questao = new Texture(TexturePath);
        correto = new Texture("correto.png");
        errado = new Texture("errado.png");
        int yBotoesQuiz = 80;
        int xBotoesQuiz = -240;
        BotaoA = new Botao("botoesquiz/4.png", 80, 80);
        BotaoA.setButtonY(yBotoesQuiz);
        BotaoA.setButtonX(xBotoesQuiz);

        BotaoB = new Botao("botoesquiz/5.png", 80, 80);
        BotaoB.setButtonY(yBotoesQuiz);
        BotaoB.setButtonX(xBotoesQuiz + 280);

        BotaoC = new Botao("botoesquiz/6.png", 80, 80);
        BotaoC.setButtonY(yBotoesQuiz+100);
        BotaoC.setButtonX(xBotoesQuiz );

        BotaoD = new Botao("botoesquiz/7.png", 80, 80);
        BotaoD.setButtonY(yBotoesQuiz+100);
        BotaoD.setButtonX(xBotoesQuiz + 280);

        iniciarPartida();
    }

    public void iniciarPartida() {
        this.partida = new Partida("Jogador 1", "Jogador 2");
        partida.startTurn();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        int x = Gdx.graphics.getWidth() / 2 - 180;
        int yMeio = Gdx.graphics.getHeight() / 2 + 70;
        int yFim = -Gdx.graphics.getHeight() + (Gdx.graphics.getHeight() - 100);

        batch.begin();
        batch.draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Tempo restante: " + (int) partida.getTimeLeft(), Gdx.graphics.getWidth() - 180, yMeio);
        font.draw(batch, "Turno: " + partida.getTurno(), Gdx.graphics.getWidth() - 180, yMeio - 20);

        // Renderizar as cartas jogadas no meio do tabuleiro se ambas foram selecionadas
        if (cartasSelecionadas && cartasRenderizadas) {
            Carta cartaJogada1 = partida.getJogador1().getMao().getCartaJogada();
            Carta cartaJogada2 = partida.getJogador2().getMao().getCartaJogada();
            if (cartaJogada1 != null) {
                batch.draw(cartaJogada1.getTexture(), Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100, cartaJogada1.getWidth(), cartaJogada1.getHeight());
            }
            if (cartaJogada2 != null) {
                batch.draw(cartaJogada2.getTexture(), Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 50, cartaJogada2.getWidth(), cartaJogada2.getHeight());
            }
        }
        //desenha as cartas do jogador 1
        for (int i = 0; i < 3; i++) {
            //adiciona os pontos de vitoria na tela jogador 1
            font.draw(batch, partida.getJogador1().getPontosVitoria(i), 55 + i * 90, -yFim);
            //adiciona os pontos de vitoria na tela jogador 2
            font.draw(batch, partida.getJogador2().getPontosVitoria(i), Gdx.graphics.getWidth() - 240 + i * 90, Gdx.graphics.getHeight() - 200);
            //adiciona as cartas na mao do jogador
            Carta carta = partida.getJogador1().getCartas()[i];
            if (carta != null) {
                float xCarta = x + i * 140;
                float yCarta = 15;
                //achar o meio da tela
                batch.draw(carta.getTexture(), x + i * 140, yCarta, carta.getWidth(), carta.getHeight());
                carta.setCartaX(xCarta);
                carta.setCartaY(yCarta);
//                System.out.println("Carta " + i + ": Elemento = " + carta.getElemento() + ", Texture = " + carta.getTexture());
            }
        }

        if (powerup1) {
            batch.draw(botaoP1.getButtonTexture(), botaoP1.getButtonX(), botaoP1.getButtonY(), botaoP1.getButtonWidth(), botaoP1.getButtonHeight());
        } else {
            quiz = 0;
            botaoP1.setButtonTexture("powerupbuttons/ouroff.png");
            batch.draw(botaoP1.getButtonTexture(), botaoP1.getButtonX(), botaoP1.getButtonY(), botaoP1.getButtonWidth(), botaoP1.getButtonHeight());
        }
        if (powerup2) {
            batch.draw(botaoP2.getButtonTexture(), botaoP2.getButtonX(), botaoP2.getButtonY(), botaoP2.getButtonWidth(), botaoP2.getButtonHeight());
        } else {
            botaoP1.setButtonTexture("powerupbuttons/theyoff.png");
            batch.draw(botaoP2.getButtonTexture(), botaoP2.getButtonX(), botaoP2.getButtonY(), botaoP2.getButtonWidth(), botaoP2.getButtonHeight());
        }

        if (quiz == 1 ) {
            int quizWidth = 720;
            int quizHeight = 450;
            batch.draw(image_questao, ((float) Gdx.graphics.getWidth() - quizWidth) / 2, ((float) Gdx.graphics.getHeight() - quizHeight) / 2, quizWidth, quizHeight);
            batch.draw(BotaoA.getButtonTexture(), BotaoA.getButtonX(), BotaoA.getButtonY(), BotaoA.getButtonWidth(), BotaoA.getButtonHeight());
            batch.draw(BotaoB.getButtonTexture(), BotaoB.getButtonX(), BotaoB.getButtonY(), BotaoB.getButtonWidth(), BotaoB.getButtonHeight());
            batch.draw(BotaoC.getButtonTexture(), BotaoC.getButtonX(), BotaoC.getButtonY(), BotaoC.getButtonWidth(), BotaoC.getButtonHeight());
            batch.draw(BotaoD.getButtonTexture(), BotaoD.getButtonX(), BotaoD.getButtonY(), BotaoD.getButtonWidth(), BotaoD.getButtonHeight());
            if (BotaoA.detectaClique()) {
                if (respcorreta == 1) {
                    acertou = 1;
                } else {
                    acertou = 0;
                }
            }
            if (BotaoB.detectaClique()) {
                if (respcorreta == 2) {
                    acertou = 1;
                } else {
                    acertou = 0;
                }
            }
            if (BotaoC.detectaClique()) {
                if (respcorreta == 3) {
                    acertou = 1;
                } else {
                    acertou = 0;
                }
            }
            if (BotaoD.detectaClique()) {
                if (respcorreta == 4) {
                    acertou = 1;
                } else {
                    acertou = 0;
                }
            }

        }
        if (acertou == 1) {
            if (!quizVisivel) {
                tempoEsperaQuiz = System.currentTimeMillis();
            }
            quizVisivel = true;
            batch.draw(correto, 0, 0, 160, 80);
            //faz o power up do jogador 1
            partida.powerUp(partida.getJogador1(), partida.getJogador2());
        } else if (acertou == 0) {
            if (!quizVisivel) {
                tempoEsperaQuiz = System.currentTimeMillis();
            }
            quizVisivel = true;
            batch.draw(errado, 0, 0, 160, 80);
            //faz o power up do jogador 2
            partida.powerUp(partida.getJogador2(), partida.getJogador1());

        }
//        System.out.println("Tempo de espera: " + tempoEsperaQuiz+ "Delta: " + delta);
        if ((System.currentTimeMillis() - tempoEsperaQuiz) >= 3 && quizVisivel) {
            System.out.println("Tempo de espera saida: " + tempoDeEspera);
            quiz = 0;
            quizVisivel = false;
        }
        batch.end();

        partida.setJogadaTurno(partida.validarJogadas());
        if (!partida.getJogada() && quiz != 1) {
            // Atualizar o tempo restante do turno
            partida.updateTurnTime(delta);
            if (partida.getTimeLeft() == partida.getTimeLeftMax()) {
                somCombateCartas.play();
                partida.getJogador1().selecionarCarta(0);
                partida.getJogador2().selecionarCarta(0);
                cartasSelecionadas = partida.getJogador1().getJogada() && partida.getJogador2().getJogada();
                if (cartasSelecionadas) {
                    tempoDeEspera = System.currentTimeMillis();
                    cartasRenderizadas = true;
                }
            }
        } else {
            if (cartasRenderizadas && (System.currentTimeMillis() - tempoDeEspera) >= 3000) {
                try {
                    // Aguarde 3 segundos antes de calcular o dano
                    int resultado = partida.combate(somEmbaralhamento,somCombateCartas);
                    if (resultado == 1) {
                        game.setScreen(new VitoriaScreen(game));
                    }
                    if (resultado == 2) {
                        game.setScreen(new DerrotaScreen(game));
                    }
                    cartasSelecionadas = false;
                    cartasRenderizadas = false;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //verifica a toda hora
        int resultadoPartida = partida.verificarVitoria();
        if (resultadoPartida == 1) {
            game.setScreen(new VitoriaScreen(game));
        }
        if (resultadoPartida == 2) {
            game.setScreen(new DerrotaScreen(game));
        }
        for (int i = 0; i < partida.getJogador1().getMao().getTotalCartas(); i++) {
            Carta carta = partida.getJogador1().getCartas()[i];
            if (carta == null) {
                continue;
            }
            if (quiz != 1 && carta.detectaClique() && !partida.validarJogadas()) {
                somCombateCartas.play();
                partida.getJogador1().selecionarCarta(i);
                partida.getJogador2().selecionarCarta(i);
                cartasSelecionadas = partida.getJogador1().getJogada() && partida.getJogador2().getJogada();
                if (cartasSelecionadas) {
                    tempoDeEspera = System.currentTimeMillis();
                    cartasRenderizadas = true;
                }
            }
        }
//logica do power up
        if (botaoP1.detectaClique() && powerup1) {
            quiz = 1;
            botaoP1.setButtonTexture("powerupbuttons/ouroff.png");
        }

    }

    public static Boolean manualSleep(long millis) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < millis) {
            // Loop vazio para "dormir"
        }
        return true;
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
        image_questao.dispose();
        BotaoA.dispose();
        BotaoB.dispose();
        BotaoC.dispose();
        BotaoD.dispose();
        botaoP1.dispose();
        botaoP2.dispose();
    }
}
