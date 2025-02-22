package com.platformer.game.player;

import static com.platformer.game.Const.Player.PLAYER_HORIZONTAL_VELOCITY;
import static com.platformer.game.Const.Player.PLAYER_VERTICAL_VELOCITY;

import com.badlogic.gdx.Gdx;

public enum Direction {

    UP(new MovementImplementation() {
        @Override
        public void onCanMove(Player player) {
            player.setVelocityY(PLAYER_VERTICAL_VELOCITY);
        }

        @Override
        public void orElse(Player player) {
            player.setVelocityY(0.f);
        }

        @Override
        public boolean canMove(Player player) {
            return player.getY() + player.getHeight() < Gdx.graphics.getHeight();
        }
    }),
    DOWN(new MovementImplementation() {
        @Override
        public boolean canMove(Player player) {
            return player.getY() > 0;
        }

        @Override
        public void onCanMove(Player player) {
            player.setVelocityY(-PLAYER_VERTICAL_VELOCITY);
        }

        @Override
        public void orElse(Player player) {
            player.setVelocityY(0.f);
        }
    }),
    LEFT(new MovementImplementation() {
        @Override
        public boolean canMove(Player player) {
            return player.getX() > 0;
        }

        @Override
        public void onCanMove(Player player) {
            player.setVelocityX(-PLAYER_HORIZONTAL_VELOCITY);
        }

        @Override
        public void orElse(Player player) {
            player.setVelocityX(0.f);
        }
    }),
    RIGHT(new MovementImplementation() {
        @Override
        public boolean canMove(Player player) {
            return player.getX() + player.getWidth() < Gdx.graphics.getWidth();
        }

        @Override
        public void onCanMove(Player player) {
            player.setVelocityX(PLAYER_HORIZONTAL_VELOCITY);
        }

        @Override
        public void orElse(Player player) {
            player.setVelocityX(0.f);
        }
    });

    private final MovementImplementation movementImplementation;
    private Direction(MovementImplementation movementImplementation) {
        this.movementImplementation = movementImplementation;
    }

    private abstract static class MovementImplementation {
        public abstract boolean canMove(Player player);
        public abstract void onCanMove(Player player);
        public abstract void orElse(Player player);
    }

    public void attemptMove(Player player) {
        if(this.movementImplementation.canMove(player)) {
            this.movementImplementation.onCanMove(player);
            return;
        }
        this.movementImplementation.orElse(player);
    }

}
