package game.launcher.gamelogic;

import game.launcher.enums.Direction;
import game.launcher.entities.Snake;
import javafx.scene.Scene;

public class InputHandler {
    private final Scene snakeScene;
    private final Snake snake;
    public boolean locked = false;

    public InputHandler(Scene snakeScene, Snake snake) {
        this.snakeScene = snakeScene;
        this.snake = snake;

        snakeScene.setOnKeyPressed(keyEvent -> {
            Direction direction = snake.getDirection();
            if (!locked) {
                switch (keyEvent.getCode()) {
                    case UP, W -> {
                        if (direction != Direction.DOWN) {
                            snake.setDirection(Direction.UP);
                            locked = true;
                        }
                    }
                    case RIGHT, D -> {
                        if (direction != Direction.LEFT) {
                            snake.setDirection(Direction.RIGHT);
                            locked = true;
                        }
                    }
                    case DOWN, S -> {
                        if (direction != Direction.UP) {
                            snake.setDirection(Direction.DOWN);
                            locked = true;
                        }
                    }
                    case LEFT, A -> {
                        if (direction != Direction.RIGHT) {
                            snake.setDirection(Direction.LEFT);
                            locked = true;
                        }
                    }
                }
            }
        });
    }


}
