package com.platformer.game.player;

import static com.badlogic.gdx.Gdx.input;

import com.badlogic.gdx.Input.Keys;

public class PlayerController implements DesktopInputController {
    private final Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    @Override
    public void handleKeyDown() {
        if(input.isKeyPressed(Keys.W) || input.isKeyPressed(Keys.UP)) Direction.UP.attemptMove(this.player);
        if(input.isKeyPressed(Keys.S) || input.isKeyPressed(Keys.DOWN)) Direction.DOWN.attemptMove(this.player);
        if(input.isKeyPressed(Keys.D) || input.isKeyPressed(Keys.RIGHT)) Direction.RIGHT.attemptMove(this.player);
        if(input.isKeyPressed(Keys.A) || input.isKeyPressed(Keys.LEFT)) Direction.LEFT.attemptMove(this.player);
        if(input.isKeyPressed(Keys.SPACE)) this.player.shoot();
    }


    @Override
    public void handleKeyUp() {
        if(!input.isKeyPressed(Keys.W) && !input.isKeyPressed(Keys.S) && !input.isKeyPressed(Keys.UP) && !input.isKeyPressed(Keys.DOWN)) this.player.setVelocityY(0.f);
        if(!input.isKeyPressed(Keys.A) && !input.isKeyPressed(Keys.D) && !input.isKeyPressed(Keys.LEFT) && !input.isKeyPressed(Keys.RIGHT)) this.player.setVelocityX(0.f);
    }

    @Override
    public void handleMouseButtonDown() {

    }

    @Override
    public void handleMouseButtonUp() {

    }

}
