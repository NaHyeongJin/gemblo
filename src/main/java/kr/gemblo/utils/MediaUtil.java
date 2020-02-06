package kr.gemblo.utils;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class MediaUtil {
    private MediaUtil() {
    }

    public static Image createImage(String filePath) {
        Image result = new Image(MediaUtil.class.getResourceAsStream("/kr/gemblo" + filePath));
        if (result.getException() != null)
            result.getException().printStackTrace();

        return result;
    }
}
