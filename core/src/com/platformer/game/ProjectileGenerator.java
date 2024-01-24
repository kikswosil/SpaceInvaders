package com.platformer.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class ProjectileGenerator implements Creatable, Updatable {
    public static final int PROJECTILE_TEXTURE_WIDTH = 64;
    public static final int PROJECTILE_TEXTURE_HEIGHT = 64;
    public static final int PROJECTILE_WIDTH = 3;
    private static final int PROJECTILE_LENGTH = 15;
    private Texture texture;
    private final Player player;
    private final List<Projectile> projectilePool;

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
                PROJECTILE_TEXTURE_WIDTH,
                PROJECTILE_TEXTURE_HEIGHT,
                Pixmap.Format.RGBA8888
        );
        image.setColor(Color.WHITE);
        image.fillRectangle(
                0,
                0,
                PROJECTILE_TEXTURE_WIDTH,
                PROJECTILE_TEXTURE_HEIGHT
        );
        this.texture = new Texture(image);
        image.dispose();
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
