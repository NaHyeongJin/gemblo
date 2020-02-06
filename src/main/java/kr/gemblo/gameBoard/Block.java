package kr.gemblo.gameBoard;

import kr.gemblo.enums.gameplay.PlayerAction;

import static java.lang.Math.round;
import static kr.gemblo.gridNodes.GridNode.RADIUS;
import static kr.gemblo.gridNodes.GridNode.ROOT3;


public class Block {
    public int[] pointX;
    public int[] pointY;
    public int[] screenX;
    public int[] screenY;
    public int[] gridX;
    public int[] gridY;
    public int score;

    public Block(int[] x, int[] y, int score) {
        this.pointX = x;
        this.pointY = y;
        this.screenX = new int[x.length];
        this.screenY = new int[y.length];
        this.gridX = new int[x.length];
        this.gridY = new int[y.length];
        this.score = score;
        linearTransformationX(pointX, pointY);
        linearTransformationY(pointX, pointY);
    }

    public void linearTransformationX(int[] matX, int[] matY) {
        for (int i = 0; i < matX.length; i++) {
            double temp = 1.5 * RADIUS * (matX[i] - matY[i]);
            this.screenX[i] = (int) round(temp / 23);
            this.gridX[i] = matX[i] - matY[i] + 2;
        }
    }

    public void linearTransformationY(int[] matX, int[] matY) {
        for (int i = 0; i < matX.length; i++) {
            double temp = ROOT3 * RADIUS / 2 * (matX[i] + matY[i]);
            this.screenY[i] = (int) round(temp / 13);
            this.gridY[i] = matX[i] + matY[i] + 4;
        }
    }

    public int getSize() {
        return pointX.length;
    }

    public void tryTurnBlock(PlayerAction playerAction) {
// TURN_LEFT 1 -1 TURN_RIGHT 0 1
//           1  0           -1 1
        int[] tempX = pointX.clone();
        int[] tempY = pointY.clone();
        for (int i = 0; i < tempX.length; i++) {
            switch (playerAction) {
                case TURN_LEFT:
                    pointX[i] = tempX[i] - tempY[i];
                    pointY[i] = tempX[i];
                    break;
                case TURN_RIGHT:
                    pointX[i] = tempY[i];
                    pointY[i] = tempY[i] - tempX[i];
                    break;
                case REVERSE:
                    pointX[i] = tempY[i];
                    pointY[i] = tempX[i];
                    break;
            }
        }
        linearTransformationX(pointX, pointY);
        linearTransformationY(pointX, pointY);
    }
}
