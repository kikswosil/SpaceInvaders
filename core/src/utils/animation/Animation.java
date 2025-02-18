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
    private int delay;
    private long lastFrame;
    private boolean isDone = false;
    private final int frameSizeX;
    private final int frameSizeY;
    private final Texture texture;

    public Animation(int initialX, int initialY, int posX, int posY, int frameSizeX, int frameSizeY, Texture texture, int delay) {
        this.currentX = posX;
        this.currentY = posY;
        this.frameX = initialX;
        this.frameY = initialY;
        this.frameSizeX = frameSizeX;
        this.frameSizeY = frameSizeY;
        this.texture = texture;
        this.delay = delay;
        this.lastFrame = System.currentTimeMillis();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
            new TextureRegion(this.texture, frameX, frameY, frameSizeX, frameSizeY),
            (float) this.currentX,
            (float) this.currentY,
            128,
            128
        );
    }

    @Override
    public void update() {
        if(this.frameX + this.frameSizeX == this.texture.getWidth()) this.isDone = true;
        else if(System.currentTimeMillis() - lastFrame >= this.delay) {
            this.frameX += this.frameSizeX;
            this.lastFrame = System.currentTimeMillis();
        }
        else return;
    }

    public boolean isDone() {
        return this.isDone;
    }

}
