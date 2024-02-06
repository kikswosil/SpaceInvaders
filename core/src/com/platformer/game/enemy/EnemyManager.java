package com.platformer.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.platformer.game.projectile.Projectile;

import java.util.*;
import java.util.stream.Collectors;

import static com.platformer.game.Const.Enemy.*;

public class EnemyManager {
    private final Map<EnemyTypes, EnemyBuilder> enemyMap = new HashMap<>();
    public EnemyManager(List<Enemy> enemyPool, List<Projectile> projectilePool, List<Texture> textures) {
        List<EnemyTypes> enemyTypes = Arrays.stream(EnemyTypes.values()).collect(Collectors.toList());
        for(int i = 0; i < enemyTypes.size(); i++) {
            this.enemyMap.put(enemyTypes.get(i),
                    new EnemyBuilder()
                            .setTexture(textures.get(i))
                            .setPosition((int) this.determinePosition().x, (int) this.determinePosition().y)
                            .setSize(ENEMY_WIDTH, textures.get(i).getHeight() * 2)
                            .setRewardedScore((int) (10 * (i == 0 ? 0.5f : i)))
                            .setCollideablePool(projectilePool)
                            .setEnemyPool(enemyPool)
                            .setSpeedScalar(250.f + 20 * (i == 0 ? 1 : i))
            );
        }
    }
    public Vector2 determinePosition() {
        return new Vector2(new Random()
                .nextInt(
                        ENEMY_GENERATION_FIELD_MARGIN,
                        Gdx.graphics.getWidth() - ENEMY_WIDTH - ENEMY_GENERATION_FIELD_MARGIN
                ), Gdx.graphics.getHeight()
        );
    }

    public Enemy getEnemyFromType(EnemyTypes enemyType) {
        return this.enemyMap.get(enemyType).createEnemy();
    }
}
