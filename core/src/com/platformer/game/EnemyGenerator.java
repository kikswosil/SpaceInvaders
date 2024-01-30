package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

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
    private final EnemyBuilder builder = new EnemyBuilder();

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

    public void doDifficultyProgression() {
        this.generatedEnemies++;
    }

    private Vector2 determinePosition() {
        return new Vector2(
                this.random
                    .ints(ENEMY_GENERATION_FIELD_MARGIN, Gdx.graphics.getWidth() - ENEMY_WIDTH - ENEMY_GENERATION_FIELD_MARGIN)
                    .findFirst()
                    .orElse(0),
                Gdx.graphics.getHeight()
        );
    }

    @Override
    public void update() {
        for (int i = 0; i <= generatedEnemies; i++) {
            int textureIndex = this.random.ints(0, this.textures.size()).findFirst().orElse(0);
            Texture texture = this.textures.get(textureIndex);
            this.builder
                    .setTexture(texture)
                    .setPosition((int) this.determinePosition().x, (int) this.determinePosition().y)
                    .setSize(ENEMY_WIDTH, texture.getHeight() * 2)
                    .setRewardedScore(10 * (textureIndex == 0 ? 1 : textureIndex))
                    .setCollideablePool(this.projectilePool)
                    .setEnemyPool(this.enemyPool)
            .getEnemy();
        }
    }
}
