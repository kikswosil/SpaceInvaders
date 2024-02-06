package com.platformer.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.function.Consumer;

public class GameRunningState implements GameState{
    private final Player player;
    private final ScoreCounter scoreCounter;
    private final List<Enemy> enemyPool;
    private final List<Projectile> projectilePool;
    private final SpriteBatch batch;
    public GameRunningState(Game game) {
        this.player = game.getPlayer();
        this.scoreCounter = game.getScoreCounter();
        this.enemyPool = game.getEnemyPool();
        this.projectilePool = game.getProjectilePool();
        this.batch = game.getBatch();
    }
    @Override
    public void render() {
        // update player
        this.player.update();

        // update enemies and projectiles.
        this.projectilePool.forEach(Projectile::update);
        this.enemyPool.forEach(Enemy::update);

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

        //draw the score counter.
        this.scoreCounter.draw(this.batch);

        this.batch.end();

        // count score.
        this.scoreCounter.update();

        // remove items from update lists.
        this.projectilePool.removeIf(Projectile::shouldRemove);
        this.enemyPool.removeIf(Enemy::shouldRemove);
    }
}
