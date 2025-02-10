package io.github.antoniosrt;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class Colecao implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private List<Carta> cartas;
    private Botao setaEsq, setaDir, botaoBack;
    private int currentIndex;
    private Texture image;

    public Colecao(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        cartas = new ArrayList<>();
        currentIndex = 0;
        image = new Texture("menubg.png");

        botaoBack = new Botao("helpback.png", 80, 80);
        botaoBack.setButtonY(-180);
        botaoBack.setButtonX(260);

        setaEsq = new Botao("setaesq.png", 80, 80);
        setaDir = new Botao("setadir.png", 80, 80);

        setaDir.setButtonX(250);
        setaEsq.setButtonX(-250);

        for (int i = 1; i <= 10; i++) {
            adicionarCarta(new Carta(0, i, i, "vento/" + i + ".png"));
            adicionarCarta(new Carta(1, i, i, "agua/" + i + ".png"));
            adicionarCarta(new Carta(2, i, i, "terra/" + i + ".png"));
        }
    }

    public void adicionarCarta(Carta carta) {
        cartas.add(carta);
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;
        batch.draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(botaoBack.getButtonTexture(), botaoBack.getButtonX(), botaoBack.getButtonY(), botaoBack.getButtonWidth(), botaoBack.getButtonHeight());

        if (!cartas.isEmpty()) {
            Carta carta = cartas.get(currentIndex);
            batch.draw(carta.getTexture(), centerX - 150, centerY - 325, 350, 650); // Desenha a carta com 300x650 pixels
        }

        batch.draw(setaEsq.getButtonTexture(), setaEsq.getButtonX(), setaEsq.getButtonY(), setaEsq.getButtonWidth(), setaEsq.getButtonHeight());
        batch.draw(setaDir.getButtonTexture(), setaDir.getButtonX(), setaDir.getButtonY(), setaDir.getButtonWidth(), setaDir.getButtonHeight());
        batch.end();

        if (Gdx.input.justTouched()) {
            if (setaEsq.detectaClique()) {
                currentIndex = (currentIndex - 1 + cartas.size()) % cartas.size();
            }
            if (setaDir.detectaClique()) {
                currentIndex = (currentIndex + 1) % cartas.size();
            }
        }

        if (botaoBack.detectaClique()) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
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
        setaEsq.getButtonTexture().dispose();
        setaDir.getButtonTexture().dispose();
        for (Carta carta : cartas) {
            carta.getTexture().dispose();
        }
    }
}
