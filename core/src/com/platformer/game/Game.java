package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import static com.platformer.game.Const.Player.PLAYER_HEIGHT;
import static com.platformer.game.Const.Player.PLAYER_WIDTH;

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
					"fly.png",
					"dragonfly.png",
					"mantis.png",
					"cicada.png",
					"mosquito.png",
					"bee.png",
					"hornet.png",
					"bumblebee.png",
					"wasp.png",
					"ant.png",
					"moth.png",
					"earwig.png",
					"termite.png"
			}
	);
	private boolean isStarted = false;
	private GameState currentState;

	public Player getPlayer() {
		return this.player;
	}

	public BitmapFont getFont() {
		return this.font;
	}

	public List<Enemy> getEnemyPool() {
		return this.enemyPool;
	}

	public List<Projectile> getProjectilePool() {
		return this.projectilePool;
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}

	public ScoreCounter getScoreCounter() {
		return this.scoreCounter;
	}

	public boolean isStarted() {
		return this.isStarted;
	}

	public ProjectileGenerator getProjectileGenerator() {
		return this.projectileGenerator;
	}

	public EnemyGenerator getEnemyGenerator() {
		return this.enemyGenerator;
	}

	public void start() {
		this.isStarted = true;
		this.restart();
	}

	public void restart() {
		this.projectilePool.clear();
		this.enemyPool.clear();
		scoreCounter.reset();
		enemyGenerator.reset();
		player.reset();
	}

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

		// create music
		new Playlist().create();

        // handle time events.
        // (ex. enemy generation, difficulty progression)
		TaskSchedule.getScheduledTasks(this);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		if(!this.isStarted) {
			this.currentState = new GameStartState(this);
		}

		if(this.player.isDead()) {
			this.currentState = new GameOverState(this);
		}

		if(!this.player.isDead() && this.isStarted) {
			this.currentState = new GameRunningState(this);
		}

		this.currentState.render();

	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
	}

}
