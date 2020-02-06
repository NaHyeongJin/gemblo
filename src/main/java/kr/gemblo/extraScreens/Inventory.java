package kr.gemblo.extraScreens;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.playerElements.Player;
import kr.gemblo.utils.MediaUtil;

import java.util.Vector;

import static kr.gemblo.gridNodes.GridNode.RADIUS;
import static kr.gemblo.gridNodes.GridNode.ROOT3;

public class Inventory extends Pane {
    private static final String FILE_PATH = "/images/Tiles/Hexagon%s.png";

    public Inventory(Player player, boolean isSelected, int selectedBlockNumber, int blockRow, int blockColumn) {
        Vector<GridPane> gridPaneOdd = new Vector<>(player.getBlocksSize(), 1);
        Vector<GridPane> gridPaneEven = new Vector<>(player.getBlocksSize(), 1);
        for (int i = 0; i < player.getBlocksSize() + 5 / 6; i++) {

            for (int j = 0; j < 6; j++) {

                if (i * 6 + j >= player.getBlocksSize()) break;

                GridPane tempGridPaneOdd = new GridPane();
                GridPane tempGridPaneEven = new GridPane();

                setGridPaneOdd(tempGridPaneOdd);
                setGridPaneEven(tempGridPaneEven);

                for (int k = 0; k < player.getBlocks(i * 6 + j).pointX.length; k++) {
                    Rectangle image = new Rectangle(30, 26);
                    if (isSelected && (i * 6 + j) == selectedBlockNumber) {
                        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("/images/Null.png")));
                    } else if (i == blockRow && j == blockColumn)
                        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format("/images/Tiles/HexagonExtra%s.png", player.getPlayerNumber()))));
                    else
                        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format(FILE_PATH, player.getPlayerNumber()))));
                    if (player.getBlocks(i * 6 + j).gridX[k] % 2 == 0)
                        tempGridPaneEven.add(image, 4 - player.getBlocks(i * 6 + j).gridX[k], (player.getBlocks(i * 6 + j).gridY[k]) / 2);
                    else
                        tempGridPaneOdd.add(image, 4 - player.getBlocks(i * 6 + j).gridX[k], (player.getBlocks(i * 6 + j).gridY[k] + 1) / 2);
                }

                tempGridPaneOdd.setLayoutX(88 * j + 7);
                tempGridPaneOdd.setLayoutY(130 * i - 13);
                tempGridPaneEven.setLayoutX(88 * j);
                tempGridPaneEven.setLayoutY(130 * i);

                gridPaneEven.add(tempGridPaneEven);
                gridPaneOdd.add(tempGridPaneOdd);

                this.getChildren().add(tempGridPaneEven);
                this.getChildren().add(tempGridPaneOdd);
            }
        }
    }

    void setGridPane(GridPane gridPane) {
        gridPane.getColumnConstraints().add(new ColumnConstraints((2 * RADIUS) - 2));
        gridPane.getColumnConstraints().add(new ColumnConstraints(RADIUS - 2));
        gridPane.getColumnConstraints().add(new ColumnConstraints((2 * RADIUS) - 2));
        gridPane.getColumnConstraints().add(new ColumnConstraints(RADIUS - 2));
    }

    void setGridPaneOdd(GridPane gridPane) {
        gridPane.getColumnConstraints().add(new ColumnConstraints(RADIUS - 2));
        setGridPane(gridPane);
        for (int i = 0; i < 5; i++) gridPane.getRowConstraints().add(new RowConstraints((ROOT3 * RADIUS) - 2));
    }

    void setGridPaneEven(GridPane gridPane) {
        setGridPane(gridPane);
        gridPane.getColumnConstraints().add(new ColumnConstraints((2 * RADIUS) - 2));
        for (int i = 0; i < 5; i++) gridPane.getRowConstraints().add(new RowConstraints((ROOT3 * RADIUS) - 2));
    }
}
