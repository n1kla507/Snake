package game.entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Apple {
    public int xPos;
    public int yPos;
    private final int fieldSquares;

    public Apple(int fieldSquares) {
        this.fieldSquares = fieldSquares;
        xPos = -1;
        yPos = -1;
    }

    public Point createApple(ArrayList<Point> snake, ArrayList<Apple> apples, Bot bot) {
        Point applePoint;
        do {
            int rndmPosX = ThreadLocalRandom.current().nextInt(0, fieldSquares);
            int rndmPosY = ThreadLocalRandom.current().nextInt(0, fieldSquares);
            applePoint = new Point(rndmPosX, rndmPosY);
        } while (!isValidPos(applePoint, snake, apples, bot));

        xPos = applePoint.x;
        yPos = applePoint.y;

        return applePoint;
    }

    public boolean isValidPos(Point point, ArrayList<Point> snake, ArrayList<Apple> apples, Bot bot) {
        for (Point value : snake) {
            if (point.equals(value)) {
                return false;
            }
        }
        for (Apple apple : apples) {
            if (apple.xPos != -1) {
                if (point.equals(getApplePoint())) {
                    return false;
                }
            }
        }

        if (bot != null) {
            ArrayList<Point> bots = bot.botSnake;
            for (Point value : bots) {
                if (point.equals(value)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Point getApplePoint() {
        return new Point(xPos, yPos);
    }
}
