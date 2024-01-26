package com.platformer.game;

public class ScoreCounter {
    private int score = 0;
    public int getScore() {
        return this.score;
    }

    public void addToScore(int value) {
        this.score += value;
    }
}
