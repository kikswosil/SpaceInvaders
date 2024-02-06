package com.platformer.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.Game;

public class GameStartState implements GameState{
    private final Game game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    public GameStartState(Game game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
    }
    @Override
    public void render() {
        this.batch.begin();
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.start();
        }

        this.font.getData().setScale(5.f);
        this.font.draw(
                this.batch,
                "SPACE INVADERS",
                (float) ((Gdx.graphics.getWidth() - 600) / 2),
                Gdx.graphics.getHeight() - 400
        );

        this.font.getData().setScale(3.f);
        this.font.draw(
                this.batch,
                "Press [SPACE] to play.",
                (float) ((Gdx.graphics.getWidth() - 400) / 2),
                Gdx.graphics.getHeight() - 600
        );
        this.batch.end();
    }
}
