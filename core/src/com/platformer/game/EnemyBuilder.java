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

    public Enemy getEnemy() {
        return new Enemy(
                this.texture,
                this.x,
                this.y,
                this.width,
                this.height,
                this.collideablePool,
                this.enemyPool,
                this.score
        );
    }
}
