package io.github.antoniosrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture image;
    private Botao botaoJogar, botaoHelp, botaoCartas;

    public MenuScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        image = new Texture("menubg.png");

        botaoJogar = new Botao("botaojogar.png", 140, 70);

        botaoHelp = new Botao("botaohelp.png", 140, 70);
        botaoHelp.setButtonY(100);

        botaoCartas = new Botao("botaocartas.png", 140, 70);
        botaoCartas.setButtonY(200);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(botaoHelp.getButtonTexture(), botaoHelp.getButtonX(), botaoHelp.getButtonY(), botaoHelp.getButtonWidth(), botaoHelp.getButtonHeight());
        batch.draw(botaoCartas.getButtonTexture(), botaoCartas.getButtonX(), botaoCartas.getButtonY(), botaoCartas.getButtonWidth(), botaoCartas.getButtonHeight());
        batch.draw(botaoJogar.getButtonTexture(), botaoJogar.getButtonX(), botaoJogar.getButtonY(), botaoJogar.getButtonWidth(), botaoJogar.getButtonHeight());
        batch.end();

        if (botaoHelp.detectaClique()) {
            game.setScreen(new Help(game));
        }

        if (botaoCartas.detectaClique()) {
            game.setScreen(new Colecao(game));
        }

        if (botaoJogar.detectaClique()) {
            game.setScreen(new SelecaoScreen(game));
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
        image.dispose();
        botaoHelp.getButtonTexture().dispose();
        botaoJogar.getButtonTexture().dispose();
        botaoCartas.getButtonTexture().dispose();
    }
}
