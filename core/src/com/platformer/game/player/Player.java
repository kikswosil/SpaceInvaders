package com.platformer.game.player;

import static com.platformer.game.Const.Player.PLAYER_HEIGHT;
import static com.platformer.game.Const.Player.PLAYER_TEXTURE_PATH;
import static com.platformer.game.Const.Player.PLAYER_VELOCITY_SCALAR;
import static com.platformer.game.Const.Player.PLAYER_WIDTH;
import static com.platformer.game.Const.Projectile.PROJECTILE_COOLDOWN;
import static com.platformer.game.Const.Projectile.PROJECTILE_LENGTH;
import static com.platformer.game.Const.Projectile.PROJECTILE_TEXTURE_SIZE;
import static com.platformer.game.Const.Projectile.PROJECTILE_WIDTH;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.platformer.game.enemy.Enemy;
import com.platformer.game.projectile.Projectile;
import com.platformer.game.utils.behavioural.Creatable;
import com.platformer.game.utils.behavioural.Drawable;
import com.platformer.game.utils.behavioural.Updatable;
import com.platformer.game.utils.collision.Collideable;
import com.platformer.game.utils.collision.CollisionUtil;

public class Player extends Sprite implements Creatable, Updatable, Drawable, Collideable {

    private final Vector2 velocity = new Vector2(0, 0);
    private final DesktopInputController controller = new PlayerController(this);
    private long lastProjectileFired;

    private Sound deathSound;

    private final List<Enemy> enemyList;
    private final List<Projectile> projectilePool;
    private boolean isDead = false;

    public Player(List<Enemy> enemyPool, List<Projectile> projectilePool) {
        this.enemyList = enemyPool;
        this.projectilePool = projectilePool;

        this.setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void setVelocityX(float x) {
        this.velocity.x = x;
    }

    public void setVelocityY(float y) {
        this.velocity.y = y;
    }

    @Override
    public void create() {
        Texture texture = new Texture(PLAYER_TEXTURE_PATH);
        this.setTexture(texture);
        this.setRegion(0, 0, texture.getWidth(), texture.getHeight());

        this.setPosition(((float) Gdx.graphics.getWidth() / 2) - (this.getWidth() / 2), 30);

        this.deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death1.mp3"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                this,
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight());
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
                this.getY() + this.velocity.y * Gdx.graphics.getDeltaTime());

        this.enemyList.forEach(enemy -> {
            if (CollisionUtil.isColliding(this, enemy)) {
                deathSound.play();
                setDead(true);
            }
        });
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight());
    }

    public void reset() {
        this.setDead(false);
        this.setPosition(((float) Gdx.graphics.getWidth() / 2) - (this.getWidth() / 2), 0);
    }

    public void shoot() {
        if(System.currentTimeMillis() - this.lastProjectileFired < PROJECTILE_COOLDOWN) return;
        
        Pixmap image = new Pixmap(
                PROJECTILE_TEXTURE_SIZE,
                PROJECTILE_TEXTURE_SIZE,
                Pixmap.Format.RGBA8888);
        image.setColor(Color.WHITE);
        image.fillRectangle(
                0,
                0,
                PROJECTILE_TEXTURE_SIZE,
                PROJECTILE_TEXTURE_SIZE);
        Texture texture = new Texture(image);
        image.dispose();
        this.projectilePool.add(
                new Projectile(
                        texture,
                        (int) (this.getX() + (this.getWidth() / 2)) - (PROJECTILE_WIDTH / 2),
                        (int) (this.getY() + this.getHeight()),
                        PROJECTILE_WIDTH,
                        PROJECTILE_LENGTH));
        this.lastProjectileFired = System.currentTimeMillis();
    }
}