package kr.gemblo.enums.gameplay;

import kr.gemblo.gridNodes.*;

import java.util.Arrays;
import java.util.Optional;

public enum GridElement {
    NULL(1),
    SPACE(0),
    BASE(2),
    PLAYER1START(3),
    PLAYER2START(4),
    PLAYER3START(5),
    PLAYER4START(6),
    PLAYER5START(7),
    PLAYER6START(8);

    private int letter;

    GridElement(int letter) {
        this.letter = letter;
    }

    public static GridNode createGridNode(int letter, int positionX, int positionY) {
        Optional<GridElement> gridElementOpt = Arrays.stream(values())
                .filter(gridElement -> gridElement.letter == letter)
                .findFirst();
        switch (gridElementOpt.orElseThrow(IllegalStateException::new)) {
            case NULL:
                return new Null(positionX, positionY);
            case SPACE:
                return new Space(positionX, positionY);
            case BASE:
                return new Base(positionX, positionY);
            case PLAYER1START:
                return new PlayerStart(positionX, positionY, 1);
            case PLAYER2START:
                return new PlayerStart(positionX, positionY, 2);
            case PLAYER3START:
                return new PlayerStart(positionX, positionY, 3);
            case PLAYER4START:
                return new PlayerStart(positionX, positionY, 4);
            case PLAYER5START:
                return new PlayerStart(positionX, positionY, 5);
            case PLAYER6START:
                return new PlayerStart(positionX, positionY, 6);
        }
        throw new IllegalStateException();
    }

    public static GridNode createPlayerGridNode(int numberOfPlayers, int playerNumber, int positionX, int positionY) {
        if (playerNumber < numberOfPlayers + 1) return new PlayerBlock(positionX, positionY, playerNumber);
        else if (playerNumber < numberOfPlayers * 2 + 1)
            return new PlacedBlock(positionX, positionY, playerNumber, numberOfPlayers);
        else if (playerNumber < numberOfPlayers * 3 + 1)
            return new PlacableBlock(positionX, positionY, playerNumber, numberOfPlayers);
        else return null;
    }

}