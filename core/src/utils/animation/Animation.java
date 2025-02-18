package utils.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformer.game.utils.behavioural.Drawable;
import com.platformer.game.utils.behavioural.Updatable;

public class Animation implements Drawable, Updatable {
    private int currentX;
    private int currentY;
    private int frameX;
    private int frameY;
    private boolean isDone = false;
    private final int frameSizeX;
    private final int frameSizeY;
    private final Texture texture;

    public Animation(int frameSizeX, int frameSizeY, Texture texture) {
        this.currentX = 0;
        this.currentY = 0;
        this.frameSizeX = frameSizeX;
        this.frameSizeY = frameSizeY;
        this.texture = texture;
    }

    public Animation(int initialX, int initialY, int frameSizeX, int frameSizeY, Texture texture) {
        this.currentX = initialX;
        this.currentY = initialY;
        this.frameSizeX = frameSizeX;
        this.frameSizeY = frameSizeY;
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
            new TextureRegion(this.texture, frameSizeX, frameSizeY, frameX, frameY),
            (float) this.currentX,
            (float) this.currentY,
            (float) this.frameSizeX,
            (float) this.frameSizeY
        );
    }

    @Override
    public void update() {
        if(this.frameX + this.frameSizeX == this.texture.getWidth()) this.isDone = true;
        else this.frameX += this.frameSizeX;
    }

    public boolean isDone() {
        return this.isDone;
    }

}
