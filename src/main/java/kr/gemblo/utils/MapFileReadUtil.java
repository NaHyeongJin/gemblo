package kr.gemblo.utils;

import kr.gemblo.GembloApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MapFileReadUtil {
    private static final String FILE_PATH = "maps/map%s.txt";

    private MapFileReadUtil() {
    }

    public static int[][] getMapAry(int numberOfPlayers) {
        int[][] map = new int[33][33];
        try (InputStream is = GembloApp.class.getResourceAsStream(String.format(FILE_PATH, numberOfPlayers));
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            // Read the txt of the maps
            // and get if it is:
            // map == 0 // null 빈 공간(보드 판 내의 빈 공간)
            // map == 1 // space 빈 공간(아예 표시하지 않는 부분)
            // map == 2 // 고정 블록(4, 5인용에서 기본적으로 고정시키는 부분)
            // map == 3 ~ numberOfPlayers+2 // Start Point
            // map == playerNumber+3 ~ 2*playerNumber+2 // 이미 둔 곳
            // map == 2 * playerNumber+3 ~ 3*playerNumber+2 // 둘 수 있는 곳
            // 어차피 파일 읽을 때(기본 보드 읽어들일 때)에는 0, 1, 2, 3~NOP+2까지만 표시되기 때문에 2자리일 고민 할 필요 없음
            // 그냥 NOP = 6 으로 고정하고, 플레이어가 적어지면 그냥 그거까지만 쓰는걸로, 456인용이여도 다 똑같이 숫자적용 범위만 =6으로계산

            // Read the next line of the file
            String line;
            for (int i = 0; i < map.length; i++) {
                line = br.readLine();
                String[] temp;
                temp = line.split("");
                for (int j = 0; j < map[i].length; j++) {
                    map[i][j] = Integer.parseInt(temp[j]);
                }
            }
        } catch (IOException ioE) {
            ioE.printStackTrace();
            throw new RuntimeException(ioE);
        }
        return map;
    }
}
