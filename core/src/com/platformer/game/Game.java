package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.platformer.game.music.Playlist;
import com.platformer.game.score_counter.ScoreCounter;
import com.platformer.game.enemy.Enemy;
import com.platformer.game.enemy.EnemyGenerator;
import com.platformer.game.player.Player;
import com.platformer.game.projectile.Projectile;
import com.platformer.game.projectile.ProjectileGenerator;
import com.platformer.game.state.GameOverState;
import com.platformer.game.state.GameRunningState;
import com.platformer.game.state.GameStartState;
import com.platformer.game.state.GameState;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
    private BitmapFont font;
	private Timer timer;
	private boolean isStarted = false;
	private GameState currentState;

    private final List<Projectile> projectilePool = new ArrayList<>();
	private final List<Enemy> enemyPool = new ArrayList<>();
	private final Player player = new Player(this);
    private final ScoreCounter scoreCounter = new ScoreCounter(this);
    private final ProjectileGenerator projectileGenerator = new ProjectileGenerator(this);
	private final EnemyGenerator enemyGenerator = new EnemyGenerator(this);

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
	public Timer getTimer() {
		return this.timer;
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

		this.timer = new Timer();

        this.font = new BitmapFont();
        this.font.getData().setScale(3f);

		this.scoreCounter.create();

        // create game objects.
		this.player.create();
        this.projectileGenerator.create();
		this.enemyGenerator.create();

		// create music
		new Playlist().create();
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
