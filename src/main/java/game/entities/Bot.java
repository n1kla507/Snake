package game.entities;

import game.enums.Difficultie;
import game.enums.Direction;

import java.awt.*;
import java.util.ArrayList;

public class Bot {
    private final Difficultie difficultie;
    public ArrayList<Point> botSnake;
    private Direction direction;
    private ArrayList<Apple> apples;
    private Snake playerSnake;
    private final Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
    private final int FIELD_SIZE = 20;

    public Bot(Difficultie difficultie, Snake playerSnake) {
        this.difficultie = difficultie;
        this.playerSnake = playerSnake;
        botSnake = new ArrayList<>();
        botSnake.add(new Point(8, 4));
        botSnake.add(new Point(7, 4));
        botSnake.add(new Point(6, 4));
        botSnake.add(new Point(5, 4));

        direction = Direction.RIGHT;
    }

    public void dependOnDifficultie(ArrayList<Apple> apples) {
        this.apples = apples;
        switch (difficultie) {
            case EASY -> updateEasy();
            case MID -> updateMid();
            case HARD -> updateHard();
        }
    }

    private void updateEasy() {
        System.out.println("UPDATE EASYYYY");
        direction = direction.rndmPossibleDirection(direction);
        Point point = botSnake.getFirst();
        Point point2 = newPoint(point, direction);
        System.out.println("point 2: " + point2);
        botSnake.addFirst(point2);
        System.out.println("neue Bot Richtung: " + direction);
        System.out.println("neue Bot Position: " + botSnake.getFirst());
    }

    private void updateMid() {
        Point apple1 = new Point(apples.getFirst().getApplePoint());
        Point apple2 = new Point(apples.get(1).getApplePoint());
        Point first = botSnake.getFirst();
        int bestDistance = 100;
        Direction bestDirection = direction;
        Point bestPoint = new Point();

        int skip = getSkip(direction);
        System.out.println("direction: " + direction);
        System.out.println("skip: " + skip);

        for (int i = 0; i < directions.length; i++) {
            System.out.println("i: " + i);
            if (i != skip) {
                System.out.println("not skipped");
                Point point = newPoint(first, directions[i]);
                int distance1 = getDistance(apple1, point);
                int distance2 = getDistance(apple2, point);
                int shortest = Math.min(distance2, distance1);
                System.out.println("shortest: " + shortest);
                System.out.println("bestDistance: " + bestDistance);
                if (shortest < bestDistance) {
                    System.out.println("true");
                    bestDistance = shortest;
                    bestPoint = point;
                    bestDirection = directions[i];
                    System.out.println("best Direction: " + bestDirection);
                }
            }
        }

        System.out.println("final Direction: " + bestDirection);
        botSnake.addFirst(bestPoint);
        direction = bestDirection;
        System.out.println("neue Bot Richtung: " + direction);
        System.out.println("neue Bot Position: " + botSnake.getFirst());
    }

    private void updateHard() {
        Point apple1 = new Point(apples.getFirst().getApplePoint());
        Point apple2 = new Point(apples.get(1).getApplePoint());
        Point head = botSnake.getFirst();
        int bestDistance = 100;
        Direction bestDirection = direction;
        Point bestPoint = null;

        int skip = getSkip(direction);

        System.out.println("direction: " + direction);
        System.out.println("skip: " + skip);

        for (int i = 0; i < directions.length; i++) {
            System.out.println("i: " + i);
            if (i != skip) {
                System.out.println("not skipped");
                Point point = newPoint(head, directions[i]);
                boolean possibleMove = !isHittingSnake(point) && !isHittingWall(point) && !isHittingSelf(point);
                if (possibleMove) {
                    int distance1 = getDistance(apple1, point);
                    int distance2 = getDistance(apple2, point);
                    int shortest = Math.min(distance2, distance1);
                    System.out.println("shortest: " + shortest);
                    System.out.println("bestDistance: " + bestDistance);
                    if (shortest < bestDistance) {
                        System.out.println("true");
                        bestDistance = shortest;
                        bestPoint = point;
                        bestDirection = directions[i];
                        System.out.println("best Direction: " + bestDirection);
                    }
                }
            }
        }
        if (bestPoint == null) {
            bestPoint = newPoint(head, direction);
        }
        System.out.println("final Direction: " + bestDirection);
        botSnake.addFirst(bestPoint);
        direction = bestDirection;
        System.out.println("neue Bot Richtung: " + direction);
        System.out.println("neue Bot Position: " + botSnake.getFirst());
    }

    private int getDistance(Point appleP, Point snakeP) {
        return Math.abs(appleP.x - snakeP.x) + Math.abs(appleP.y - snakeP.y);
    }

    private Point newPoint(Point point, Direction tryDirection) {
        switch (tryDirection) {
            case RIGHT -> {
                return new Point(point.x + 1, point.y);
            }
            case LEFT -> {
                return new Point(point.x - 1, point.y);
            }
            case DOWN -> {
                return new Point(point.x, point.y + 1);
            }
            case UP -> {
                return new Point(point.x, point.y - 1);
            }
        }
        return null;
    }

    private int getSkip(Direction direction) {
        int skip = -1;
        switch (direction) {
            case UP -> skip = 2;
            case RIGHT -> skip = 3;
            case DOWN -> skip = 0;
            case LEFT -> skip = 1;
        }
        return skip;
    }

    private boolean isHittingSnake(Point point) {
        for (Point player : playerSnake.snakeBody) {
            if (player.equals(point)) {
                return true;
            }
        }
        return false;
    }

    private boolean isHittingWall(Point point) {
        if (point.x == FIELD_SIZE || point.x == -1) {
            return true;
        } else if (point.y == FIELD_SIZE || point.y == -1) {
            return true;
        }
        return false;
    }

    private boolean isHittingSelf(Point nextMove) {
        for (Point point : botSnake){
            if (point.equals(nextMove)){
                return true;
            }
        }
        return false;
    }

    public void reset() {
        botSnake = new ArrayList<>();
        botSnake.add(new Point(8, 4));
        botSnake.add(new Point(7, 4));
        botSnake.add(new Point(6, 4));
        botSnake.add(new Point(5, 4));

        direction = Direction.RIGHT;
    }

    public Direction getDirection(){
        return direction;
    }
}