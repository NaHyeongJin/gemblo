package kr.gemblo.gridNodes;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.utils.MediaUtil;

public class BackGround extends GridNode {
    public BackGround(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.name = "BackGround";
        this.canMoveTo = false;
        this.image = new Rectangle(WIDTH * 40, HEIGHT * 35);
        ((Rectangle) image)
                .setFill(new ImagePattern(MediaUtil.createImage("/images/Woodboard.png")));
    }
}
