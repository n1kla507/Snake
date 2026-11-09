package game.launcher.entities;

import game.launcher.enums.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private Direction direction;
    public ArrayList<Point> snakeBody;
    private final int fieldSize;
    private final int FIELD_SIZE_SQRS;
    private final int TILE_SIZE;

    public Snake(int fieldSize){
        this.fieldSize = fieldSize;
        TILE_SIZE = 25;
        this.FIELD_SIZE_SQRS = fieldSize / TILE_SIZE;
        snakeBody = new ArrayList<>();
        direction = Direction.RIGHT;
        buildsnake();
    }

    public void update(){
        Point point = snakeBody.getFirst();

        switch (direction) {
            case RIGHT -> snakeBody.addFirst(new Point(point.x + 1, point.y));
            case LEFT -> snakeBody.addFirst(new Point(point.x - 1, point.y));
            case DOWN -> snakeBody.addFirst(new Point(point.x, point.y + 1));
            case UP -> snakeBody.addFirst(new Point(point.x, point.y - 1));
        }
    }

    public void buildsnake(){
        int y = FIELD_SIZE_SQRS / 2;
        int x = FIELD_SIZE_SQRS / 2 - 1;
        snakeBody.add(new Point(x, y));
        x -= 1;
        snakeBody.add(new Point(x, y));

        if (fieldSize >= 500) {
            x -= 1;
            snakeBody.add(new Point(x, y));
            x -= 1;
            snakeBody.add(new Point(x, y));
        } else if (fieldSize >= 375) {
            x -= 1;
            snakeBody.add(new Point(x, y));
        }
    }

    public void reset(){
        direction = Direction.RIGHT;
        snakeBody = new ArrayList<>();
        buildsnake();
    }

    public Direction getDirection(){
        return direction;
    }

    public void  setDirection(Direction direction){
        this.direction = direction;
    }
}
