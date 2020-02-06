package kr.gemblo.playerElements;

import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import kr.gemblo.gameBoard.Block;
import kr.gemblo.utils.MediaUtil;

import java.util.Vector;

import static kr.gemblo.gridNodes.GridNode.HEIGHT;
import static kr.gemblo.gridNodes.GridNode.WIDTH;
import static kr.gemblo.utils.BlockFileReadUtil.getBlockAry;

public class Player {
    private final static String FILE_PATH = "/images/Tiles/Hexagon%s.png";
    protected Node image;

    private int playerNumber;
    private Vector<Block> blocks;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.blocks = getBlockAry();
        image = new Rectangle(WIDTH, HEIGHT);
        ((Rectangle) image).setFill(new ImagePattern(MediaUtil.createImage(String.format(FILE_PATH, playerNumber))));
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Block getBlocks(int blockNumber) {
        return blocks.elementAt(blockNumber);
    }

    public int getBlocksSize() {
        return blocks.size();
    }

    public Node getImage() {
        return image;
    }

    public void removeBlock(int i) {
        blocks.remove(i);
    }

    public void removeAllBlock() {
        blocks.removeAllElements();
        blocks.setSize(0);
    }
}
