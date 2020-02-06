package kr.gemblo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kr.gemblo.GembloApp;
import kr.gemblo.utils.ModalUtil;

import java.io.IOException;

public class MenuController {
    private static MenuController instance;
    private static Scene SCENE;

    static {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(
                    MenuController.class.getModule().getResourceAsStream(
                            "kr/gemblo/fxml/menu.fxml")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent parent = fxmlLoader.getRoot();
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        SCENE = scene;
        instance = fxmlLoader.getController();
    }

    public static MenuController getInstance() {
        synchronized (MenuController.class) {
            if (instance == null) {
                instance = new MenuController();
            }
            return instance;
        }
    }

    public void show() {
        GembloApp.primaryStage.setScene(SCENE);
    }

    @FXML
    public void OnStartClicked() {
        SettingsController.getInstance().show();
    }

    @FXML
    public void OnHowToPlayClicked() {
        HowToPlayController.getInstance().show();
    }

    @FXML
    public void OnEntered(MouseEvent event) {
        Text text = (Text) ((StackPane) event.getSource()).getChildren().get(0);
        text.setText("▷ " + text.getText());
    }

    @FXML
    public void OnExited(MouseEvent event) {
        Text text = (Text) ((StackPane) event.getSource()).getChildren().get(0);
        text.setText(text.getText().replace("▷ ", ""));
    }

    @FXML
    public void OnQuitClicked() {
        ModalUtil.confirm(
                "QUIT",
                "Did you really want to quit?",
                () -> System.exit(1)
        );
    }
}
