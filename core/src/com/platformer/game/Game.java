package com.platformer.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.platformer.game.enemy.Enemy;
import com.platformer.game.enemy.EnemyGenerator;
import com.platformer.game.music.Playlist;
import com.platformer.game.player.Player;
import com.platformer.game.projectile.Projectile;
import com.platformer.game.score_counter.ScoreCounter;
import com.platformer.game.state.GameOverState;
import com.platformer.game.state.GameRunningState;
import com.platformer.game.state.GameStartState;
import com.platformer.game.state.GameState;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
    private BitmapFont font;
	public static boolean isStarted = false;
	private GameState currentState;



    private final List<Projectile> projectilePool = new ArrayList<>();
	private final List<Enemy> enemyPool = new ArrayList<>();
	private final Player player = new Player(
        this.enemyPool, 
        this.projectilePool
    );
    private final ScoreCounter scoreCounter = new ScoreCounter(this.enemyPool);
    // private final ProjectileGenerator projectileGenerator = new ProjectileGenerator(
    //         this.player,
    //         this.projectilePool
    // );
	private final EnemyGenerator enemyGenerator = new EnemyGenerator(
            this.enemyPool,
            this.projectilePool,
            this.player
    );



    public void start() {
        Game.isStarted = true;
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
        // this.projectileGenerator.create();
        this.enemyGenerator.create();

        // create music
        new Playlist().create();
    }

    @Override
    public void render () {
        ScreenUtils.clear(0, 0, 0, 1);

        if(!Game.isStarted) {
            this.currentState = new GameStartState(
                    this.batch,
                    this.font,
                    this::start
            );
        }

        if(this.player.isDead()) {
            this.currentState = new GameOverState(
                    this.font,
                    this.batch,
                    this.scoreCounter,
                    this::restart
            );
        }

        if(!this.player.isDead() && Game.isStarted) {
            this.currentState = new GameRunningState(
                    this.player,
                    this.scoreCounter,
                    this.enemyPool,
                    this.projectilePool,
                    this.batch
            );
        }

        this.currentState.render();

    }



    @Override
    public void dispose () {
        this.batch.dispose();
    }
}
