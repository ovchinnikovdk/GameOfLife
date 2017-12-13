package ru.sbt.gameoflife.simple;

import ru.sbt.gameoflife.GameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SBT-Ovchinnikov-DK on 12.12.2017.
 * Простая реализация игры Game of Life в один поток и с одной матрицей.
 */
public class SimpleGameOfLife implements GameOfLife {
    protected int n;
    protected int m;
    protected int step;
    protected byte[][] field;


    public List<String> play(String inputFile) {
        field = readFile(inputFile);
        if (field == null) {
            return null;
        }

        while (step < m) {
            doStep();
        }


        return byteArrayToString(field);
    }

    /*
    * doStep() - Вопроизводит один шаг игры
    * Вокруг каждой клетки считается количество живых клеток на предыдущем шаге
    * 1) Если вокруг клетки 2 или 3 живые клетки и текущая клетка жива, то она остаётся жить
    * 2) Если вокруг вокруг 3 клетки живы и сама клетка мертва, то там появляется жизнь
    * 3) Во всех остальных случаях клетки считаются умирают.
    * */
    protected void doStep(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int liveSum = 0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (k != 1 || l != 1) {
                            int fieldValue = field[((i + k - 1) % n + n) % n][((j + l - 1) % n + n) % n];
                            if (fieldValue == CellType.LIVE.getValue()
                                    || fieldValue == CellType.NOW_DEAD_WAS_LIVE.getValue()
                                    || fieldValue == CellType.NOW_LIVE_WAS_LIVE.getValue()) {
                                liveSum++;
                            }
                        }
                    }
                }
                if (liveSum == 2 && field[i][j] == CellType.LIVE.getValue()) {
                    field[i][j] = CellType.NOW_LIVE_WAS_LIVE.getValue();
                } else if (liveSum == 3) {
                    if (field[i][j] == CellType.DEAD.getValue()) {
                        field[i][j] = CellType.NOW_LIVE_WAS_DEAD.getValue();
                    }
                    else {
                        field[i][j] = CellType.NOW_LIVE_WAS_LIVE.getValue();
                    }
                } else if (liveSum > 3 || liveSum < 2) {
                    if (field[i][j] == CellType.LIVE.getValue()) {
                        if (field[i][j] == CellType.LIVE.getValue()) {
                            field[i][j] = CellType.NOW_DEAD_WAS_LIVE.getValue();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (field[i][j] == CellType.NOW_LIVE_WAS_LIVE.getValue() || field[i][j] == CellType.NOW_LIVE_WAS_DEAD.getValue()) {
                    field[i][j] = 1;
                }
                else {
                    field[i][j] = 0;
                }
            }
        }
        step++;
    }

    /*
    * readFile(String fileName) - вспомогательная функция для загрузки начального поля игры из файла.
    * */
    protected byte[][] readFile(String inputFile) {
        List<String> lines = new ArrayList<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (scanner != null) {
            if (scanner.hasNextLine()) {
                String paramLine = scanner.nextLine();
                String[] params = paramLine.split("\\s+");
                if (params.length != 2) {
                    return null;
                }
                n = Integer.valueOf(params[0]);
                m = Integer.valueOf(params[1]);
            }
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        }
        byte[][] result = new byte[n][n];
        for (int i = 0; i < lines.size(); i++) {
            String[] chars = lines.get(i).split("");
            if (chars.length != n) {
                return null;
            }
            for (int j = 0; j < n; j++) {
                result[i][j] = Byte.valueOf(chars[j]);
            }
        }
        return result;
    }

    /*
    * byteArrayToString(byte[][] matrix) - вспомогательная функция для конвертации поля игры в строки
    * */
    protected List<String> byteArrayToString(byte[][] matrix) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < n; j++) {
                builder.append(String.valueOf(matrix[i][j]));
            }
            result.add(builder.toString());
        }
        return result;
    }
}
