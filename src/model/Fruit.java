package model;

import resources.Settings;

import java.util.List;

public class Fruit extends Physical {
    public static final int SIZE = (35 * Settings.GRID_UNIT_SIZE /30);

    public enum Type {
        NORMAL(1), GOLDEN(10);
        final int score;
        private Type(int score) { this.score = score; }
    }

    private final Type type;

    public Fruit(int gridX, int gridY, Type type) {
        super(gridX * Settings.GRID_UNIT_SIZE, gridY * Settings.GRID_UNIT_SIZE);
        this.type = type;
        this.width = SIZE;
        this.height = SIZE;
    }

    public Type getType() { return type; }

    public int getScore() { return getType().score; }

    @Override
    public void onCollision(Physical object) {
        if (object.getClass() == Snake.class) {
            this.removeFromGame();
            Game.generateFruits(1);
        }
    }

    @Override
    public void checkCollision(List<Physical> objects) {}
}
