package io.github.antoniosrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Questao {
    private int id;
    private int respostaCorreta;
    private Texture questionTexture;

    public Questao(int id, int respostaCorreta, String texturePath) {
        this.id = id;
        this.respostaCorreta = respostaCorreta;
        this.questionTexture = new Texture(Gdx.files.internal(texturePath));
    }

    public int getId() {
        return id;
    }

    public int getRespostaCorreta() {
        return respostaCorreta;
    }

    public Texture getQuestionTexture() {
        return questionTexture;
    }
}
