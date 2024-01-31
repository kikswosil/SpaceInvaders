package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

import static com.platformer.game.Const.Projectile.*;

public class ProjectileGenerator implements Creatable, Updatable {
    private Texture texture;
    private final Player player;
    private final List<Projectile> projectilePool;
//    private Sound fireSound;

    public ProjectileGenerator(Player player, List<Projectile> projectilePool) {
        this.player = player;
        this.projectilePool = projectilePool;
    }

    private int calculateProjectileX() {
        return (int) (this.player.getX() + (this.player.getWidth() / 2)) - (PROJECTILE_WIDTH / 2);
    }

    private int calculateProjectileY() {
        return (int) (this.player.getY() + this.player.getHeight());
    }

    @Override
    public void create() {
        Pixmap image = new Pixmap(
                PROJECTILE_TEXTURE_SIZE,
                PROJECTILE_TEXTURE_SIZE,
                Pixmap.Format.RGBA8888
        );
        image.setColor(Color.WHITE);
        image.fillRectangle(
                0,
                0,
                PROJECTILE_TEXTURE_SIZE,
                PROJECTILE_TEXTURE_SIZE
        );
        this.texture = new Texture(image);
        image.dispose();

//        this.fireSound = Gdx.audio.newSound(Gdx.files.internal("Hit damage 1.mp3"));
    }

    @Override
    public void update() {
        this.projectilePool.add(
                new Projectile(
                        texture,
                        this.calculateProjectileX(),
                        this.calculateProjectileY(),
                        PROJECTILE_WIDTH,
                        PROJECTILE_LENGTH
                )
        );
    }
}
