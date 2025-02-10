package io.github.antoniosrt;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsMenu implements Screen {
    private Stage stage;
    private Skin skin;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("./ui/uiskin.json"));

        // Slider para controlar o volume
        Slider volumeSlider = new Slider(0, 1, 0.01f, false, skin);
        volumeSlider.setValue(AudioManager.getInstance().getVolume());

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float newVolume = volumeSlider.getValue();
                AudioManager.getInstance().setVolume(newVolume);
            }
        });

        volumeSlider.setPosition(50, Gdx.graphics.getHeight() / 2);
        volumeSlider.setSize(300, 20);
        stage.addActor(volumeSlider);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (skin != null) skin.dispose();
    }
}
