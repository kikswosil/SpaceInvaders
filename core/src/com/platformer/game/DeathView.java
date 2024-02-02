package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathView implements Drawable {
    private final BitmapFont font;
    private final int score;

    private final Game game;
    public DeathView(Game game, BitmapFont font, int score) {
        this.font = font;
        this.score = score;
        this.game = game;
    }

    @Override
    public void draw(SpriteBatch batch) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            System.out.println("space pressed.");
            game.restart();
        }

        this.font.getData().setScale(5f);
        this.font.draw(
                batch,
                "GAME OVER",
                (float) ((Gdx.graphics.getWidth() - 400) / 2),
                Gdx.graphics.getHeight() - 400
        );
        this.font.getData().setScale(3f);
        this.font.draw(
                batch,
                String.format("score: %d", score),
                (float) ((Gdx.graphics.getWidth() - 200) / 2),
                Gdx.graphics.getHeight() - 500
        );
        this.font.draw(
                batch,
                "Press [SPACE] to play again",
                (float) ((Gdx.graphics.getWidth() - 550) / 2),
                Gdx.graphics.getHeight() - 600
        );
    }
}
