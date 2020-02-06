package kr.gemblo.utils;

import kr.gemblo.GembloApp;
import kr.gemblo.gameBoard.Block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class BlockFileReadUtil {
    private static final String FILE_PATH = "blocks/block%s.txt";

    private BlockFileReadUtil() {
    }

    public static Vector<Block> getBlockAry() {
        int[] blockX;
        int[] blockY;
        String[] temp;
        Vector<Block> blocks = new Vector<Block>(18, 1);
        for (int i = 0; i < 18; i++) {
            try (InputStream is = GembloApp.class.getResourceAsStream(String.format(FILE_PATH, i + 1));
                 BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                line = br.readLine(); // x축
                temp = line.split(" ");
                blockX = new int[temp.length];
                for (int j = 0; j < temp.length; j++) blockX[j] = Integer.parseInt(temp[j]);
                line = br.readLine(); // y축
                temp = line.split(" ");
                blockY = new int[temp.length];
                for (int j = 0; j < temp.length; j++) blockY[j] = Integer.parseInt(temp[j]);
                line = br.readLine(); // score
                int score = Integer.parseInt(line);
                blocks.add(i, new Block(blockX, blockY, score));
            } catch (IOException ioE) {
                ioE.printStackTrace();
                throw new RuntimeException(ioE);
            }
        }
        return blocks;
    }
}
