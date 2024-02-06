package com.platformer.game;

public class Const {
    public static final class Player {

        public static final int PLAYER_WIDTH = 128;
        public static final int PLAYER_HEIGHT = 64;
        public static final float PLAYER_HORIZONTAL_VELOCITY = 3.f;
        public static final float PLAYER_VERTICAL_VELOCITY = 3.f;
        public static final float PLAYER_VELOCITY_SCALAR = 400.f;
    }

    public static class Enemy {

        public static final float ENEMY_GENERATION_INTERVAL = 1.f;
        public static final float ENEMY_GENERATION_DELAY = 0.f;
        public static final float ENEMY_VELOCITY_SCALAR_LOW = 250.f;
        public static final float ENEMY_VELOCITY_SCALAR_HIGH = 500.f;
        public static final long ENEMY_LIFETIME_LENGTH = 10_000L;
        public static final int ENEMY_WIDTH = 128;
        public static final int ENEMY_HEIGHT = 64;
        public static final int ENEMY_GENERATION_FIELD_MARGIN = 0;
    }

    public static class Difficulty {
        public static final float DIFFICULTY_PROGRESSION_DELAY = 0.f;
        public static final float DIFFICULTY_PROGRESSION_INTERVAL = 30.f;
    }

    public static class Projectile {

        public static final float PROJECTILE_GENERATION_INTERVAL = 0.3f;
        public static final float PROJECTILE_GENERATION_DELAY = 0.f;
        public static final int PROJECTILE_LIFETIME_LENGTH = 1000;
        public static final int PROJECTILE_TEXTURE_SIZE = 64;
        public static final int PROJECTILE_WIDTH = 3;
        public static final int PROJECTILE_LENGTH = 15;
    }
}