package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import static com.platformer.game.Const.Enemy.ENEMY_VELOCITY_SCALAR;

public class Enemy extends Sprite implements Collideable, Drawable, Updatable {

    private List<Projectile> projectiles;
    private Vector2 velocity;
    private boolean shouldRemove;

    private int rewardedScore = 0;

    public Enemy(Texture texture, int x, int y, int width, int height) {
        this.setX(x);
        this.setY(y);
        this.setSize(width, height);

        this.setTexture(texture);
        this.setRegion(0, texture.getHeight(), texture.getWidth(), -texture.getHeight());

        this.velocity = new Vector2(0, -3.f);
    }

    public void setCollideablePool(List<Projectile> collideables) {
        this.projectiles = collideables;
    }

    private void checkCollisions() {
        this.projectiles.forEach(projectile -> {
            if(CollisionUtil.isColliding(this, projectile)) setShouldRemove(true);
        });
    }

    private void setShouldRemove(boolean shouldRemove) {
        this.shouldRemove = shouldRemove;
    }

    @Override
    public boolean shouldRemove() {
        return this.shouldRemove;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
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

        this.velocity.nor().scl(ENEMY_VELOCITY_SCALAR);

        this.setPosition(
                this.getX() + this.velocity.x * Gdx.graphics.getDeltaTime(),
                this.getY() + this.velocity.y * Gdx.graphics.getDeltaTime()
        );

        this.checkCollisions();
    }

    public void setRewardedScore(int value) {
        this.rewardedScore = value;
    }

    public int getRewardedScore() {
        return this.rewardedScore;
    }
}
