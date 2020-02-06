package kr.gemblo.extraScreens;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import kr.gemblo.utils.MediaUtil;


public class GameOverScreen extends Pane {
    Node backgroundImage;
    Text gameOverText;
    String winningPlayerString;

    public GameOverScreen(int[] winningPlayerNumber) {
        winningPlayerString = new String();
        backgroundImage = new Rectangle(1300, 850);
        ((Rectangle) backgroundImage)
                .setFill(new ImagePattern(MediaUtil.createImage("/images/WhiteBack.png")));
        this.getChildren().add(backgroundImage);

        for (int i = 0; i < winningPlayerNumber.length; i++) {
            winningPlayerString = winningPlayerString + " " + winningPlayerNumber[i];
        }
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");
        gameOverText = new Text(String.format("Player%s wins!", winningPlayerString));
        gameOverText.setFont(
                Font.font("Times New Roman", FontWeight.BOLD, 100)
        );

        gameOverText.setFill(Color.BLACK);

        this.setPrefSize(1300, 850);


        gameOverText.setTranslateX(250);
        gameOverText.setTranslateY(300);

        this.getChildren().add(gameOverText);
    }
}
