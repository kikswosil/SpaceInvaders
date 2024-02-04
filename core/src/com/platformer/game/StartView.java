package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartView implements Drawable{
    private final Game game;
    private final BitmapFont font;
    public StartView(Game game, BitmapFont font) {
        this.game = game;
        this.font = font;
    }
    @Override
    public void draw(SpriteBatch batch) {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.start();
        }

        this.font.getData().setScale(5.f);
        this.font.draw(
            batch,
            "SPACE INVADERS",
                (float) ((Gdx.graphics.getWidth() - 600) / 2),
                Gdx.graphics.getHeight() - 400
        );

        this.font.getData().setScale(3.f);
        this.font.draw(
                batch,
                "Press [SPACE] to play.",
                (float) ((Gdx.graphics.getWidth() - 400) / 2),
                Gdx.graphics.getHeight() - 600
        );
    }
}
