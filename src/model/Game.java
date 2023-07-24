package model;

import controller.Controller;
import controller.Controller.ControlType;
import resources.Settings;
import view.Display;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game {
    public static boolean isRunning = false;
    public static int currentScore = 0;

    static List<Physical> toRemoveQueue = new LinkedList<>();
    static List<Physical> toAddQueue = new LinkedList<>();
    static List<Physical> objects = new LinkedList<>();
    static List<Snake> players = new LinkedList<>();
    static List<Block> walls = new LinkedList<>();

    public static Controller ctrl = new Controller();

    public static void loop() {
        if (isRunning) {
            handleController();
            handleObjects();
        } else {
            /* Game Over */
            System.out.println("Game Over");
        }

        loopObjects();
    }

    public static Controller getController() {
        return ctrl;
    }

    public static void handleController() {
        for (Snake snake : players) {
            if (snake.control == ControlType.ARROWS) {
                if (ctrl.getStatePressed(Controller.KeyMap.LEFT.v1)) {
                    snake.move(Settings.Direction.LEFT);
                }
                if (ctrl.getStatePressed(Controller.KeyMap.UP.v1)) {
                    snake.move(Settings.Direction.UP);
                }
                if (ctrl.getStatePressed(Controller.KeyMap.RIGHT.v1)) {
                    snake.move(Settings.Direction.RIGHT);
                }
                if (ctrl.getStatePressed(Controller.KeyMap.DOWN.v1)) {
                    snake.move(Settings.Direction.DOWN);
                }
            }

            if (snake.control == ControlType.WASD) {
                if (ctrl.getStatePressed(Controller.KeyMap.LEFT.v2)) {
                    snake.move(Settings.Direction.LEFT);
                }
                if (ctrl.getStatePressed(Controller.KeyMap.UP.v2)) {
                    snake.move(Settings.Direction.UP);
                }
                if (ctrl.getStatePressed(Controller.KeyMap.RIGHT.v2)) {
                    snake.move(Settings.Direction.RIGHT);
                }
                if (ctrl.getStatePressed(Controller.KeyMap.DOWN.v2)) {
                    snake.move(Settings.Direction.DOWN);
                }
            }
        }
    }

    public static void handleObjects() {
        for (Physical obj : toRemoveQueue) {
            objects.remove(obj);
        }
        toRemoveQueue.clear();
        objects.addAll(toAddQueue);
        toAddQueue.clear();
    }

    public static void loopObjects() {
        for (Physical obj : objects) {
            obj.checkCollision(objects);
            obj.loop();
        }
    }

    public static void removeObject(Physical physical) {
        toRemoveQueue.add(physical);
        Display.remove(physical);
    }

    public static void addObject(Physical physical) {
        toAddQueue.add(physical);
        Display.add(physical);
    }

    public static void initScene() {
        /* clear everything */
        isRunning = true;
        currentScore = 0;

        objects.clear();
        Display.clear();

        /* scene */
//        generateWalls();
        generateFruits(1);
        generateSnakes(1);
    }

    public static void generateSnakes(int numberOfPlayers) {
        if (numberOfPlayers <= 0) return;

        Snake snake = new Snake(
            1, 1,
            Settings.Direction.RIGHT,
            Settings.SnakeColors.HEAD_PURPLE.value,
            Settings.SnakeColors.TAIL_PURPLE.value,
            ControlType.ARROWS
        );
        addObject(snake);
        players.add(snake);

        if (numberOfPlayers <= 1) return;

        Snake snake2 = new Snake(
            Settings.GRID_WIDTH - 2, Settings.GRID_HEIGHT - 2,
            Settings.Direction.LEFT,
            Settings.SnakeColors.TAIL_GREEN.value,
            Settings.SnakeColors.HEAD_GREEN.value,
            ControlType.WASD
        );
        addObject(snake2);
        players.add(snake2);

        if (numberOfPlayers <= 2) return;

        /* todo - 3 players */
        System.out.println("todo");
    }

    public static void generateFruits(int numberOfFruits) {
        for (int i = 0; i < numberOfFruits; i++) {
            int gridX = (int) (Settings.GRID_WIDTH * Math.random());
            int gridY = (int) (Settings.GRID_HEIGHT * Math.random());
            Fruit.Type type = Math.random() >= 0.1 ? Fruit.Type.NORMAL : Fruit.Type.GOLDEN;
            Fruit fruit = new Fruit(gridX, gridY, type);
            addObject(fruit);
        }
    }

    public static void generateWalls() {
        for (int i = 0; i < Settings.GRID_WIDTH; i++) {
            Block block = new Block(i, 0);
            walls.add(block);
            addObject(block);
            Block block2 = new Block(i, Settings.GRID_HEIGHT - 1);
            walls.add(block2);
            addObject(block2);
        }
        for (int i = 0; i < Settings.GRID_HEIGHT; i++) {
            Block block = new Block(0, i);
            walls.add(block);
            addObject(block);
            Block block2 = new Block(Settings.GRID_WIDTH - 1, i);
            walls.add(block2);
            addObject(block2);
        }
    }
}
