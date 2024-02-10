package com.platformer.game.projectile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.platformer.game.Game;
import com.platformer.game.utils.behavioural.Creatable;
import com.platformer.game.utils.behavioural.Updatable;
import com.platformer.game.player.Player;

import java.util.List;

import static com.platformer.game.Const.Projectile.*;

public class ProjectileGenerator implements Creatable, Updatable {
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

        Timer timer = new Timer();

        timer.scheduleTask(new Timer.Task() {
                               @Override
                               public void run() {
                                   if (!player.isDead() && Game.isStarted)
                                       update();
                               }
                           },
                PROJECTILE_GENERATION_DELAY,
                PROJECTILE_GENERATION_INTERVAL
        );
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
