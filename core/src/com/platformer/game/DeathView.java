package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathView implements Drawable {
    private final BitmapFont font;
    private final int score;
    public DeathView(BitmapFont font, int score) {
        this.font = font;
        this.score = score;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        this.font.getData().setScale(5f);
        this.font.draw(batch, "You died.", (float) ((Gdx.graphics.getWidth() - 250) / 2), Gdx.graphics.getHeight() - 400);
        this.font.getData().setScale(3f);
        this.font.draw(batch, String.format("score: %d", score), (float) ((Gdx.graphics.getWidth() - 200) / 2), Gdx.graphics.getHeight() - 500);
        batch.end();
    }
}
