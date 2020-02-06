package kr.gemblo.controller;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import kr.gemblo.GembloApp;
import kr.gemblo.gameBoard.GameScene;

public class GameController {
    private static GameController instance;
    private static GameScene game;

    static {
        instance = new GameController();
    }

    public static GameController getInstance() {
        synchronized (GameController.class) {
            if (instance == null) {
                instance = new GameController();
            }
            return instance;
        }
    }

    void show(int players) {
        game = new GameScene.Builder().player(players).build();
        game.setStyle("-fx-background-color: #000000;");
        game.setPrefSize(1200, 850);
        Scene scene = new Scene(game);
        scene.setOnKeyPressed(e -> game.onKeyPressed(e.getCode()));
        scene.setFill(Color.TRANSPARENT);

        GembloApp.primaryStage.setScene(scene);
    }
}
