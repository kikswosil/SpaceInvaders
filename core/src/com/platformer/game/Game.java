package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_DELAY;
import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_INTERVAL;

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

	private final List<Music> playlist = new ArrayList<>();
	private int currentSongIndex = 0;
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
		this.playlist.add(Gdx.audio.newMusic(Gdx.files.internal("music/16_bit_space.ogg")));
		this.playlist.add(Gdx.audio.newMusic(Gdx.files.internal("music/retro_metal.ogg")));

		this.playlist.forEach(new Consumer<Music>() {
			@Override
			public void accept(Music music) {
				music.setOnCompletionListener(new Music.OnCompletionListener() {
					@Override
					public void onCompletion(Music music) {
						if(currentSongIndex < playlist.size() - 1) {
							currentSongIndex++;
						}
						else {
							currentSongIndex = 0;
						}
						playlist.get(currentSongIndex).setVolume(0.5f);
						playlist.get(currentSongIndex).play();
					}
				});
			}
		});

		this.playlist.get(currentSongIndex).setVolume(0.5f);
		this.playlist.get(currentSongIndex).play();

        // handle time events.
        // (ex. enemy generation, difficulty progression)

		Timer timer = new Timer();
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				if(!player.isDead() && isStarted)
					projectileGenerator.update();
			}
		},
				PROJECTILE_GENERATION_DELAY,
				PROJECTILE_GENERATION_INTERVAL
		);

		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				if(!player.isDead() && isStarted)
					enemyGenerator.update();
			}
		}, ENEMY_GENERATION_DELAY, ENEMY_GENERATION_INTERVAL);

		timer.scheduleTask(new Timer.Task() {

			@Override
			public void run() {
				if(!player.isDead() && isStarted)
					enemyGenerator.doDifficultyProgression();
			}
		}, DIFFICULTY_PROGRESSION_DELAY, DIFFICULTY_PROGRESSION_INTERVAL);
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
