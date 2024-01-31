package com.platformer.game;

import java.util.Random;

public enum EnemyTypes {
    FLY,
    DRAGONFLY,
    MANTIS,
    CICADA,
    MOSQUITO,
    BEE,
    HORNET,
    BUMBLEBEE,
    WASP,
    ANT,
    MOTH,
    CRICKET,
    EARWIG,
    TERMITE;

    private static final Random random = new Random();
    private static final EnemyTypes[] enemyTypes = EnemyTypes.values();
    public static EnemyTypes randomEnemyType() {
        return enemyTypes[random.nextInt(enemyTypes.length)];
    }
}
