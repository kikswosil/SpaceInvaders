package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;


public class Enemy extends Sprite implements Collideable, Drawable, Updatable {

    private final List<Projectile> projectiles;
    private final Vector2 velocity = new Vector2(0, -3.f);
    private final float speedScalar;
    private boolean shouldRemove;

    private int rewardedScore = 0;

    public Enemy(
            Texture texture,
            int x,
            int y,
            int width,
            int height,
            List<Projectile> collideablePool,
            List<Enemy> enemyPool,
            int rewardedScore,
            float speedScalar
    ) {
        this.setX(x);
        this.setY(y);
        this.setSize(width, height);

        this.setTexture(texture);
        this.setRegion(0, texture.getHeight(), texture.getWidth(), -texture.getHeight());

        this.projectiles = collideablePool;
        this.rewardedScore = rewardedScore;
        this.speedScalar = speedScalar;

        enemyPool.add(this);
    }

    private void checkCollisions() {
        this.projectiles.forEach(projectile -> {
            if(CollisionUtil.isColliding(this, projectile)) {
                Enemy.this.shouldRemove = true;
                projectile.setShouldRemove(true);
            }
        });
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

        this.velocity.nor().scl(this.speedScalar);

        this.setPosition(
                this.getX() + this.velocity.x * Gdx.graphics.getDeltaTime(),
                this.getY() + this.velocity.y * Gdx.graphics.getDeltaTime()
        );

        this.checkCollisions();
    }
    public int getRewardedScore() {
        return this.rewardedScore;
    }
}
