package com.platformer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Playlist implements Creatable {
    private final List<Music> playlist = new ArrayList<>();
    private int currentSongIndex = 0;
    @Override
    public void create() {
        this.playlist.add(Gdx.audio.newMusic(Gdx.files.internal("music/16_bit_space.ogg")));
        this.playlist.add(Gdx.audio.newMusic(Gdx.files.internal("music/retro_metal.ogg")));
        this.playlist.forEach(new Consumer<Music>() {
            @Override
            public void accept(Music music) {
                music.setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        if(currentSongIndex < playlist.size() - 1) {
                            currentSongIndex++;
                        }
                        else {
                            currentSongIndex = 0;
                        }
                        playlist.get(currentSongIndex).setVolume(0.5f);
                        playlist.get(currentSongIndex).play();
                    }
                });
            }
        });

        playlist.get(currentSongIndex).setVolume(0.5f);
        playlist.get(currentSongIndex).play();
    }
}
