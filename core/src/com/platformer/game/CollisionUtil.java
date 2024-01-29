package com.platformer.game;

import com.badlogic.gdx.math.Rectangle;

public class CollisionUtil {
    public static boolean isColliding(Collideable parent, Collideable target) {
        Rectangle parentHitBox = parent.getHitBox();
        Rectangle targetHitBox = target.getHitBox();
        return targetHitBox.getX() + targetHitBox.getWidth() > parentHitBox.getX()  &&
               targetHitBox.getX() < parentHitBox.getX() + parentHitBox.getWidth()  &&
               targetHitBox.getY() + targetHitBox.getHeight() > parentHitBox.getY() &&
               targetHitBox.getY() < parentHitBox.getY() + parentHitBox.getHeight();
    }
}
