package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnemyGenerator implements Creatable, Updatable {

    public static final int ENEMY_WIDTH = 128;
    public static final int ENEMY_HEIGHT = 64;
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

    @Override
    public void update() {
        Texture texture = this.textures.get(this.random.ints(0, this.textures.size()).findFirst().orElse(0));
        Enemy enemy = new Enemy(
                texture,
                this.random.ints(0, Gdx.graphics.getWidth() - ENEMY_WIDTH).findFirst().orElse(0),
                Gdx.graphics.getHeight(),
                ENEMY_WIDTH,
                texture.getHeight() * 2
        );
        enemy.setCollideablePool(this.projectilePool);
        this.enemyPool.add(enemy);
    }
}
