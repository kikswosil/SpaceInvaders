package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState implements GameState {
    private final Game game;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final ScoreCounter scoreCounter;
    public GameOverState(Game game) {
        this.game = game;
        this.font = game.getFont();
        this.batch = game.getBatch();
        this.scoreCounter = game.getScoreCounter();
    }
    @Override
    public void render() {
        // handle player death
        this.batch.begin();
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            System.out.println("space pressed.");
            game.restart();
        }

        this.font.getData().setScale(5f);
        this.font.draw(
                this.batch,
                "GAME OVER",
                (float) ((Gdx.graphics.getWidth() - 400) / 2),
                Gdx.graphics.getHeight() - 400
        );
        this.font.getData().setScale(3f);
        this.font.draw(
                this.batch,
                String.format("score: %d", this.scoreCounter.getScore()),
                (float) ((Gdx.graphics.getWidth() - 200) / 2),
                Gdx.graphics.getHeight() - 500
        );
        this.font.draw(
                this.batch,
                "Press [SPACE] to play again",
                (float) ((Gdx.graphics.getWidth() - 550) / 2),
                Gdx.graphics.getHeight() - 600
        );
        this.batch.end();
    }
}
