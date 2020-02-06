package kr.gemblo.gameBoard;

import javafx.scene.layout.GridPane;
import kr.gemblo.gridNodes.BackGround;

public class BackgroundGrid {

    GridPane gridPane = new GridPane();
    public static BackgroundGrid instance;

    BackgroundGrid() {
        this.gridPane.add(new BackGround(0, 0).getImage(), 0, 0);

        instance = this;
    }
}
