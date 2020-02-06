package kr.gemblo.gridNodes;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.utils.MediaUtil;

public class PlacableBlock extends GridNode {
    private final static String FILE_PATH = "/images/Tiles/HexagonExtra%s.png";

    public PlacableBlock(int matX, int matY, int playerNumber, int numberOfPlayers) {
        rX = matX;
        rY = matY;
        isEven(matX, matY);
        linearTransformationX(matX, matY);
        linearTransformationY(matX, matY);

        this.playerNumber = playerNumber % numberOfPlayers;
        if (this.playerNumber == 0) this.playerNumber = numberOfPlayers;

        name = "PlacableBlock";
        canMoveTo = false;
        image = new Rectangle(WIDTH, HEIGHT);
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format(FILE_PATH, this.playerNumber))));
    }
}
