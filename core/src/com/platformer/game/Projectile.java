package com.platformer.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import static com.platformer.game.Const.Projectile.*;

public class Projectile extends Sprite implements Drawable, Updatable, Collideable {
    private boolean shouldRemove = false;
    private final long createdTimestamp = TimeUtils.millis();
    public Projectile(Texture texture, int x, int y, int width, int height) {
        this.setTexture(texture);

        this.setX(x);
        this.setY(y);
        this.setSize(width, height);
    }

    @Override
    public boolean shouldRemove() {
        return this.shouldRemove;
    }
    @Override
    public void draw(SpriteBatch batch) {
        if (this.getTexture() == null) return;
        batch.draw(
                this,
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight()
        );
    }

    @Override
    public void update() {
        this.setPosition(
                this.getX(),
                this.getY() + 5
        );
        // limits lifetime length of a projectile to LIFETIME_LENGTH.
        if(TimeUtils.timeSinceMillis(this.createdTimestamp) > PROJECTILE_LIFETIME_LENGTH) this.shouldRemove = true;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight()
        );
    }
}
