package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.platformer.game.Const.Enemy.*;

public class EnemyGenerator implements Creatable, Updatable {

    private int generatedEnemies = 0;
    private final List<Enemy> enemyPool;
    private final List<Projectile> projectilePool;
    private final Random random = new Random();

    private List<Texture> textures = new ArrayList<>();

    private final String[] paths;

    public EnemyGenerator(List<Enemy> enemyPool, List<Projectile> projectilePool, String[] paths) {
        this.enemyPool = enemyPool;
        this.projectilePool = projectilePool;

        this.paths = paths;
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
    }

    public void incrementGeneratedEnemies() {
        this.generatedEnemies++;
    }

    @Override
    public void update() {
        for (int i = 0; i <= generatedEnemies; i++) {
            int textureIndex = this.random.ints(0, this.textures.size()).findFirst().orElse(0);
            Texture texture = this.textures.get(textureIndex);
            // REFACTOR: REPLACE THIS WITH A BUILDER PATTERN.
            Enemy enemy = new Enemy(
                    texture,
                    this.random.ints(
                            ENEMY_GENERATION_FIELD_MARGIN,
                            Gdx.graphics.getWidth() - ENEMY_WIDTH - ENEMY_GENERATION_FIELD_MARGIN
                    ).findFirst().orElse(0),
                    Gdx.graphics.getHeight(),
                    ENEMY_WIDTH,
                    texture.getHeight() * 2
            );
            enemy.setCollideablePool(this.projectilePool);
            enemy.setRewardedScore(10 * (textureIndex == 0 ? 1 : textureIndex));
            this.enemyPool.add(enemy);
        }
    }
}
