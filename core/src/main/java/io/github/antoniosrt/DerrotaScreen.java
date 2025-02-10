package io.github.antoniosrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class DerrotaScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture gameImage;
    private Botao botaoHome;

    public DerrotaScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        gameImage = new Texture("telafinal/6.png");

        botaoHome = new Botao("homelogo.png", 80, 80);
        botaoHome.setButtonY(290);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f);
        batch.begin();
        batch.draw(gameImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(botaoHome.getButtonTexture(), botaoHome.getButtonX(), botaoHome.getButtonY(), botaoHome.getButtonWidth(), botaoHome.getButtonHeight());
        batch.end();

        if (botaoHome.detectaClique()){
            game.setScreen(new MenuScreen(game));
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
        gameImage.dispose();
        botaoHome.getButtonTexture().dispose();
    }
}
