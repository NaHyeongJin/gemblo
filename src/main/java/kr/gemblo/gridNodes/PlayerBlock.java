package kr.gemblo.gridNodes;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.utils.MediaUtil;

public class PlayerBlock extends GridNode {
    private final static String FILE_PATH = "/images/Tiles/Hexagon%s.png";

    public PlayerBlock(int matX, int matY, int playerNumber) {
        rX = matX;
        rY = matY;
        isEven(matX, matY);
        linearTransformationX(matX, matY);
        linearTransformationY(matX, matY);

        this.playerNumber = playerNumber;
        name = "PlayerBlock";
        canMoveTo = false;
        image = new Rectangle(WIDTH, HEIGHT);
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format(FILE_PATH, playerNumber))));
    }

    public PlayerBlock(int matX, int matY, int playerNumber, boolean canNotPlace) {
        rX = matX;
        rY = matY;
        isEven(matX, matY);
        linearTransformationX(matX, matY);
        linearTransformationY(matX, matY);

        this.playerNumber = playerNumber;
        name = "PlayerBlock";
        canMoveTo = false;
        image = new Rectangle(WIDTH, HEIGHT);
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage("/images/Tiles/HexagonCanNot.png")));
    }
}
