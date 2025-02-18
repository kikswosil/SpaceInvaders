package com.platformer.game.enemy;

import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_DELAY;
import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_INTERVAL;
import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_DELAY;
import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_INTERVAL;
import static com.platformer.game.Const.Enemy.ENEMY_TEXTURE_PATHS;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.platformer.game.Game;
import com.platformer.game.player.Player;
import com.platformer.game.projectile.Projectile;
import com.platformer.game.utils.behavioural.Creatable;
import com.platformer.game.utils.behavioural.Updatable;

public class EnemyGenerator implements Creatable, Updatable {
    private int generatedEnemies = 0;
    private final List<Enemy> enemyPool;
    private final List<Projectile> projectilePool;
    private final Player player_ref;

    private EnemyManager manager;

    public EnemyGenerator(List<Enemy> enemyPool, List<Projectile> projectilePool, Player player) {
        this.projectilePool = projectilePool;
        this.enemyPool = enemyPool;
        this.player_ref = player;
    }

    public void reset() {
        this.generatedEnemies = 0;
    }

    @Override
    public void create() {
        // create textures from provided paths.
        List<Texture> textures = Arrays.stream(ENEMY_TEXTURE_PATHS).map(new Function<String, Texture>() {
            @Override
            public Texture apply(String s) {
                return new Texture(s);
            }
        }).collect(Collectors.toList());

        this.manager = new EnemyManager(this.enemyPool,
                this.projectilePool,
                textures
        );

        Timer timer = new Timer();

        timer.scheduleTask(new Timer.Task() {
                               @Override
                               public void run() {
                                   if(!player_ref.isDead() && Game.isStarted)
                                       update();
                               }
                           },
                ENEMY_GENERATION_DELAY,
                ENEMY_GENERATION_INTERVAL
        );

        timer.scheduleTask(new Timer.Task() {

                               @Override
                               public void run() {
                                   if(!player_ref.isDead() && Game.isStarted)
                                       doDifficultyProgression();
                               }
                           },
                DIFFICULTY_PROGRESSION_DELAY,
                DIFFICULTY_PROGRESSION_INTERVAL
        );
    }

    public void doDifficultyProgression() {
        this.generatedEnemies++;
    }

    @Override
    public void update() {
        for (int i = 0; i <= generatedEnemies; i++) {
            this.manager.getEnemyFromType(EnemyTypes.randomEnemyType());
        }
    }
}
