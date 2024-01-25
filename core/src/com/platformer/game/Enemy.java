package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Sprite implements Collideable, Drawable, Updatable, Creatable {

    public static final float VELOCITY_SCALAR = 250.f;
    private Vector2 velocity;

    private String texturePath;
    public Enemy(String texturePath, int x, int y, int width, int height) {
        this.setX(x);
        this.setY(y);
        this.setSize(width, height);

        this.texturePath = texturePath;

        this.velocity = new Vector2(0, -3.f);
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void create() {
        Texture texture = new Texture(texturePath);
        this.setTexture(texture);
        this.setRegion(0, 0, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                this,
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight()
        );
    }

    @Override
    public void update() {

        this.velocity.nor().scl(VELOCITY_SCALAR);

        this.setPosition(
                this.getX() + this.velocity.x * Gdx.graphics.getDeltaTime(),
                this.getY() + this.velocity.y * Gdx.graphics.getDeltaTime()
        );
    }
}
