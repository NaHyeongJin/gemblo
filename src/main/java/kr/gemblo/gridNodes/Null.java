package kr.gemblo.gridNodes;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.utils.MediaUtil;

public class Null extends GridNode {
    private final static String FILE_PATH = "/images/Tiles/Hexagon.png";

    public Null(int matX, int matY) {
        rX = matX;
        rY = matY;
        isEven(matX, matY);
        linearTransformationX(matX, matY);
        linearTransformationY(matX, matY);
        name = "Null";
        canMoveTo = false;
        image = new Rectangle(WIDTH, HEIGHT);
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(FILE_PATH)));
    }
}
