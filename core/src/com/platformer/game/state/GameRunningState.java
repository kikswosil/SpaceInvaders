package com.platformer.game.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.score_counter.ScoreCounter;
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
    private final SpriteBatch batch;

    public GameRunningState(Player player, ScoreCounter scoreCounter, List<Enemy> enemyPool, List<Projectile> projectilePool, SpriteBatch spriteBatch) {
        this.player = player;
        this.scoreCounter = scoreCounter;
        this.enemyPool = enemyPool;
        this.projectilePool = projectilePool;
        this.batch = spriteBatch;
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
