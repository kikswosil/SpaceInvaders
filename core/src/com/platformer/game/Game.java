package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Game extends ApplicationAdapter {
	public static final int PLAYER_WIDTH = 128;
	public static final int PLAYER_HEIGHT = 64;
	public static final float PROJECTILE_GENERATION_INTERVAL = 0.3f;
	public static final float PROJECTILE_GENERATION_DELAY = 0.f;
	private SpriteBatch batch;

    private final List<Projectile> projectilePool = new ArrayList<>();

	private final List<Enemy> enemyPool = new ArrayList<>();

	private final Player player = new Player(
			"player.png",
			PLAYER_WIDTH,
			PLAYER_HEIGHT
	);

    private final ProjectileGenerator projectileGenerator = new ProjectileGenerator(
			this.player,
			this.projectilePool
	);

	private final EnemyGenerator enemyGenerator = new EnemyGenerator(
			this.enemyPool,
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
		this.batch = new SpriteBatch();

		this.player.create();
        this.projectileGenerator.create();
		this.enemyGenerator.create();

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
		}, 0.f, 2.f);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		this.player.update();


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

		this.enemyPool.forEach(new Consumer<Enemy>() {
			@Override
			public void accept(Enemy enemy) {
				enemy.draw(batch);
			}
		});

		this.batch.end();

		this.projectilePool.removeIf(Projectile::shouldRemove);
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
	}
}