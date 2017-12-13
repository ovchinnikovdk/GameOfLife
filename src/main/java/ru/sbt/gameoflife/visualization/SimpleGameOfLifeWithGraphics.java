package ru.sbt.gameoflife.visualization;

import ru.sbt.gameoflife.simple.SimpleGameOfLife;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by SBT-Ovchinnikov-DK on 13.12.2017.
 * Визуализация однопоточной игры Game of Life, необходима для отладки игры
 * Помимо выполнения игры на каждом шаге сохраняет изображение поля на данный момент в папку images/
 */
public class SimpleGameOfLifeWithGraphics extends SimpleGameOfLife {
    @Override
    public List<String> play(String inputFile) {
        field = readFile(inputFile);
        if (field == null) {
            return null;
        }
        drawAndSaveImage();
        while (step < m) {
            doStep();
            drawAndSaveImage();
        }
        return byteArrayToString(field);
    }

    /*
    * drawAndSaveImage() - рисует текущее состояние field в файл images/step<N>.jpg,
    * увеличивая изображение в scale раз.
    * */
    private void drawAndSaveImage() {
        int scale = 10;
        BufferedImage bufferedImage = new BufferedImage(n * scale, n * scale, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int colorWhite = (255 << 16) | (255 << 8) | 255;
                for (int k = 0; k < scale; k++)
                    for (int l = 0; l < scale; l++)
                        bufferedImage.setRGB(i * scale + k, j * scale + l, field[i][j] == 1 ? colorWhite : 0);
            }
        }
        try {
            ImageIO.write(bufferedImage, "jpg", new File("images/step" + step + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
