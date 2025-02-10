package io.github.antoniosrt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private static AudioManager instance;
    private Music currentMusic;
    private float volume = 1.0f; // Volume padrão

    private AudioManager() {}

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (currentMusic != null) {
            currentMusic.setVolume(volume);
        }
    }

    public void playMusic(String filePath, boolean looping) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        currentMusic.setLooping(looping);
        currentMusic.setVolume(volume);
        currentMusic.play();
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }

    public void dispose() {
        if (currentMusic != null) {
            currentMusic.dispose();
        }
    }
}
