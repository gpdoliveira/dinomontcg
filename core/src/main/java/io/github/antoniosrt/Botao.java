package io.github.antoniosrt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class Botao {
    private int buttonX, buttonY, buttonWidth, buttonHeight;
    private Texture buttonTexture;
    private int selected; //flag para marcar qual classe esta selecionada na tela SelecaoScreen

    public Botao(String texturePath, int buttonWidth, int buttonHeight) {
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        this.buttonY = Gdx.graphics.getHeight() / 2 - buttonHeight / 2;
        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
        this.selected = -1;
    }

    public boolean detectaClique() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            return touchX >= buttonX && touchX <= buttonX + buttonWidth && touchY >= buttonY && touchY <= buttonY + buttonHeight;
        }
        return false;
    }

    public void setButtonY(float offsetY) {
        this.buttonY -= offsetY;
    }

    public void setButtonX(float offsetX) {
        this.buttonX += offsetX;
    }

    public int getButtonX() {
        return buttonX;
    }

    public int getButtonY() {
        return buttonY;
    }


    public int getButtonWidth() {
        return buttonWidth;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public Texture getButtonTexture() {
        return buttonTexture;
    }

    public void setButtonTexture(String texturePath) {
        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
    }

    public int verificaClasse(int carn, int herb, int ven){
        if (carn ==1){
            return 1;
        } else if (herb ==1){
            return 2;
        } else if (ven ==1){
            return 3;
        }
        return 1;
    }

    public int getSelected() {
        return selected;
    }
    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void dispose() {
        buttonTexture.dispose();
    }
}

