package kr.gemblo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import kr.gemblo.GembloApp;
import kr.gemblo.utils.ConstUtil;
import kr.gemblo.utils.ModalUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SettingsController {
    private static SettingsController instance;
    private static Scene SCENE;

    @FXML
    private ComboBox<Integer> howManyPlayersCombo;

    static {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(
                    SettingsController.class.getModule().getResourceAsStream(
                            "kr/gemblo/fxml/settings.fxml")
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

    public static SettingsController getInstance() {
        synchronized (SettingsController.class) {
            if (instance == null) {
                instance = new SettingsController();
            }
            return instance;
        }
    }

    public void show() {
        if (howManyPlayersCombo.getItems().size() == 0) {
            // Set combo values
            this.howManyPlayersCombo.getItems()
                    .addAll(
                            Arrays.stream(ConstUtil.PLAYER_NUMBERS)
                                    .boxed()
                                    .collect(Collectors.toList())
                    );

            this.howManyPlayersCombo.getSelectionModel().select(ConstUtil.DEFAULT_PLAYER_NUMBER_INDEX);

        }
        GembloApp.primaryStage.setScene(SCENE);
    }

    private boolean validate() {
        return !this.howManyPlayersCombo.getSelectionModel().isEmpty();
    }

    @FXML
    public void onStartClicked() {

        if (!validate()) {
            ModalUtil.alert("INVALID SETTINGS",
                    "There is an empty option"
            );
        } else {
            ModalUtil.confirm("START",
                    "Are you ready?",
                    () -> {
                        GameController.getInstance().show(this.howManyPlayersCombo.getSelectionModel().getSelectedItem());
                    }
            );
        }
    }

    @FXML
    public void onBackToMenuClicked() {
        ModalUtil.confirm("BACK TO MENU",
                "Go back to menu?",
                () -> MenuController.getInstance().show()
        );
    }

    @FXML
    public void OnEntered() {
    }

    @FXML
    public void OnPressed() {
    }

    @FXML
    public void OnExited() {
    }
}
