package com.loucaskreger.autmclient.module;

public enum HudPosition {
    TOP_LEFT,
    TOP_RIGHT;

    // Computes the x position for a given screen size and width of the text.
    public int getXPosition(int screenWidth, int textWidth) {
        return switch (this) {
            case TOP_RIGHT -> screenWidth - textWidth;
            default -> 0;
        };
    }
}
