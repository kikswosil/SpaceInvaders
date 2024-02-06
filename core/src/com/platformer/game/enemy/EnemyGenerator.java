package com.platformer.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.platformer.game.utils.behavioural.Creatable;
import com.platformer.game.utils.behavioural.Updatable;
import com.platformer.game.projectile.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnemyGenerator implements Creatable, Updatable {

    private int generatedEnemies = 0;
    private final List<Enemy> enemyPool;
    private final List<Projectile> projectilePool;

    private EnemyManager manager;
    private List<Texture> textures = new ArrayList<>();

    private final String[] paths;

    public EnemyGenerator(List<Enemy> enemyPool, List<Projectile> projectilePool, String[] paths) {
        this.enemyPool = enemyPool;
        this.projectilePool = projectilePool;

        this.paths = paths;
    }

    public void reset() {
        this.generatedEnemies = 0;
    }

    @Override
    public void create() {
        // create textures from provided paths.
        this.textures = Arrays.stream(paths).map(new Function<String, Texture>() {
            @Override
            public Texture apply(String s) {
                return new Texture(s);
            }
        }).collect(Collectors.toList());

        this.manager = new EnemyManager(this.enemyPool,
                this.projectilePool,
                this.textures);
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
