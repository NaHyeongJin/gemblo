package kr.gemblo.gameBoard;


import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import kr.gemblo.enums.gameplay.GridElement;
import kr.gemblo.gridNodes.GridNode;
import kr.gemblo.gridNodes.Null;
import kr.gemblo.utils.MapFileReadUtil;

import static kr.gemblo.gridNodes.GridNode.RADIUS;
import static kr.gemblo.gridNodes.GridNode.ROOT3;

public class GridBoard {

    public GridNode[][] grid;
    public GridPane gridBoardEven; // 짝수
    public GridPane gridBoardOdd; // 홀수
    public static GridBoard instance;

    GridBoard(int sizeX, int sizeY, int numberOfPlayers) {
        this.generateBoard(sizeX, sizeY, numberOfPlayers);
        instance = this;
    }

    private void generateBoard(int sizeX, int sizeY, int numberOfPlayers) {
        this.grid = new GridNode[sizeY][sizeX];
        this.gridBoardEven = new GridPane();
        this.gridBoardOdd = new GridPane();
        for (int i = 0; i < 33; i++) {
            gridBoardEven.getColumnConstraints().add(new ColumnConstraints((2 * RADIUS) - 2));
            gridBoardEven.getColumnConstraints().add(new ColumnConstraints(RADIUS - 2));
            gridBoardOdd.getColumnConstraints().add(new ColumnConstraints(RADIUS - 2));
            gridBoardOdd.getColumnConstraints().add(new ColumnConstraints((2 * RADIUS) - 2));
            gridBoardEven.getRowConstraints().add(new RowConstraints((ROOT3 * RADIUS) - 1));
            gridBoardOdd.getRowConstraints().add(new RowConstraints((ROOT3 * RADIUS) - 1));
        }

        int[][] imageToPrint = MapFileReadUtil.getMapAry(numberOfPlayers);

        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 33; j++) {
                GridNode nodeToAdd = GridElement.createGridNode(imageToPrint[i][j], j, i);
                if (nodeToAdd.getIsEven())
                    this.gridBoardEven.add(nodeToAdd.getImage(), nodeToAdd.getGridX(), nodeToAdd.getGridY() / 2);
                else
                    this.gridBoardOdd.add(nodeToAdd.getImage(), nodeToAdd.getGridX(), (nodeToAdd.getGridY() + 1) / 2);
                this.grid[i][j] = nodeToAdd;
            }
        } // text에서 숫자대로 판 생성 후 grid에 각 노드 저장
    }

    public static void BoardCheck(GridNode[][] grid, int x, int y, int turn, int numberOfPlayers) {
        if (y + 1 < grid.length && y >= 0 &&
                x + 1 < grid.length && x >= 0) {
            BoardChecker(grid, x, y, x + 1, y + 1, x + 1, y, turn, numberOfPlayers);
            BoardChecker(grid, x, y, x, y + 1, x + 1, y + 1, turn, numberOfPlayers);
        }
        if (y - 1 < grid.length && y - 1 >= 0 &&
                x + 1 < grid.length && x >= 0)
            BoardChecker(grid, x, y, x + 1, y, x, y - 1, turn, numberOfPlayers);
        if (y - 1 < grid.length && y - 1 >= 0 &&
                x - 1 < grid.length && x - 1 >= 0) {
            BoardChecker(grid, x, y, x, y - 1, x - 1, y - 1, turn, numberOfPlayers);
            BoardChecker(grid, x, y, x - 1, y - 1, x - 1, y, turn, numberOfPlayers);
        }
        if (y + 1 < grid.length && y >= 0 &&
                x - 1 < grid.length && x - 1 >= 0)
            BoardChecker(grid, x, y, x - 1, y, x, y + 1, turn, numberOfPlayers);
    } // block 6+3 ~ 2*6+2 // 이미 둔 곳 -> NOP+turn
    // block 0 // 빈 칸
    // 1 == null 아무것도 없는 칸 허공
    // grid == 2 * 6+3 ~ 3*6+2 // 둘 수 있는 곳 -> 2*NOP+turn
    // 얘는 grid[y][x] 기준으로 주변에 둘 수 있는 곳 찾아줌

    protected static void BoardChecker(GridNode[][] grid, int x, int y, int checkX1, int checkY1, int checkX2, int checkY2, int turn, int numberOfPlayers) {
        if ((!grid[checkY1][checkX1].getName().equals((grid[checkY2][checkX2]).getName()) &&
                !((grid[checkY1][checkX1].getName().equals("PlacedBlock") || grid[checkY1][checkX1].getName().equals("PlacableBlock")) && grid[checkY1][checkX1].getPlayerNumber() == (turn + 1) % (numberOfPlayers + 1)) &&
                !((grid[checkY2][checkX2].getName().equals("PlacedBlock") || grid[checkY2][checkX2].getName().equals("PlacableBlock")) && grid[checkY2][checkX2].getPlayerNumber() == (turn + 1) % (numberOfPlayers + 1))) ||
                (grid[checkY1][checkX1].getName().equals((grid[checkY2][checkX2].getName())) && grid[checkY1][checkX1].getName().equals("Null"))) {
            if (grid[checkY1 + checkY2 - y][checkX1 + checkX2 - x].getName().equals("Null") &&
                    NearCheck(grid, checkX1 + checkX2 - x, checkY1 + checkY2 - y, turn))
                grid[checkY1 + checkY2 - y][checkX1 + checkX2 - x] =
                        GridElement.createPlayerGridNode(numberOfPlayers, (2 * numberOfPlayers) + 1 + turn, checkX1 + checkX2 - x, checkY1 + checkY2 - y);
        }
    }

    public static void PlacableEraser(GridNode[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].getName().equals("PlacableBlock")) grid[i][j] = new Null(j, i);
            }
        }
    }

    public static boolean NearCheck(GridNode[][] grid, int x, int y, int turn) {
        if (x + 1 < 33 && y + 1 < 33) {
            if (grid[y + 1][x + 1].getName().equals("PlacedBlock") && grid[y + 1][x + 1].getPlayerNumber() == turn + 1)
                return false;
            else if (grid[y][x + 1].getName().equals("PlacedBlock") && grid[y][x + 1].getPlayerNumber() == turn + 1)
                return false;
            else if (grid[y + 1][x].getName().equals("PlacedBlock") && grid[y + 1][x].getPlayerNumber() == turn + 1)
                return false;
        }
        if (x + 1 < 33 && y - 1 >= 0) {
            if (grid[y - 1][x].getName().equals("PlacedBlock") && grid[y - 1][x].getPlayerNumber() == turn + 1)
                return false;
        }
        if (x - 1 >= 0 && y + 1 < 33) {
            if (grid[y][x - 1].getName().equals("PlacedBlock") && grid[y][x - 1].getPlayerNumber() == turn + 1)
                return false;
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            if (grid[y - 1][x - 1].getName().equals("PlacedBlock") && grid[y - 1][x - 1].getPlayerNumber() == turn + 1)
                return false;
        }
        return true;
    } // grid[y][x]기준으로 6방향 체크하고 같은 블록 있으면 false; 없으면 true true면 Placable이고 false면 아님
}
