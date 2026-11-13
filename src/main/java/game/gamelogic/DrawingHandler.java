package game.gamelogic;

import game.enums.Direction;
import game.entities.Apple;
import game.entities.Bot;
import game.entities.Snake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class DrawingHandler {
    private final int FIELD_SIZE_PX;
    private final int TILE_SIZE;
    private final int FIELD_SIZE_SQRS;
    private final boolean botFlag;
    private final Bot bot;
    private final GraphicsContext graphicsContext;
    private Snake snake;
    public ArrayList<Apple> apples = new ArrayList<>();

    public DrawingHandler(GraphicsContext graphicsContext, int fieldSize, boolean botFlag, Bot bot, Snake snake) {
        FIELD_SIZE_PX = fieldSize;
        TILE_SIZE = 25;
        FIELD_SIZE_SQRS = fieldSize / TILE_SIZE;
        this.graphicsContext = graphicsContext;
        this.botFlag = botFlag;
        this.bot = bot;
        this.snake = snake;

        apples.add(new Apple(FIELD_SIZE_SQRS));

        if (fieldSize >= 500) {
            apples.add(new Apple(FIELD_SIZE_SQRS));
            if (!botFlag) {
                apples.add(new Apple(FIELD_SIZE_SQRS));
            }
        }
    }

    public void drawMap() {
        for (int i = 0; i < FIELD_SIZE_SQRS; i++) {
            for (int j = 0; j < FIELD_SIZE_SQRS; j++) {
                if (j % 2 == 0) {
                    if (i % 2 == 0) {
                        graphicsContext.setFill(Color.rgb(110, 140, 60));
                    } else {
                        graphicsContext.setFill(Color.rgb(120, 150, 70));
                    }
                } else {
                    if (i % 2 == 1) {
                        graphicsContext.setFill(Color.rgb(110, 140, 60));
                    } else {
                        graphicsContext.setFill(Color.rgb(120, 150, 70));
                    }
                }
                graphicsContext.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        drawSnake(null, snake.snakeBody, snake.getDirection(), Direction.RIGHT, false);
        if (botFlag) {
            drawSnake(null, bot.botSnake, bot.getDirection(), Direction.RIGHT, true);
        }
    }

    public void drawSnake(Point end, ArrayList<Point> snakeBody, Direction direction, Direction lastDirection, boolean isBot) {
        Color colorSnake;
        if (isBot){
            colorSnake = Color.LIGHTPINK;
        }else colorSnake = Color.LIGHTBLUE;
        graphicsContext.setFill(colorSnake);
        if (end != null && end.equals(new Point(-1,-1))){
            overdrawEnd(snakeBody.getFirst());
            graphicsContext.setFill(colorSnake);
        }else if (end != null && !end.equals(new Point(-1 ,-1))){
            overdrawEnd(end);
            graphicsContext.setFill(colorSnake);
        }
        drawHead(snakeBody.getFirst(), direction);
        graphicsContext.setFill(colorSnake);
        if (end == null) {
            for (int i = 1; i < snakeBody.size(); i++) {
                Point point = snakeBody.get(i);
                int x, y;
                if (i == snakeBody.size() - 1){
                    x = point.x * TILE_SIZE + 2;
                }else {
                    x = point.x * TILE_SIZE;
                }
                y = point.y * TILE_SIZE + 2;
                graphicsContext.fillRect(x, y, TILE_SIZE, TILE_SIZE - 4);
            }
        } else {
            Point second = snakeBody.get(1);
            System.out.println("second: " + second);
            if (direction != lastDirection){
                System.out.println("different directions, last Direction: " + lastDirection);
                overdrawEnd(second);
                graphicsContext.setFill(colorSnake);
                int x = second.x * TILE_SIZE;
                int y = second.y * TILE_SIZE;

                switch (lastDirection){
                    case LEFT -> {
                        if(direction == Direction.UP){
                            graphicsContext.fillRect(x + 2, y, TILE_SIZE - 2, TILE_SIZE - 2);
                        }else {
                            graphicsContext.fillRect(x + 2, y + 2, TILE_SIZE - 2, TILE_SIZE - 2);
                        }
                    }
                    case RIGHT -> {
                        if (direction == Direction.UP){
                            graphicsContext.fillRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2);
                        }else {
                            graphicsContext.fillRect(x, y + 2, TILE_SIZE - 2, TILE_SIZE - 2);
                        }
                    }
                    case UP -> {
                        if (direction == Direction.RIGHT){
                            graphicsContext.fillRect(x + 2, y + 2, TILE_SIZE - 2, TILE_SIZE - 2);
                        }else {
                            graphicsContext.fillRect(x, y + 2, TILE_SIZE - 2, TILE_SIZE - 2);
                        }
                    }
                    case DOWN -> {
                        if (direction == Direction.RIGHT){
                            graphicsContext.fillRect(x + 2, y, TILE_SIZE - 2, TILE_SIZE - 2);
                        }else {
                            graphicsContext.fillRect(x, y, TILE_SIZE - 2, TILE_SIZE - 2);
                        }
                    }
                }
            }else {
                int x = second.x * TILE_SIZE + 3;
                int y = second.y * TILE_SIZE + 3;
                graphicsContext.fillRect(x, y, TILE_SIZE - 6, TILE_SIZE - 6);
            }
        }
    }

    private void overdrawEnd(Point end) {
        int x = end.x;
        int y = end.y;
        if (y % 2 == 0 && x % 2 == 1) {
            graphicsContext.setFill(Color.rgb(120, 150, 70));
        } else if (y % 2 == 1 && x % 2 == 0) {
            graphicsContext.setFill(Color.rgb(120, 150, 70));
        } else {
            graphicsContext.setFill(Color.rgb(110, 140, 60));
        }
        graphicsContext.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

    }

    public void drawHead(Point point, Direction direction) {
        int x = point.x * TILE_SIZE;
        int y = point.y * TILE_SIZE;

        switch (direction) {
            case UP -> {
                x += 2;

                graphicsContext.fillRect(x, y, TILE_SIZE - 4, TILE_SIZE);
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(x + 4, y + 8, 3, 3);
                graphicsContext.fillRect(x + 14, y + 8, 3, 3);
            }

            case DOWN -> {
                x += 2;

                graphicsContext.fillRect(x, y, TILE_SIZE - 4, TILE_SIZE);
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(x + 4, y + 17, 3, 3);
                graphicsContext.fillRect(x + 14, y + 17, 3, 3);
            }

            case LEFT -> {
                y += 2;

                graphicsContext.fillRect(x, y, TILE_SIZE, TILE_SIZE - 4);
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(x + 8, y + 4, 3, 3);
                graphicsContext.fillRect(x + 8, y + 14, 3, 3);
            }

            case RIGHT -> {
                y += 2;

                graphicsContext.fillRect(x, y, TILE_SIZE, TILE_SIZE - 4);
                graphicsContext.setFill(Color.BLACK);
                graphicsContext.fillRect(x + 17, y + 4, 3, 3);
                graphicsContext.fillRect(x + 17, y + 14, 3, 3);
            }

        }
    }

    public void drawApple() {
        for (int i = 0; i < apples.size(); i++) {
            Apple apple = apples.get(i);
            if (apple.xPos == -1) {
                Point point = apple.createApple(snake.snakeBody, apples, bot);
                graphicsContext.setFill(Color.RED);
                graphicsContext.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public void testdraw() {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(0, 0, TILE_SIZE, TILE_SIZE);
    }

    public void reset() {
        snake.reset();
        apples = new ArrayList<>();
        if (botFlag) {
            bot.reset();
        }

        apples.add(new Apple(FIELD_SIZE_SQRS));

        if (FIELD_SIZE_PX >= 500) {
            apples.add(new Apple(FIELD_SIZE_SQRS));
            if (!botFlag) {
                apples.add(new Apple(FIELD_SIZE_SQRS));
            }
        }
    }

    public void appleEaten(int index) {
        Apple apple = apples.get(index);
        apple.xPos = -1;
        apple.yPos = -1;
    }

    public int getFIELD_SIZE_SQRS() {
        return FIELD_SIZE_SQRS;
    }
}
