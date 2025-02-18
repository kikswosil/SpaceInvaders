package com.platformer.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.score_counter.ScoreCounter;

import utils.animation.Animation;

import com.platformer.game.enemy.Enemy;
import com.platformer.game.player.Player;
import com.platformer.game.projectile.Projectile;

import java.util.List;
import java.util.function.Consumer;

public class GameRunningState implements GameState{
    private final Player player;
    private final ScoreCounter scoreCounter;
    private final List<Enemy> enemyPool;
    private final List<Projectile> projectilePool;
    private final List<Animation> explosionPool;
    private final SpriteBatch batch;
    private final Texture background;

    public GameRunningState(Player player, ScoreCounter scoreCounter, List<Enemy> enemyPool, List<Projectile> projectilePool, List<Animation> explosionPool, SpriteBatch spriteBatch, final Texture background) {
        this.player = player;
        this.scoreCounter = scoreCounter;
        this.enemyPool = enemyPool;
        this.projectilePool = projectilePool;
        this.explosionPool = explosionPool;
        this.batch = spriteBatch;
        this.background = background;
    }
    @Override
    public void render() {
        this.batch.begin();
        this.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch.end();
        // update player
        this.player.update();

        // update enemies and projectiles.
        this.projectilePool.forEach(Projectile::update);
        this.enemyPool.forEach(Enemy::update);
        this.explosionPool.forEach(Animation::update);

        this.batch.begin();
        // render player
        this.player.draw(this.batch);

        // render projectiles.
        this.projectilePool.forEach(new Consumer<Projectile>() {
            @Override
            public void accept(Projectile projectile) {
                projectile.draw(batch);
            }
        });

        // render enemies.
        this.enemyPool.forEach(new Consumer<Enemy>() {
            @Override
            public void accept(Enemy enemy) {
                enemy.draw(batch);
            }
        });

        this.explosionPool.forEach(new Consumer<Animation>() {
            @Override
            public void accept(Animation animation) {
                animation.draw(batch);
            }
        });

        //draw the score counter.
        this.scoreCounter.draw(this.batch);

        this.batch.end();

        // count score.
        this.scoreCounter.update();

        // remove items from update lists.
        this.projectilePool.removeIf(Projectile::shouldRemove);
        this.enemyPool.removeIf(Enemy::shouldRemove);
        this.explosionPool.removeIf(Animation::isDone);
    }
}
