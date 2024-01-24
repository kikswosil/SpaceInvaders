package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player extends Sprite implements Creatable, Updatable, Drawable, Collideable{

    public static final float SPEED_SCALAR = 400.f;
    private final String texturePath;
    private final Vector2 velocity;
    private List<Collideable> collideables = new ArrayList<>();
    private final DesktopInputController controller = new PlayerController(this);

    private final CollisionDetector collisionDetector = new CollisionDetector(this) {
        @Override
        public void onCollisionDetected(Collideable collideable) {
            //
        }
    };

    public Player(String texturePath, Rectangle initialHitBox) {
        this.texturePath = texturePath;

        this.setSize(initialHitBox.width, initialHitBox.height);

        this.velocity = new Vector2(0, 0);
    }

    public Player(String texturePath, int width, int height) {
        this.texturePath = texturePath;

        this.setSize(width, height);

        this.velocity = new Vector2(0, 0);
    }
    public void addToCollideables(Collideable collideable) {
        this.collideables.add(collideable);
    }

    public void setVelocityX(float x) {
        this.velocity.x = x;
    }

    public void setVelocityY(float y) {
        this.velocity.y = y;
    }
    @Override
    public void create() {
        Texture texture = new Texture(this.texturePath);
        this.setTexture(texture);
        this.setRegion(0, 0, texture.getWidth(), texture.getHeight());

        this.setPosition(((float) Gdx.graphics.getWidth() / 2) - (this.getWidth() / 2), 30);
    }

    @Override
    public void draw(SpriteBatch batch) {
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

        this.controller.process();

        this.velocity.nor().scl(SPEED_SCALAR);
        this.setPosition(
                this.getX() + this.velocity.x * Gdx.graphics.getDeltaTime(),
                this.getY() + this.velocity.y * Gdx.graphics.getDeltaTime()
        );

        this.collisionDetector.detectCollisions(this.collideables);

        this.collideables = this.collideables.stream()
                .filter(collideable -> !collideable.shouldRemove())
                .collect(Collectors.toList());
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getY()
        );
    }
}