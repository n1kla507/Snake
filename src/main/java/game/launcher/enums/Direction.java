package game.launcher.enums;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    RIGHT,
    LEFT,
    UP,
    DOWN;

    public Direction rndmPossibleDirection(Direction direction){
        int temp = ThreadLocalRandom.current().nextInt(0,4);

        switch (direction){
            case UP -> {
                switch (temp){
                    case 0 -> {
                        return Direction.LEFT;
                    }
                    case 1,4 -> {
                        return Direction.UP;
                    }
                    case 2 -> {
                        return Direction.RIGHT;
                    }
                }
            }
            case DOWN -> {
                switch (temp){
                    case 0 -> {
                        return Direction.RIGHT;
                    }
                    case 1,4 -> {
                        return Direction.DOWN;
                    }
                    case 2 -> {
                        return Direction.LEFT;
                    }
                }
            }
            case LEFT -> {
                switch (temp){
                    case 0 -> {
                        return Direction.DOWN;
                    }
                    case 1,4 -> {
                        return Direction.LEFT;
                    }
                    case 2 -> {
                        return Direction.UP;
                    }
                }
            }
            case RIGHT -> {
                switch (temp){
                    case 0 -> {
                        return Direction.UP;
                    }
                    case 1,4 -> {
                        return Direction.RIGHT;
                    }
                    case 2 -> {
                        return Direction.DOWN;
                    }
                }
            }
        }
        return direction;
    }
}
