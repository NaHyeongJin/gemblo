package kr.gemblo.gridNodes;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.utils.MediaUtil;

public class Base extends GridNode {
    private final static String FILE_PATH = "/images/Tiles/Hexagon6.png";

    public Base(int matX, int matY) {
        rX = matX;
        rY = matY;
        isEven(matX, matY);
        linearTransformationX(matX, matY);
        linearTransformationY(matX, matY);
        name = "BaseBlock";
        canMoveTo = false;
        image = new Rectangle(WIDTH, HEIGHT);
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(FILE_PATH)));
    }
}
