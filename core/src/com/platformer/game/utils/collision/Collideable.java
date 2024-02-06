package com.platformer.game.utils.collision;

import com.badlogic.gdx.math.Rectangle;

public interface Collideable {
    public Rectangle getHitBox();
    public default boolean shouldRemove() {
        return false;
    };
}
