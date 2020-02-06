package kr.gemblo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import kr.gemblo.GembloApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class HowToPlayController {
    private static HowToPlayController instance;
    private static Scene SCENE;

    private static final String[] IMAGE_PATH_ARY = {
            "/images/Menu/HowToPlay1.png",
            "/images/Menu/HowToPlay2.png",
            "/images/Menu/HowToPlay3.png"
    };


    private static final Color SLIDER_NOT_SELECTED_COLOR =
            Color.rgb(76, 248, 87, 0.4);
    private static final Color SLIDER_SELECTED_COLOR =
            Color.rgb(76, 248, 87, 1);

    private List<Image> imageList = new ArrayList<>();
    private List<Circle> imageSliderList = new ArrayList<>();

    @FXML
    private ImageView imageView;
    @FXML
    private HBox imageSlider;

    static {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(
                    HowToPlayController.class.getModule().getResourceAsStream(
                            "kr/gemblo/fxml/howToPlay.fxml"
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent parent = fxmlLoader.getRoot();
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        SCENE = scene;
        instance = fxmlLoader.getController();

        // Set images and texts
        instance.imageList.add(new Image("kr/gemblo/images/Menu/HowToPlay1.png"));
        instance.imageList.add(new Image("kr/gemblo/images/Menu/HowToPlay2.png"));
        instance.imageList.add(new Image("kr/gemblo/images/Menu/HowToPlay3.png"));
        instance.imageView.setImage(instance.imageList.get(0));

        // Add image slider
        IntStream.range(0, IMAGE_PATH_ARY.length).forEach(i -> {
            Circle imageSlider = instance.createImageSlider();
            instance.imageSliderList.add(imageSlider);
            instance.imageSlider.getChildren().add(
                    instance.imageSlider.getChildren().size() - 1,
                    imageSlider
            );
            if (i != IMAGE_PATH_ARY.length - 1) {
                HBox.setMargin(imageSlider, new Insets(0, 10, 0, 0));
            }
        });

        // Add onKeyEvent
        SCENE.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    instance.onLeftSignClicked(null);
                    break;
                case RIGHT:
                    instance.onRightSignClicked(null);
                    break;
                default:
                    break;
            }
        });

        instance.imageSliderList.get(0).setFill(SLIDER_SELECTED_COLOR);

    }

    public static HowToPlayController getInstance() {
        synchronized (HowToPlayController.class) {
            if (instance == null) {
                instance = new HowToPlayController();
            }
            return instance;
        }
    }

    public void show() {
        GembloApp.primaryStage.setScene(SCENE);
    }

    @FXML
    public void onBackToMenuClicked() {
        MenuController.getInstance().show();
    }

    private void slide(int newIndex) {
        this.imageView.setImage(instance.imageList.get(newIndex));
        this.imageSliderList.get(newIndex).setFill(SLIDER_SELECTED_COLOR);
    }

    @FXML
    void onLeftSignClicked(MouseEvent event) {
        int index = this.imageList.indexOf(this.imageView.getImage());
        this.imageSliderList.get(index).setFill(SLIDER_NOT_SELECTED_COLOR);
        if (index == 0) {
            index = this.imageList.size();
        }

        this.slide(--index);
    }

    @FXML
    void onRightSignClicked(MouseEvent event) {
        int index = this.imageList.indexOf(this.imageView.getImage());
        this.imageSliderList.get(index).setFill(SLIDER_NOT_SELECTED_COLOR);
        if (index == this.imageList.size() - 1) {
            index = -1;
        }

        this.slide(++index);
    }

    private Circle createImageSlider() {
        Circle result = new Circle();
        result.setRadius(15);
        result.setFill(SLIDER_NOT_SELECTED_COLOR);
        return result;
    }

}
