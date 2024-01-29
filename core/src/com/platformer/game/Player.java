package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.function.Consumer;

import static com.platformer.game.Const.Player.PLAYER_VELOCITY_SCALAR;

public class Player extends Sprite implements Creatable, Updatable, Drawable, Collideable{

    private final String texturePath;
    private final Vector2 velocity = new Vector2(0, 0);
    private final DesktopInputController controller = new PlayerController(this);

    private final List<Enemy> enemyList;
    private boolean isDead = false;

    public Player(String texturePath, List<Enemy> enemyList, Rectangle initialHitBox) {
        this.texturePath = texturePath;
        this.enemyList = enemyList;

        this.setSize(initialHitBox.width, initialHitBox.height);
    }

    public Player(String texturePath, List<Enemy> enemyList, int width, int height) {
        this.texturePath = texturePath;
        this.enemyList = enemyList;

        this.setSize(width, height);
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

    // REFACTOR: MOVE THIS TO A CollisionManager CLASS.
    private boolean isColliding(Collideable collideable) {
        Rectangle projectileHitBox = collideable.getHitBox();
        return projectileHitBox.x + projectileHitBox.width > getX()  &&
                projectileHitBox.x < getX() + getWidth()              &&
                projectileHitBox.y + projectileHitBox.height > getY() &&
                projectileHitBox.y < getY() + getHeight();
    }

    private void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean isDead() {
        return this.isDead;
    }

    @Override
    public void update() {

        this.controller.process();

        this.velocity.nor().scl(PLAYER_VELOCITY_SCALAR);
        this.setPosition(
                this.getX() + this.velocity.x * Gdx.graphics.getDeltaTime(),
                this.getY() + this.velocity.y * Gdx.graphics.getDeltaTime()
        );

        this.enemyList.forEach(new Consumer<Enemy>() {
            @Override
            public void accept(Enemy enemy) {
                if(isColliding(enemy)) {
                    setDead(true);
                }
            }
        });
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