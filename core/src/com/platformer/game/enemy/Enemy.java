package com.platformer.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.platformer.game.utils.collision.Collideable;
import com.platformer.game.utils.collision.CollisionUtil;
import com.platformer.game.utils.behavioural.Drawable;
import com.platformer.game.utils.behavioural.Updatable;
import com.platformer.game.projectile.Projectile;

import java.util.List;

import static com.platformer.game.Const.Enemy.ENEMY_LIFETIME_LENGTH;


public class Enemy extends Sprite implements Collideable, Drawable, Updatable {

    private final List<Projectile> projectiles;
    private final Vector2 velocity = new Vector2(0, -3.f);
    private final float speedScalar;
    private final Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Boss hit 1.mp3"));
    private boolean shouldRemove;

    private int rewardedScore = 0;

    private final long createdTimestamp = TimeUtils.millis();
    private boolean shouldRewardScore = false;

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
            if(CollisionUtil.isColliding(this, projectile)) die(projectile);
        });
    }

    private void die(Projectile projectile) {
        this.shouldRemove = true;
        this.shouldRewardScore = true;
        this.deathSound.play();
        projectile.setShouldRemove(true);
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
        if(TimeUtils.timeSinceMillis(this.createdTimestamp) > ENEMY_LIFETIME_LENGTH) this.shouldRemove = true;
    }
    public int getRewardedScore() {
        return this.rewardedScore;
    }

    public boolean shouldRewardScore() {
        return this.shouldRewardScore;
    }
}
