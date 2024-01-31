package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.function.Consumer;

public class ScoreCounter implements Drawable, Creatable, Updatable {
    private BitmapFont font;
    private List<Enemy> enemyPool;
    private int score = 0;

    public ScoreCounter(List<Enemy> enemyPool) {
        this.enemyPool = enemyPool;
    }
    public int getScore() {
        return this.score;
    }

    public void addToScore(int value) {
        this.score += value;
    }

    @Override
    public void draw(SpriteBatch batch) {
        this.font.draw(batch, String.format("score: %d", score), 10, Gdx.graphics.getHeight() - 10);
    }

    @Override
    public void create() {
        this.font = new BitmapFont();
        this.font.getData().setScale(3.f);
    }

    @Override
    public void update() {
        this.enemyPool.forEach(new Consumer<Enemy>() {
            @Override
            public void accept(Enemy enemy) {
                if (enemy.shouldRemove() && enemy.shouldRewardScore()) {
                    addToScore(enemy.getRewardedScore());
                }
            }
        });
    }
}
