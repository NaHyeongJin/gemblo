package kr.gemblo.extraScreens;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.utils.MediaUtil;

public class InstructionStuff extends Pane {
    public InstructionStuff() {
        Rectangle leftImage = new Rectangle(250, 250);
        leftImage.setFill(new ImagePattern(MediaUtil.createImage(
                "/images/HowToPlayL.png")));

        leftImage.setTranslateX(5);
        leftImage.setTranslateY(600);

        this.getChildren().add(leftImage);

        Rectangle rightImage = new Rectangle(250, 250);
        rightImage.setFill(new ImagePattern(MediaUtil.createImage(
                "/images/HowToPlayR.png")));
        rightImage.setTranslateX(260);
        rightImage.setTranslateY(600);
        this.getChildren().add(rightImage);
    }
}
