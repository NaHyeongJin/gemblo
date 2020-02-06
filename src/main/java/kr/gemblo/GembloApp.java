package kr.gemblo;

import javafx.application.Application;
import javafx.stage.Stage;
import kr.gemblo.controller.MenuController;
import kr.gemblo.utils.ConstUtil;

import java.util.ResourceBundle;

public class GembloApp extends Application {

    public static Stage primaryStage;

    public static void main(String[] args) {
        ConstUtil.setResourceBundle(ResourceBundle.getBundle("kr/gemblo/application"));
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GembloApp.primaryStage = primaryStage;
        primaryStage.setTitle(ConstUtil.APP_NAME);

        
        // Start Menu
        MenuController.getInstance().show();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
