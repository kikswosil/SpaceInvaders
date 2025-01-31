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
        if(input.isKeyPressed(Keys.W)) Direction.UP.attemptMove(this.player);
        if(input.isKeyPressed(Keys.S)) Direction.DOWN.attemptMove(this.player);
        if(input.isKeyPressed(Keys.D)) Direction.RIGHT.attemptMove(this.player);
        if(input.isKeyPressed(Keys.A)) Direction.LEFT.attemptMove(this.player);
        if(input.isKeyPressed(Keys.SPACE)) this.player.shoot();
    }


    @Override
    public void handleKeyUp() {
        if(!input.isKeyPressed(Keys.W) && !input.isKeyPressed(Keys.S)) this.player.setVelocityY(0.f);
        if(!input.isKeyPressed(Keys.A) && !input.isKeyPressed(Keys.D)) this.player.setVelocityX(0.f);
    }

    @Override
    public void handleMouseButtonDown() {

    }

    @Override
    public void handleMouseButtonUp() {

    }

}
