package game.windows;

import game.enums.MenuAction;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameOver {
    Stage popUpStage;
    private MenuAction action = MenuAction.RESUME;
    private final int punkte;
    private final boolean isWinner;
    private final boolean winner;

    public GameOver(int punkte) {
        this.punkte = punkte;
        isWinner = false;
        winner = false;
    }

    public  GameOver(int punkte, boolean winner) {
        this.punkte = punkte;
        isWinner = true;
        this.winner = winner;
    }

    public MenuAction popUpErstellen() {
        popUpStage = new Stage();
        popUpStage.setTitle("Auswahl Fenster");
        popUpStage.centerOnScreen();

        Scene popOpScene = new Scene(createContent(), 200, 200);
        popUpStage.setScene(popOpScene);
        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.showAndWait();
        return action;
    }

    private Region createContent() {
        VBox auswahl = new VBox();

        HBox stats = new HBox();

        Label gewinner = new Label();
        if(winner) {
            if (isWinner) {
                gewinner.setText("Du hast gewonnen!");
            }else gewinner.setText("Du hast verloren!");
            stats.getChildren().add(gewinner);
        }

        Label punkte = new Label();
        punkte.setText("Punkte: " + this.punkte);
        punkte.setFont(new Font(20));
        punkte.setTextFill(Color.WHITE);
        stats.getChildren().add(punkte);
        stats.setAlignment(Pos.CENTER);

        Button neustart = new Button("Restart");
        neustart.setOnAction(actionEvent -> {
            action = MenuAction.RESTART;
            popUpStage.close();
        });

        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(actionEvent -> {
            action = MenuAction.MAIN_MENU;
            popUpStage.close();
        });

        Button closeGame = new Button("Close Game");
        closeGame.setOnAction(actionEvent -> {
            action = MenuAction.CLOSE;
            popUpStage.close();
        });

        auswahl.getChildren().add(stats);
        auswahl.getChildren().add(neustart);
        auswahl.getChildren().add(mainMenu);
        auswahl.getChildren().add(closeGame);
        auswahl.setAlignment(Pos.CENTER);
        auswahl.setSpacing(10);

        StackPane root = new StackPane();
        root.getChildren().add(auswahl);
        root.setStyle("-fx-background-color: #343435");
        return root;
    }

}
