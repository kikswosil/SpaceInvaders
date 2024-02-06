package com.platformer.game.player;

public interface DesktopInputController {
    public default void process() {
        this.handleKeyUp();
        this.handleKeyDown();
        this.handleMouseButtonUp();
        this.handleMouseButtonDown();
    }
    public void handleKeyDown();
    public void handleKeyUp();
    public void handleMouseButtonDown();
    public void handleMouseButtonUp();
}
