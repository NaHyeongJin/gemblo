package kr.gemblo.utils;

import java.util.ResourceBundle;

public class ConstUtil {

    private static ResourceBundle RESOURCE_BUNDLE = null;

    public static void setResourceBundle(ResourceBundle resourceBundle) {
        if (RESOURCE_BUNDLE != null) {
            throw new IllegalStateException("Already set");
        }
        RESOURCE_BUNDLE = resourceBundle;
    }

    public interface hasDoubleSize {
        double get();
    }

    public static String getRbString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

    public enum DisplaySizeEnum implements hasDoubleSize {
        EXTERNAL_FRAME_W(900),
        EXTERNAL_FRAME_H(600),
        TITLE_W(EXTERNAL_FRAME_W.get()),
        TITLE_H(80),
        MENU_ITEM_W(300),
        MENU_ITEM_H(80),
        MENU_IMAGE_W(180),
        MENU_IMAGE_H(450),
        MODAL_MIN_W(200),
        MODAL_MIN_H(100);

        double size;

        DisplaySizeEnum(double size) {
            this.size = size;
        }

        public double get() {
            return this.size;
        }
    }

    public interface hasIntSize {
        int get();
    }

    public enum GridBoardSizeEnum implements hasIntSize {
        BOARD_POSITION_X(182),
        BOARD_POSITION_Y(50),
        TILE_SIZE(60),
        SPACE_TARGET_NODE_Y(10),
        SPACE_TARGET_NODE_X(30),
        TILE_CORRECTION_COORDINATE(1);

        int size;

        GridBoardSizeEnum(int size) {
            this.size = size;
        }

        public int get() {
            return this.size;
        }
    }

    public enum GameSceneCoordinatesEnum implements hasIntSize {
        SIZE_BOARD_TILE(33);

        int size;

        GameSceneCoordinatesEnum(int size) {
            this.size = size;
        }

        public int get() {
            return this.size;
        }
    }

    public static final String APP_NAME = "GEMBLO";
    public static final int[] PLAYER_NUMBERS = new int[]{4, 5, 6};
    public static final int DEFAULT_PLAYER_NUMBER_INDEX = 0;
}
