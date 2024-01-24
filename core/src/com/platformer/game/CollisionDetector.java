package com.platformer.game;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public abstract class CollisionDetector {

    private final Collideable parentCollideable;

    public CollisionDetector(Collideable parent) {
        this.parentCollideable = parent;
    }

    public void detectCollisions(List<Collideable> collideables) {
        collideables.forEach((Collideable collideable) -> {
            if(this.isCollidingWith(collideable)) {
                this.onCollisionDetected(collideable);
            }
        });
    }

    // could be refactored to return which side specifically it collides with.
    public boolean isCollidingWith(Collideable collideable) {
        final Rectangle parentHitBox = this.parentCollideable.getHitBox();
        final Rectangle collideableHitBox = collideable.getHitBox();

        return  parentHitBox.x < collideableHitBox.x + collideableHitBox.width  &&  // collision on the right   of the collideable
                parentHitBox.x + parentHitBox.width  > collideableHitBox.x      &&  // -------||------- left    --------||--------
                parentHitBox.y < collideableHitBox.y + collideableHitBox.height &&  // -------||------- bottom  --------||--------
                parentHitBox.y + parentHitBox.height > collideableHitBox.y;         // -------||------- top     --------||--------
    }

    public abstract void onCollisionDetected(Collideable collideable);

}
