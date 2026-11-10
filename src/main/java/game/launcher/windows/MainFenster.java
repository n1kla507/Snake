package game.launcher.windows;

import game.launcher.enums.Difficultie;
import game.launcher.gamelogic.GameLoop;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MainFenster extends Application {
    private Stage mainstage;
    private Stage snakeStage;
    //500, 375 oder 250
    private int canvasSize = 500;
    private GraphicsContext graphicsContext;
    private boolean botFlag = false;
    private Difficultie difficultie = Difficultie.EASY;

    @Override
    public void start(Stage mainstage) throws Exception {
        this.mainstage = mainstage;

        Scene mainScene = new Scene(createContent(), 400, 300);

        mainstage.centerOnScreen();
        mainstage.setTitle("Snake");
        mainstage.setScene(mainScene);
        mainstage.show();
    }

    public void restartGame() throws Exception {
        start(mainstage);
    }


    // das Spiel wird über Launcher Klasse gestartet
    public static void main(String[] args) {
        launch(args);
    }

    public Region createContent() {
        VBox vBox = new VBox();

        ComboBox<Object> fieldSizeValue = new ComboBox<>();
        fieldSizeValue.getItems().add("20x20");
        fieldSizeValue.getItems().add("15x15");
        fieldSizeValue.getItems().add("10x10");
        fieldSizeValue.getItems().add("Bot-easy");
        fieldSizeValue.getItems().add("Bot-medium");
        fieldSizeValue.getItems().add("Bot-hard");
        fieldSizeValue.getSelectionModel().selectFirst();

        /*
        Bot als Spiel modi hinzufügen
        google suche für difficulties:
        snake game bot mit difficulties
         */

        Button btn = getButton(fieldSizeValue);

        Label gameName = new Label();
        gameName.setText("Snake!");
        gameName.setFont(new Font(30));
        gameName.setTextFill(Color.WHITE);

        vBox.getChildren().add(gameName);
        vBox.getChildren().add(fieldSizeValue);
        vBox.getChildren().add(btn);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);
        root.setStyle("-fx-background-color: #343435");

        return root;
    }

    private Button getButton(ComboBox<Object> fieldSizeValue) {
        Button btn = new Button("Starte das Spiel");
        btn.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(6),
                                Insets.EMPTY
                        )
                ));

        btn.setBorder(
                new Border(
                        new BorderStroke(
                                Color.RED,
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(5),
                                new BorderWidths(3))));

        btn.setOnAction(event -> {
            mainstage.close();

            switch(fieldSizeValue.getSelectionModel().getSelectedIndex()){
                case 0 -> {
                    botFlag = false;
                    canvasSize = 500;
                }
                case 1 -> {
                    botFlag = false;
                    canvasSize = 375;
                }
                case 2 -> {
                    botFlag = false;
                    canvasSize = 250;
                }
                case 3 -> {
                    difficultie = Difficultie.EASY;
                    botFlag = true;
                    canvasSize = 500;
                }
                case 4 -> {
                    difficultie = Difficultie.MID;
                    botFlag = true;
                    canvasSize = 500;
                }
                case 5 -> {
                    difficultie = Difficultie.HARD;
                    botFlag = true;
                    canvasSize = 500;
                }
            }

            startSnakeWindow();
        });
        return btn;
    }

    public void startSnakeWindow() {
        Scene snakeScene = new Scene(createSnakeWindow(), 550, 650);
        GameLoop gameLoop = new GameLoop(graphicsContext, canvasSize, botFlag, difficultie);
        gameLoop.startingBoard();

        snakeStage = new Stage();
        snakeStage.centerOnScreen();
        snakeStage.setTitle("Snake");
        snakeStage.setScene(snakeScene);
        snakeStage.showAndWait();

        if (gameLoop.menuFlag) {
            try {
                restartGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Region createSnakeWindow() {
        StackPane snakeRoot = new StackPane();
        snakeRoot.setStyle("-fx-background-color: #343435");

        VBox box = new VBox();
        Label apfel = new Label();
        apfel.setFont(new Font(20));
        apfel.setTextFill(Color.WHITE);
        apfel.setText("Äpfel: " + 0);
        apfel.setId("apfel");

        Label highscore = new Label();
        highscore.setFont(new Font(20));
        highscore.setTextFill(Color.WHITE);
        highscore.setText("Highscore: " + 0);
        highscore.setId("highscore");

        Canvas snakeCanvas = new Canvas(canvasSize, canvasSize);

        box.getChildren().add(apfel);
        box.getChildren().add(highscore);
        box.getChildren().add(snakeCanvas);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);

        snakeRoot.getChildren().add(box);
//        snakeRoot.getChildren().add(snakeCanvas);

        graphicsContext = snakeCanvas.getGraphicsContext2D();

        return snakeRoot;
    }
}
