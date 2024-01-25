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

    public static final int ENEMY_WIDTH = 150;
    private final List<Enemy> enemyPool;
    private final Random random = new Random();

    private List<Texture> textures = new ArrayList<>();

    private final String[] paths;

    public EnemyGenerator(List<Enemy> enemyPool, String[] paths) {
        this.enemyPool = enemyPool;

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
        this.enemyPool.add(new Enemy(
                this.textures.get(this.random.ints(0, this.textures.size()).findFirst().orElse(0)),
                this.random.ints(0, Gdx.graphics.getWidth() - ENEMY_WIDTH).findFirst().orElse(0),
                Gdx.graphics.getHeight(),
                ENEMY_WIDTH,
                150
        ));
    }
}
