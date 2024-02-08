package com.platformer.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.platformer.game.Game;
import com.platformer.game.utils.behavioural.Creatable;
import com.platformer.game.utils.behavioural.Updatable;
import com.platformer.game.projectile.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_DELAY;
import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_INTERVAL;
import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_DELAY;
import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_INTERVAL;

public class EnemyGenerator implements Creatable, Updatable {
    private Game game;
    private int generatedEnemies = 0;
    private final List<Enemy> enemyPool;
    private final List<Projectile> projectilePool;

    private EnemyManager manager;

    private final String[] paths = new String[]{
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
    };

    public EnemyGenerator(Game game) {
        this.projectilePool = game.getProjectilePool();
        this.enemyPool = game.getEnemyPool();

        this.game = game;
    }

    public void reset() {
        this.generatedEnemies = 0;
    }

    @Override
    public void create() {
        // create textures from provided paths.
        List<Texture> textures = Arrays.stream(paths).map(new Function<String, Texture>() {
            @Override
            public Texture apply(String s) {
                return new Texture(s);
            }
        }).collect(Collectors.toList());

        this.manager = new EnemyManager(this.enemyPool,
                this.projectilePool,
                textures
        );

        game.getTimer().scheduleTask(new Timer.Task() {
                               @Override
                               public void run() {
                                   if(!game.getPlayer().isDead() && game.isStarted())
                                       game.getEnemyGenerator().update();
                               }
                           },
                ENEMY_GENERATION_DELAY,
                ENEMY_GENERATION_INTERVAL
        );

        game.getTimer().scheduleTask(new Timer.Task() {

                               @Override
                               public void run() {
                                   if(!game.getPlayer().isDead() && game.isStarted())
                                       game.getEnemyGenerator().doDifficultyProgression();
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
