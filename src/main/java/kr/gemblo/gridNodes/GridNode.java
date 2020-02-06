package kr.gemblo.gridNodes;

import javafx.scene.Node;

import static java.lang.Math.round;

public abstract class GridNode {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 26;
    public static final int ZEROTOMATRIX = 17;
    public static final int RADIUS = 15;
    public static final double ROOT3 = 1.7320508;


    int gridX; // 기저벡터가 (1,0) (0,1)인 일반 좌표계
    int gridY;
    public int rX; //
    public int rY;
    public int originX;
    public int originY;
    int playerNumber;
    protected String name;

    private double screenX;
    private double screenY;

    boolean canMoveTo;

    boolean isEven;

    protected Node image;

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getGridX() {
        return gridX;
    }

    public boolean getIsEven() {
        return isEven;
    }

    public int getGridY() {
        return gridY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getImage() {
        return image;
    }

    public void setImage(Node image) {
        this.image = image;
    }

    public void linearTransformationX(int matX, int matY) {
        double temp = 1.5 * RADIUS * (matX - matY);
        this.screenX = (int) round(temp);
        this.gridX = matX - matY + (2 * ZEROTOMATRIX);
    }

    public void linearTransformationY(int matX, int matY) {
        double temp = ROOT3 * RADIUS / 2 * (matX + matY);
        this.screenY = (int) round(temp);
        this.gridY = matX + matY + (2 * ZEROTOMATRIX);
    }

    public void isEven(int matX, int matY) {
        if ((matX - matY) % 2 == 0) this.isEven = true;
        else this.isEven = false;
    } // x가 짝수인지 홀수인지 판단, 짝수면 true, 홀수면 false리턴

}
