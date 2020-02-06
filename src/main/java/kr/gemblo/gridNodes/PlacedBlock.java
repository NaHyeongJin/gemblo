package kr.gemblo.gridNodes;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.utils.MediaUtil;

public class PlacedBlock extends GridNode {
    private final static String FILE_PATH = "/images/Tiles/Hexagon%s.png";

    public PlacedBlock(int matX, int matY, int playerNumber, int numberOfPlayers) {
        rX = matX;
        rY = matY;
        isEven(matX, matY);
        linearTransformationX(matX, matY);
        linearTransformationY(matX, matY);

        this.playerNumber = playerNumber % numberOfPlayers;
        if (this.playerNumber == 0) this.playerNumber = numberOfPlayers;

        name = "PlacedBlock";
        canMoveTo = false;
        image = new Rectangle(WIDTH, HEIGHT);
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format(FILE_PATH, this.playerNumber))));
    }
}
