package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_DELAY;
import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_INTERVAL;
import static com.platformer.game.Const.Player.PLAYER_HEIGHT;
import static com.platformer.game.Const.Player.PLAYER_WIDTH;
import static com.platformer.game.Const.Projectile.PROJECTILE_GENERATION_DELAY;
import static com.platformer.game.Const.Projectile.PROJECTILE_GENERATION_INTERVAL;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
    private BitmapFont font;

    private final List<Projectile> projectilePool = new ArrayList<>();

	private final List<Enemy> enemyPool = new ArrayList<>();

	private final Player player = new Player(
			"player.png",
			enemyPool,
			PLAYER_WIDTH,
			PLAYER_HEIGHT
	);

    private final ScoreCounter scoreCounter = new ScoreCounter(this.enemyPool);

    private final ProjectileGenerator projectileGenerator = new ProjectileGenerator(
			this.player,
			this.projectilePool
	);

	private final EnemyGenerator enemyGenerator = new EnemyGenerator(
			this.enemyPool,
			this.projectilePool,
			new String[]{
					"enemy1.png",
					"enemy2.png",
					"enemy3.png",
					"enemy4.png",
					"enemy5.png",
					"enemy6.png",
					"enemy7.png",
					"enemy8.png",
					"enemy9.png",
					"enemy10.png",
					"enemy11.png",
					"enemy12.png",
					"enemy13.png"
			}
	);

	@Override
	public void create () {
        // create game utils.
		this.batch = new SpriteBatch();

        this.font = new BitmapFont();
        this.font.getData().setScale(3f);

		this.scoreCounter.create();

        // create game objects.
		this.player.create();
        this.projectileGenerator.create();
		this.enemyGenerator.create();

        // handle time events.
        // (ex. enemy generation, difficulty progression)

		Timer timer = new Timer();
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				projectileGenerator.update();
			}
		},
				PROJECTILE_GENERATION_DELAY,
				PROJECTILE_GENERATION_INTERVAL
		);

		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				enemyGenerator.update();
			}
		}, ENEMY_GENERATION_DELAY, ENEMY_GENERATION_INTERVAL);

		timer.scheduleTask(new Timer.Task() {

			@Override
			public void run() {
				enemyGenerator.doDifficultyProgression();
			}
		}, 30f, 30f);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

        // draw player death screen, if player is dead.
		if (this.player.isDead()) {
			new DeathView(this.font, this.scoreCounter.getScore()).draw(this.batch);
			return;
		}

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

		this.scoreCounter.draw(this.batch);

		this.batch.end();

        // count score.
		this.scoreCounter.update();

        // remove items from update lists.
		this.projectilePool.removeIf(Projectile::shouldRemove);
		this.enemyPool.removeIf(Enemy::shouldRemove);
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
	}
}
