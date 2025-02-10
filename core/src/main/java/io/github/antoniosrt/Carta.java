package io.github.antoniosrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Carta {
    private int elemento;
    private int valor;
    private int id;
    private Texture texture;
    private String texturePath;

    private float height = 200;
    private float  width =120 ;
    private float cartaX;
    private float cartaY;

    public Carta(int elemento, int valor, int id, String texturePath){
        this.elemento = elemento;
        this.valor = valor;
        this.id = id;
        this.texturePath = texturePath;
        this.texture = new Texture(Gdx.files.internal(texturePath));
    }

    public int getElemento() {
        return elemento;
    }

    public Texture getTexture() {
        return texture;
    }
    public String getTexturePath() {
        return texturePath;
    }

    public boolean detectaClique() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            float cartaToqueRange = cartaY + height;
            float cartaToqueRangeX = cartaX + width;
            System.out.println("CartaX: " + cartaX + " CartaY: " + cartaY);
            System.out.println("CartaX: " + cartaToqueRangeX + " CartaY: " + cartaToqueRange);
            System.out.println("TouchX: " + touchX + " TouchY: " + touchY);
            return touchX >= cartaX && touchX <= cartaX + width && touchY >= cartaY && touchY <= cartaY + height;
        }
        return false;
    }

    public void setCartaY(float offsetY) {
        this.cartaY = offsetY;
    }
    public void setCartaX(float offsetX) {
        this.cartaX = offsetX;
    }
    public float getHeight() {
        return height;
    }
    public float getWidth() {
        return width;
    }
    public int getValor() {
        return valor;
    }
}
