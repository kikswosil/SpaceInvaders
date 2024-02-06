package com.platformer.game.utils;

import com.badlogic.gdx.utils.Timer;
import com.platformer.game.Game;

import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_DELAY;
import static com.platformer.game.Const.Difficulty.DIFFICULTY_PROGRESSION_INTERVAL;
import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_DELAY;
import static com.platformer.game.Const.Enemy.ENEMY_GENERATION_INTERVAL;
import static com.platformer.game.Const.Projectile.PROJECTILE_GENERATION_DELAY;
import static com.platformer.game.Const.Projectile.PROJECTILE_GENERATION_INTERVAL;

public class TaskUtil {
    public static void getScheduledTasks(Game game) {
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
                               @Override
                               public void run() {
                                   if(!game.getPlayer().isDead() && game.isStarted())
                                       game.getProjectileGenerator().update();
                               }
                           },
                PROJECTILE_GENERATION_DELAY,
                PROJECTILE_GENERATION_INTERVAL
        );

        timer.scheduleTask(new Timer.Task() {
                               @Override
                               public void run() {
                                   if(!game.getPlayer().isDead() && game.isStarted())
                                       game.getEnemyGenerator().update();
                               }
                           },
                ENEMY_GENERATION_DELAY,
                ENEMY_GENERATION_INTERVAL
        );

        timer.scheduleTask(new Timer.Task() {

                               @Override
                               public void run() {
                                   if(!game.getPlayer().isDead() && game.isStarted())
                                       game.getEnemyGenerator().doDifficultyProgression();
                               }
                           },
                DIFFICULTY_PROGRESSION_DELAY,
                DIFFICULTY_PROGRESSION_INTERVAL
        );
    }
}
