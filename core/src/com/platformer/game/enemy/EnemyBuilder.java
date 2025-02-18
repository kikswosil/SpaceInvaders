package com.platformer.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.platformer.game.projectile.Projectile;
import com.platformer.game.utils.animation.Animation;

import java.util.ArrayList;
import java.util.List;

public class EnemyBuilder {
    private Texture texture;
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private int score = 0;
    private List<Projectile> collideablePool = new ArrayList<>();
    private List<Enemy> enemyPool = new ArrayList<>();
    private List<Animation> explosionPool = new ArrayList<>();
    private Texture explosionTexture;
    private float  speedScalar = 0.f;

    public EnemyBuilder setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public EnemyBuilder setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public EnemyBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public EnemyBuilder setRewardedScore(int score) {
        this.score = score;
        return this;
    }

    public EnemyBuilder setCollideablePool(List<Projectile> collideablePool) {
        this.collideablePool = collideablePool;
        return this;
    }

    public EnemyBuilder setEnemyPool(List<Enemy> enemyPool) {
        this.enemyPool = enemyPool;
        return this;
    }

    public EnemyBuilder setExplosionPool(List<Animation> explosionPool) {
        this.explosionPool = explosionPool;
        return this;
    }

    public EnemyBuilder setSpeedScalar(float speed) {
        this.speedScalar = speed;
        return this;
    }


    public EnemyBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public EnemyBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public EnemyBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public EnemyBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public EnemyBuilder setExplosionTexture(Texture texture) {
        this.explosionTexture = texture;
        return this;
    }

    public Enemy createEnemy() {
        return new Enemy(
                this.texture,
                this.x,
                this.y,
                this.width,
                this.height,
                this.collideablePool,
                this.enemyPool,
                this.explosionPool,
                this.explosionTexture,
                this.score,
                this.speedScalar
        );
    }

}
