package com.platformer.game.score_counter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.Game;
import com.platformer.game.utils.behavioural.Creatable;
import com.platformer.game.utils.behavioural.Drawable;
import com.platformer.game.utils.behavioural.Updatable;
import com.platformer.game.enemy.Enemy;

import java.util.List;
import java.util.function.Consumer;

public class ScoreCounter implements Drawable, Creatable, Updatable {
    private BitmapFont font;
    private final List<Enemy> enemyPool;
    private int score = 0;

    public ScoreCounter(List<Enemy> enemyPool) {
        this.enemyPool = enemyPool;
    }

    public void reset() {
        this.score = 0;
    }
    public int getScore() {
        return this.score;
    }

    public void addToScore(int value) {
        this.score += value;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(this.font == null) return;
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
