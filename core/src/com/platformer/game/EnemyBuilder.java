package com.platformer.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class EnemyBuilder {
    private Texture texture;
    private int x;
    private int y;
    private int width;
    private int height;
    private int score;
    private List<Projectile> collideablePool;
    private List<Enemy> enemyPool;
    private float  speedScalar;

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

    public Enemy createEnemy() {
        return new Enemy(
                this.texture,
                this.x,
                this.y,
                this.width,
                this.height,
                this.collideablePool,
                this.enemyPool,
                this.score,
                this.speedScalar
        );
    }

}
