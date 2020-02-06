package kr.gemblo.gameBoard;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import kr.gemblo.enums.gameplay.GridElement;
import kr.gemblo.enums.gameplay.PlayerAction;
import kr.gemblo.extraScreens.GameOverScreen;
import kr.gemblo.extraScreens.InstructionStuff;
import kr.gemblo.extraScreens.Inventory;
import kr.gemblo.gridNodes.GridNode;
import kr.gemblo.gridNodes.PlayerBlock;
import kr.gemblo.gridNodes.Space;
import kr.gemblo.playerElements.Player;
import kr.gemblo.utils.ConstUtil.GameSceneCoordinatesEnum;
import kr.gemblo.utils.ModalUtil;

import java.util.Vector;

import static kr.gemblo.gridNodes.GridNode.RADIUS;
import static kr.gemblo.gridNodes.GridNode.ROOT3;

public class GameScene extends Pane {
    private GridBoard gameBoard;
    static GameScene instance;
    private Pane inventory;
    Player[] players;
    private boolean isSelected = false;
    private boolean blockOnBoard = false;
    private int selectedBlockNumber = 20; // 몇 번 블록이 선택되었는지 여기에 입력 사용시에는 players[turn].blocks[selectedBlockNumber]
    int turn = 0; // turn = turn % numberOfPlayers 이므로, 0~numberOfPlayers-1 까지
    int numberOfPlayers;

    int moveCheck = 0;

    int pointX = 10;
    int pointY = 10;

    int gameOver = 0;

    private int blockRow = 0; // 오른쪽에 내가 가진 블록 위치 행렬로 표시
    private int blockColumn = 0; // row 최대3 column 최대6

    Vector<GridNode> originGrid = new Vector<>(1, 1);

    private boolean placable;

    public void setPlacable() {
        this.placable = false;
        if (blockOnBoard) {
            for (int i = 0; i < originGrid.size(); i++) {
                if ((originGrid.get(i).getName().equals("StartPoint") && originGrid.get(i).getPlayerNumber() == turn + 1) || originGrid.get(i).getName().equals("PlacableBlock")) {
                    for (int j = 0; j < originGrid.size(); j++) {
                        if ((originGrid.get(j).getName().equals("Null") ||
                                (originGrid.get(j).getName().equals("StartPoint") && originGrid.get(i).getPlayerNumber() == turn + 1) ||
                                originGrid.get(j).getName().equals("PlacableBlock")) &&
                                gameBoard.NearCheck(gameBoard.grid, originGrid.get(j).originX, originGrid.get(j).originY, turn)) {
                            this.placable = true;
                        } else {
                            this.placable = false;
                            break;
                        }
                    }
                }
            }
        }
    }

    private GameScene(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        BackgroundGrid backgroundGrid = new BackgroundGrid();
        gameBoard = new GridBoard(GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), GameSceneCoordinatesEnum.SIZE_BOARD_TILE.get(), numberOfPlayers);
        instance = this;

        this.getChildren().add(backgroundGrid.gridPane); // 백그라운드 판 자식노드에 추가하고
        gameBoard.gridBoardEven.setLayoutX(-400);
        gameBoard.gridBoardEven.setLayoutY(-550);
        this.getChildren().add(gameBoard.gridBoardEven); // 게임보드 자식에 추가
        gameBoard.gridBoardOdd.setLayoutX(-393);
        gameBoard.gridBoardOdd.setLayoutY(-563);
        this.getChildren().add(gameBoard.gridBoardOdd);

        Pane instructionsPane = new InstructionStuff();
        this.getChildren().add(instructionsPane);

        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) players[i] = new Player(i + 1); // 일단 플레이어 수 만큼 Player추가

        inventory = new Inventory(players[turn], isSelected, selectedBlockNumber, blockRow, blockColumn);
        inventory.setLayoutX(650);
        inventory.setLayoutY(100);
        this.getChildren().add(inventory);
    }

    public static class Builder {
        private int playerNumber;

        public Builder player(int playerNumber) {
            this.playerNumber = playerNumber;
            return this;
        }

        public GameScene build() {
            return new GameScene(playerNumber);
        }

    }

    void showGameOver(int[] playerWhoWon) {
        for (int i = 0; i < gameBoard.grid.length; i++) {
            for (int j = 0; j < gameBoard.grid[i].length; j++) {
                gameBoard.grid[j][i] = new Space(i, j);
            }
        }
        players[turn].removeAllBlock();


        GameOverScreen gameOverScreen = new GameOverScreen(playerWhoWon);
        this.getChildren().add(gameOverScreen);
    }

    public void BoardUpdate() {
        this.getChildren().remove(inventory); // 일단 다 지우고
        this.getChildren().remove(gameBoard.gridBoardEven);
        this.getChildren().remove(gameBoard.gridBoardOdd);

        if (isSelected) {
            for (int i = 0; i < players[turn].getBlocks(selectedBlockNumber).getSize(); i++) {
                int x = pointX + (players[turn].getBlocks(selectedBlockNumber).screenX[i] + players[turn].getBlocks(selectedBlockNumber).screenY[i]) / 2;
                int y = pointY - (players[turn].getBlocks(selectedBlockNumber).screenX[i] - players[turn].getBlocks(selectedBlockNumber).screenY[i]) / 2;
                if (blockOnBoard) {
                    if (i == 0) {
                        for (int j = 0; j < originGrid.size(); j++)
                            gameBoard.grid[originGrid.get(j).originY][originGrid.get(j).originX] = originGrid.get(j);
                        originGrid.removeAllElements();
                        originGrid.setSize(0);
                    }
                    originGrid.add(gameBoard.grid[y][x]);
                    gameBoard.grid[y][x].originX = x;
                    gameBoard.grid[y][x].originY = y;
                } else {
                    originGrid.add(gameBoard.grid[y][x]);
                    gameBoard.grid[y][x].originX = x;
                    gameBoard.grid[y][x].originY = y;
                    if (i == players[turn].getBlocks(selectedBlockNumber).getSize() - 1) blockOnBoard = true;
                }
                gameBoard.grid[y][x] = GridElement.createPlayerGridNode(numberOfPlayers, turn + 1, x, y);
            }
            gameBoard.gridBoardEven = GenerateGridPaneEven(gameBoard.grid);
            gameBoard.gridBoardOdd = GenerateGridPaneOdd(gameBoard.grid);
        } else {
            gameBoard.gridBoardEven = GenerateGridPaneEven(gameBoard.grid);
            gameBoard.gridBoardOdd = GenerateGridPaneOdd(gameBoard.grid);
        }

        gameBoard.gridBoardEven.setLayoutX(-400);
        gameBoard.gridBoardEven.setLayoutY(-550);
        this.getChildren().add(gameBoard.gridBoardEven); // 게임보드 자식에 추가
        gameBoard.gridBoardOdd.setLayoutX(-393);
        gameBoard.gridBoardOdd.setLayoutY(-563);
        this.getChildren().add(gameBoard.gridBoardOdd);
        inventory = new Inventory(players[turn], isSelected, selectedBlockNumber, blockRow, blockColumn);
        inventory.setLayoutX(650);
        inventory.setLayoutY(100);
        this.getChildren().add(inventory); // 새로 인벤 만들어서 추가
    }

    public void onKeyPressed(KeyCode key) {
        if (KeyCode.ENTER.equals(key)) {
            setPlacable();
            if (placable) turnEnd(false);
        } // 엔터 누르면 턴 엔드
        else executeMove(PlayerAction.getActionByKeyCode(key));
        // 받은 키로 실행

        BoardUpdate();
    }

    private void tryBlockSelect(PlayerAction playerAction) {
        switch (playerAction) {
            case BLOCK_DOWN:
                if ((blockRow + 1) * 6 + blockColumn == selectedBlockNumber) {
                    if ((blockRow + 2) * 6 + blockColumn < players[turn].getBlocksSize()) blockRow = blockRow + 2;
                } else if ((blockRow + 1) * 6 + blockColumn < players[turn].getBlocksSize()) blockRow++;
                break;
            case BLOCK_LEFT:
                if (blockRow * 6 + (blockColumn - 1) == selectedBlockNumber) {
                    if (blockColumn - 2 >= 0) blockColumn = blockColumn - 2;
                } else if (blockColumn - 1 >= 0) blockColumn--;
                break;
            case BLOCK_RIGHT:
                if (blockRow * 6 + (blockColumn + 1) == selectedBlockNumber) {
                    if (blockRow * 6 + (blockColumn + 2) < players[turn].getBlocksSize() && blockColumn + 2 < 6)
                        blockColumn = blockColumn + 2;
                } else if (blockRow * 6 + (blockColumn + 1) < players[turn].getBlocksSize() && blockColumn + 1 < 6)
                    blockColumn++;
                break;
            case BLOCK_UP:
                if ((blockRow - 1) * 6 + blockColumn == selectedBlockNumber) {
                    if (blockRow - 2 >= 0) blockRow = blockRow - 2;
                } else if (blockRow - 1 >= 0) blockRow--;
                break;
            case BLOCK_SELECT:
                isSelected = true;
                selectedBlockNumber = blockRow * 6 + blockColumn;
                break;
        }
    }

    public void tryMovePoint(PlayerAction playerAction) {
        switch (playerAction) {
            case MOVE_DOWN:
                pointX++;
                pointY++;
                if (!BlockOutCheck())
                    executeMove(PlayerAction.MOVE_UP);
                break;
            case MOVE_RIGHT:
                pointX++;
                if (moveCheck == 0)
                    moveCheck++;
                else {
                    moveCheck--;
                    executeMove(PlayerAction.MOVE_UP);
                }
                if (!BlockOutCheck())
                    executeMove(PlayerAction.MOVE_LEFT);
                break;
            case MOVE_LEFT:
                pointX--;
                if (moveCheck == 1)
                    moveCheck--;
                else {
                    moveCheck++;
                    executeMove(PlayerAction.MOVE_DOWN);
                }
                if (!BlockOutCheck())
                    executeMove(PlayerAction.MOVE_RIGHT);
                break;
            case MOVE_UP:
                pointX--;
                pointY--;
                if (!BlockOutCheck())
                    executeMove(PlayerAction.MOVE_DOWN);
                break;
        }
    }

    private void executeMove(PlayerAction playerAction) {
        switch (playerAction) { // MOVE는 판 위에 선택 된 블록위치 움직이는 것, BLOCK은 블록 선택위해 움직이는 것
            case TURN_LEFT:
                if (isSelected) players[turn].getBlocks(selectedBlockNumber).tryTurnBlock(playerAction.TURN_LEFT);
                break;
            case MOVE_LEFT:
                if (isSelected) tryMovePoint(playerAction.MOVE_LEFT);
                break;
            case BLOCK_LEFT:
                tryBlockSelect(playerAction.BLOCK_LEFT);
                break;
            case TURN_RIGHT:
                if (isSelected) players[turn].getBlocks(selectedBlockNumber).tryTurnBlock(playerAction.TURN_RIGHT);
                break;
            case MOVE_RIGHT:
                if (isSelected) tryMovePoint(playerAction.MOVE_RIGHT);
                break;
            case BLOCK_RIGHT:
                tryBlockSelect(playerAction.BLOCK_RIGHT);
                break;
            case MOVE_UP:
                if (isSelected) tryMovePoint(playerAction.MOVE_UP);
                break;
            case BLOCK_UP:
                tryBlockSelect(playerAction.BLOCK_UP);
                break;
            case MOVE_DOWN:
                if (isSelected) tryMovePoint(playerAction.MOVE_DOWN);
                break;
            case BLOCK_DOWN:
                tryBlockSelect(playerAction.BLOCK_DOWN);
                break;
            case BLOCK_SELECT:
                tryBlockSelect(playerAction.BLOCK_SELECT);
                break;
            case REVERSE:
                if (isSelected) players[turn].getBlocks(selectedBlockNumber).tryTurnBlock(playerAction.REVERSE);
                break;
            case TURN_PASS:
                TurnPass();
                break;
            case DEFAULT:
                break;
        }
    }

    void TurnPass() {
        ModalUtil.confirm(
                "Pass",
                "Did you really want to pass?",
                () -> turnEnd(true));
    }

    protected boolean BlockOutCheck() {
        Block temp = players[turn].getBlocks(selectedBlockNumber);
        for (int i = 0; i < temp.getSize(); i++) {
            if (gameBoard.grid[pointX + temp.pointX[i]][pointY + temp.pointY[i]].getName().equals("Space"))
                return false;
        }
        return true;
    }

    private void turnEnd(boolean pass) {
        blockOnBoard = false;
        pointX = 10;
        pointY = 10;
        moveCheck = 0;
        blockColumn = 0;
        blockRow = 0;

        for (int i = 0; i < originGrid.size(); i++) {
            if (!pass) gameBoard.grid[originGrid.get(i).originY][originGrid.get(i).originX]
                    = GridElement.createPlayerGridNode(numberOfPlayers, (numberOfPlayers) + 1 + turn, originGrid.get(i).originX, originGrid.get(i).originY);
            else gameBoard.grid[originGrid.get(i).originY][originGrid.get(i).originX]
                    = gameBoard.grid[originGrid.get(i).originY][originGrid.get(i).originX] = originGrid.get(i);
        }

        originGrid.removeAllElements();
        originGrid.setSize(0);

        if (isSelected && !pass) players[turn].removeBlock(selectedBlockNumber);

        selectedBlockNumber = 20;
        isSelected = false;

        turn = (turn + 1) % numberOfPlayers;

        gameBoard.PlacableEraser(gameBoard.grid);

        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 33; j++) {
                if (gameBoard.grid[i][j].getName().equals("PlacedBlock") && gameBoard.grid[i][j].getPlayerNumber() == (turn + 1) % (numberOfPlayers + 1))
                    gameBoard.BoardCheck(gameBoard.grid, j, i, turn, numberOfPlayers);
            }
        }

        if (pass) gameOver++;
        else gameOver = 0;

        if (gameOver == numberOfPlayers) {
            showGameOver(CheckWinner(players));
        }
    }

    GridPane GenerateGridPaneEven(GridNode[][] gridNodes) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 33; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints((2 * RADIUS) - 2));
            gridPane.getColumnConstraints().add(new ColumnConstraints(RADIUS - 2));
            gridPane.getRowConstraints().add(new RowConstraints((ROOT3 * RADIUS) - 1));
        }

        setPlacable();

        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 33; j++) {
                if (!placable && gridNodes[i][j].getName().equals("PlayerBlock"))
                    gridNodes[i][j] = new PlayerBlock(j, i, turn + 1, true);

                if (gridNodes[i][j].getIsEven())
                    gridPane.add(gridNodes[i][j].getImage(), gridNodes[i][j].getGridX(), gridNodes[i][j].getGridY() / 2);
            }
        }

        return gridPane;
    }

    GridPane GenerateGridPaneOdd(GridNode[][] gridNodes) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 33; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(RADIUS - 2));
            gridPane.getColumnConstraints().add(new ColumnConstraints((2 * RADIUS) - 2));
            gridPane.getRowConstraints().add(new RowConstraints((ROOT3 * RADIUS) - 1));
        }

        setPlacable();

        for (int i = 0; i < 33; i++) {
            for (int j = 0; j < 33; j++) {
                if (!placable && gridNodes[i][j].getName().equals("PlayerBlock"))
                    gridNodes[i][j] = new PlayerBlock(j, i, turn + 1, true);

                if (!gridNodes[i][j].getIsEven())
                    gridPane.add(gridNodes[i][j].getImage(), gridNodes[i][j].getGridX(), (gridNodes[i][j].getGridY() + 1) / 2);
            }
        }

        return gridPane;
    }

    int[] CheckWinner(Player[] players) {
        int[] scoreSum = new int[numberOfPlayers];
        int[] remnantSum = new int[numberOfPlayers];
        int[] biggestBlockSize = new int[numberOfPlayers];
        Vector<Integer> minPlayer = new Vector<>(1, 1);
        Vector<Integer> biggestPlayer = new Vector<>(1, 1);
        Vector<Integer> highScorePlayer = new Vector<>(1, 1);

        int min = 100;

        int[] winner = new int[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            scoreSum[i] = 0;
            remnantSum[i] = 0;
            biggestBlockSize[i] = 0;
            for (int j = 0; j < players[i].getBlocksSize(); j++) {
                remnantSum[i] = remnantSum[i] + players[i].getBlocks(j).getSize();
                if (biggestBlockSize[i] < players[i].getBlocks(j).getSize())
                    biggestBlockSize[i] = players[i].getBlocks(j).getSize();
                scoreSum[i] = scoreSum[i] + players[i].getBlocks(j).score;
            }
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            if (remnantSum[i] < min) {
                minPlayer.removeAllElements();
                minPlayer.setSize(0);
                minPlayer.add(i);
                min = remnantSum[i];
            } else if (remnantSum[i] == min) minPlayer.add(i);
        }

        if (minPlayer.size() > 1) {
            int minSize = 6;
            for (int i = 0; i < minPlayer.size(); i++) {
                if (biggestBlockSize[minPlayer.get(i)] < minSize) {
                    minSize = biggestBlockSize[minPlayer.get(i)];
                    biggestPlayer.removeAllElements();
                    biggestPlayer.setSize(0);
                    biggestPlayer.add(minPlayer.get(i));
                } else if (biggestBlockSize[minPlayer.get(i)] == minSize) biggestPlayer.add(minPlayer.get(i));
            }
        } else {
            winner = new int[1];
            winner[0] = minPlayer.get(0) + 1;
        }

        if (biggestPlayer.size() > 1) {
            int score = 0;
            for (int i = 0; i < biggestPlayer.size(); i++) {
                if (scoreSum[biggestPlayer.get(i)] > score) {
                    score = scoreSum[biggestPlayer.get(i)];
                    highScorePlayer.removeAllElements();
                    highScorePlayer.setSize(0);
                    highScorePlayer.add(biggestPlayer.get(i));
                } else if (scoreSum[biggestPlayer.get(i)] == score) highScorePlayer.add(biggestPlayer.get(i));
            }
            winner = new int[highScorePlayer.size()];
            for (int i = 0; i < highScorePlayer.size(); i++) winner[i] = highScorePlayer.get(i) + 1;
        } else if (biggestPlayer.size() == 1) {
            winner = new int[1];
            winner[0] = biggestPlayer.get(0) + 1;
        }

        return winner;
    }
}
