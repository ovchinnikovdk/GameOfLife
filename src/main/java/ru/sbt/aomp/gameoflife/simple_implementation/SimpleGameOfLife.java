package ru.sbt.aomp.gameoflife.simple_implementation;

import ru.sbt.aomp.gameoflife.GameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SBT-Ovchinnikov-DK on 12.12.2017.
 */
public class SimpleGameOfLife implements GameOfLife {
    private int n;
    private int m;
    private byte[][] field;


    public List<String> play(String inputFile) {
        field = readFile(inputFile);
        if (field == null) {
            return null;
        }
        for (int step = 0; step < m; step++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int liveSum = 0;
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (k != 1 && l != 1) {
                                int fieldValue = field[((i + k - 1) % n + n) % n][((j + l - 1) % n + n) % n];
                                if (fieldValue == 1 || fieldValue == 3) {
                                    liveSum++;
                                }
                            }
                        }
                    }
                    if (liveSum == 2 && field[i][j] == 1) {
                        field[i][j] = 3;
                    } else if (liveSum == 3 && field[i][j] == 0) {
                        field[i][j] = 2;
                    } else if (liveSum > 3 || liveSum < 2) {
                        field[i][j] = 0;
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (field[i][j] == 2 || field[i][j] == 3) {
                        field[i][j] = 1;
                    }
                }
            }

        }

        List<String> result = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < n; j++) {
                builder.append(String.valueOf(field[i][j]));
            }
            result.add(builder.toString());
        }
        return result;
    }

    private byte[][] readFile(String inputFile) {
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
}
