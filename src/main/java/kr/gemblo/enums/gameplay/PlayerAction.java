package kr.gemblo.enums.gameplay;

import javafx.scene.input.KeyCode;

public enum PlayerAction {
    TURN_PASS,
    TURN_LEFT,
    MOVE_LEFT,
    BLOCK_LEFT,
    TURN_RIGHT,
    MOVE_RIGHT,
    BLOCK_RIGHT,
    MOVE_UP,
    BLOCK_UP,
    MOVE_DOWN,
    BLOCK_DOWN,
    BLOCK_SELECT,
    REVERSE,
    DEFAULT;

    public static PlayerAction getActionByKeyCode(KeyCode key) {
        PlayerAction result = null;
        switch (key) {
            case Q:
                result = PlayerAction.TURN_LEFT;
                break;
            case W:
                result = PlayerAction.MOVE_UP;
                break;
            case A:
                result = PlayerAction.MOVE_LEFT;
                break;
            case S:
                result = PlayerAction.MOVE_DOWN;
                break;
            case D:
                result = PlayerAction.MOVE_RIGHT;
                break;
            case E:
                result = PlayerAction.TURN_RIGHT;
                break;
            case R:
                result = PlayerAction.REVERSE;
                break;
            case P:
                result = PlayerAction.TURN_PASS;
                break;
            case RIGHT:
                result = PlayerAction.BLOCK_RIGHT;
                break;
            case LEFT:
                result = PlayerAction.BLOCK_LEFT;
                break;
            case UP:
                result = PlayerAction.BLOCK_UP;
                break;
            case DOWN:
                result = PlayerAction.BLOCK_DOWN;
                break;
            case SPACE:
                result = PlayerAction.BLOCK_SELECT;
                break;
            default:
                result = PlayerAction.DEFAULT;
                break;
        }
        return result;
    }
}
