package game.launcher.gamelogic;

import game.launcher.enums.Difficultie;
import game.launcher.enums.Direction;
import game.launcher.enums.MenuAction;
import game.launcher.windows.GameOver;
import game.launcher.entities.Apple;
import game.launcher.entities.Bot;
import game.launcher.entities.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;

public class GameLoop {
    private final AnimationTimer timer;
    private final DrawingHandler drawingHandler;
    private final InputHandler inputHandler;
    private final Scene snakeScene;
    private final int time = 200_000_000;
//    private final int time = 800_000_000;
    //aktualisierung (tick) jede sekunde (200_000_000 is normale tick rate)
    private boolean gameover = false;
    private Point lastPoint = null;
    private Direction lastDirection = Direction.RIGHT;
    private Point lastPointBot = null;
    private Direction lastDirectionBot = Direction.RIGHT;
    public boolean menuFlag = false;
    private final boolean botFlag;
    private final Bot bot;
    private final Snake snake;
    private int punkte;
    private Label apfel;

    public GameLoop(GraphicsContext gc, int fieldSize, boolean botFlag, Difficultie difficultie) {
        snake = new Snake(fieldSize);
        this.botFlag = botFlag;
        if (botFlag) {
            bot = new Bot(difficultie, snake);
        } else bot = null;
        drawingHandler = new DrawingHandler(gc, fieldSize, botFlag, bot, snake);
        snakeScene = gc.getCanvas().getScene();
        inputHandler = new InputHandler(snakeScene, snake);
        apfel = (Label) snakeScene.lookup("#apfel");

        punkte = 0;
        drawingHandler.drawMap();

        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= time) {
                    inputHandler.locked = false;
                    if (botFlag) {
                        updateBot();
                    }
                    update();
                    draw();
                    lastUpdate = now;
                }
            }
        };
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        System.out.println("STOPPPPPPP");
        timer.stop();

        Platform.runLater(() -> {
            GameOver popup = new GameOver(punkte);
            MenuAction action = popup.popUpErstellen();

            switch (action) {
                case CLOSE -> System.exit(0);
                case RESTART -> {
                    drawingHandler.reset();
                    punkte = 0;
                    apfel.setText("Äpfel " + punkte);
                    startingBoard();
                }
                case MAIN_MENU -> {
                    menuFlag = true;
                    Stage stage = (Stage) snakeScene.getWindow();
                    stage.close();
                }
            }
        });

    }

    public void startingBoard() {
        drawingHandler.drawMap();

        EventHandler<KeyEvent> startingGameKey = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode key = keyEvent.getCode();
                if (key == KeyCode.W || key == KeyCode.A || key == KeyCode.S || key == KeyCode.D || key == KeyCode.UP || key == KeyCode.DOWN || key == KeyCode.LEFT || key == KeyCode.RIGHT) {
                    snakeScene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                    drawingHandler.drawApple();
                    startTimer();
                }
            }
        };
        snakeScene.addEventHandler(KeyEvent.KEY_PRESSED, startingGameKey);
    }

    public void draw() {
        if (!gameover) {
            drawingHandler.drawApple();
            drawingHandler.drawSnake(lastPoint, snake.snakeBody, snake.getDirection(), lastDirection, false);
            lastDirection = snake.getDirection();
            if (botFlag) {
                drawingHandler.drawSnake(lastPointBot, bot.botSnake, bot.getDirection(),lastDirectionBot, true);
                lastDirectionBot = bot.getDirection();
            }
        }
    }

    public void update() {
        snake.update();

        Point point = snake.snakeBody.getFirst();
        System.out.println("neue pos " + point);

        if (isApple(point)) {
            punkte++;
            apfel.setText("Äpfel: " + punkte);
            System.out.println("TRUEEEEEEEEEEEEEEEEEE");
            lastPoint = new Point(-1,-1);
        } else {
            lastPoint = snake.snakeBody.getLast();
            snake.snakeBody.removeLast();
        }

        gameover = isGameOver(point);
        if (gameover) {
            System.out.println("BORDEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEER");
            stopTimer();
        }

    }

    public void updateBot() {
        bot.dependOnDifficultie(drawingHandler.apples);

        Point point = bot.botSnake.getFirst();

        if (isApple(point)) {
            lastPointBot = new Point(-1, -1);
        } else {
            lastPointBot = bot.botSnake.getLast();
            bot.botSnake.removeLast();
        }
    }

    public boolean isGameOver(Point snakeHead) {
        int fieldSize = drawingHandler.getFIELD_SIZE_SQRS();
        if (snakeHead.x == fieldSize || snakeHead.x == -1) {
            return true;
        } else if (snakeHead.y == fieldSize || snakeHead.y == -1) {
            return true;
        }

        for (int i = 3; i < snake.snakeBody.size(); i++) {
            Point temp = snake.snakeBody.get(i);
            if (temp.equals(snakeHead)) {
                return true;
            }
        }

        if (botFlag) {
            Point botHead = bot.botSnake.getFirst();

            if (botHead.x == fieldSize || botHead.x == -1) {
                return true;
            } else if (botHead.y == fieldSize || botHead.y == -1) {
                return true;
            }

            for (int i = 3; i < bot.botSnake.size(); i++) {
                if (bot.botSnake.get(i).equals(botHead)) {
                    return true;
                }
            }

            for (int i = 0; i < snake.snakeBody.size(); i++) {
                Point temp = snake.snakeBody.get(i);
                if (botHead.equals(temp)) {
                    return true;
                }
            }
            for (int i = 0; i < bot.botSnake.size(); i++) {
                Point temp = bot.botSnake.get(i);
                if (snakeHead.equals(temp)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isApple(Point point) {
        boolean isApple = false;
        for (int i = 0; i < drawingHandler.apples.size(); i++) {
            Apple apple = drawingHandler.apples.get(i);
            Point applePoint = apple.getApplePoint();
            isApple = point.equals(applePoint);
            if (isApple) {
                drawingHandler.appleEaten(i);
                return true;
            }
        }
        return isApple;
    }
}
