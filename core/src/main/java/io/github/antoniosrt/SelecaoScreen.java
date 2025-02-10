package io.github.antoniosrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SelecaoScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture image;
    private Botao botaoFacil, botaoDificil, botaoCarn, botaoHerb, botaoVen, botaoBack;
    private int classe;

    public SelecaoScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        image = new Texture(Gdx.files.internal("selectmenu.png"));

        botaoFacil = new Botao("botaofacil.png", 140, 70);
        botaoFacil.setButtonY(320);
        botaoFacil.setButtonX(-150);
        botaoDificil = new Botao("botaodificil.png", 140, 70);
        botaoDificil.setButtonY(320);
        botaoDificil.setButtonX(150);

        botaoBack = new Botao("helpback.png", 80, 80);
        botaoBack.setButtonY(-350);
        botaoBack.setButtonX(400);

        botaoCarn = new Botao("classes/4.png", 300, 160);
        botaoCarn.setButtonY(-120);
        botaoCarn.setButtonX(-350);
        botaoCarn.setSelected(1);

        botaoHerb = new Botao("classes/2.png", 300, 160);
        botaoHerb.setButtonY(-120);
        botaoHerb.setButtonX(0);

        botaoVen = new Botao("classes/3.png", 300, 160);
        botaoVen.setButtonY(-120);
        botaoVen.setButtonX(350);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(botaoFacil.getButtonTexture(), botaoFacil.getButtonX(), botaoFacil.getButtonY(), botaoFacil.getButtonWidth(), botaoFacil.getButtonHeight());
        batch.draw(botaoDificil.getButtonTexture(), botaoDificil.getButtonX(), botaoDificil.getButtonY(), botaoDificil.getButtonWidth(), botaoDificil.getButtonHeight());
        batch.draw(botaoCarn.getButtonTexture(), botaoCarn.getButtonX(), botaoCarn.getButtonY(), botaoCarn.getButtonWidth(), botaoCarn.getButtonHeight());
        batch.draw(botaoHerb.getButtonTexture(), botaoHerb.getButtonX(), botaoHerb.getButtonY(), botaoHerb.getButtonWidth(), botaoHerb.getButtonHeight());
        batch.draw(botaoVen.getButtonTexture(), botaoVen.getButtonX(), botaoVen.getButtonY(), botaoVen.getButtonWidth(), botaoVen.getButtonHeight());
        batch.draw(botaoBack.getButtonTexture(), botaoBack.getButtonX(), botaoBack.getButtonY(), botaoBack.getButtonWidth(), botaoBack.getButtonHeight());
        batch.end();

        if (Gdx.input.justTouched()) {
            if (botaoBack.detectaClique()) {
                game.setScreen(new MenuScreen(game));
            }

            if (botaoCarn.detectaClique()) {
                if (botaoCarn.getSelected() == 0) {
                    botaoCarn.setButtonTexture("classes/4.png");
                    botaoCarn.setSelected(1);
                    botaoHerb.setButtonTexture("classes/2.png");
                    botaoHerb.setSelected(0);
                    botaoVen.setButtonTexture("classes/3.png");
                    botaoVen.setSelected(0);
                } else {
                    botaoCarn.setButtonTexture("classes/1.png");
                    botaoCarn.setSelected(0);
                }
            }

            if (botaoHerb.detectaClique()) {
                if (botaoHerb.getSelected() == 0) {
                    botaoHerb.setButtonTexture("classes/5.png");
                    botaoHerb.setSelected(1);
                    botaoCarn.setButtonTexture("classes/1.png");
                    botaoCarn.setSelected(0);
                    botaoVen.setButtonTexture("classes/3.png");
                    botaoVen.setSelected(0);
                } else {
                    botaoHerb.setButtonTexture("classes/2.png");
                    botaoHerb.setSelected(0);
                }
            }

            if (botaoVen.detectaClique()) {
                if (botaoVen.getSelected() == 0) {
                    botaoVen.setButtonTexture("classes/6.png");
                    botaoVen.setSelected(1);
                    botaoHerb.setButtonTexture("classes/2.png");
                    botaoHerb.setSelected(0);
                    botaoCarn.setButtonTexture("classes/1.png");
                    botaoCarn.setSelected(0);
                } else {
                    botaoVen.setButtonTexture("classes/3.png");
                    botaoVen.setSelected(0);
                }
            }
        }

        if (botaoFacil.detectaClique()) {
            classe = botaoFacil.verificaClasse(botaoCarn.getSelected(),botaoHerb.getSelected(),botaoVen.getSelected());
            game.setScreen(new PartidaScreen(game, classe));
        }

        if (botaoDificil.detectaClique()) {
            classe = botaoFacil.verificaClasse(botaoCarn.getSelected(),botaoHerb.getSelected(),botaoVen.getSelected());
            game.setScreen(new PartidaScreen(game, classe));
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
        if (batch != null) {
            batch.dispose();
        }
        if (image != null) {
            image.dispose();
        }
        if (botaoFacil != null && botaoFacil.getButtonTexture() != null) {
            botaoFacil.getButtonTexture().dispose();
        }
        if (botaoDificil != null && botaoDificil.getButtonTexture() != null) {
            botaoDificil.getButtonTexture().dispose();
        }
        if (botaoCarn != null && botaoCarn.getButtonTexture() != null) {
            botaoCarn.getButtonTexture().dispose();
        }
        if (botaoHerb != null && botaoHerb.getButtonTexture() != null) {
            botaoHerb.getButtonTexture().dispose();
        }
        if (botaoVen != null && botaoVen.getButtonTexture() != null) {
            botaoVen.getButtonTexture().dispose();
        }
        if (botaoBack != null && botaoBack.getButtonTexture() != null) {
            botaoBack.getButtonTexture().dispose();
        }
    }

}
