package ru.sbt.aomp.gameoflife.complex;

/**
 * Created by SBT-Ovchinnikov-DK on 13.12.2017.
 */
public class GameExecutorForV1 implements Runnable {
    private byte[][] oldField;
    private byte[][] newField;
    private int n;
    private int blockSize;
    private int execCellX;
    private int execCellY;


    public GameExecutorForV1(byte[][] oldField, byte[][] newField, int fieldSize, int blockSize, int x, int y) {
        this.oldField = oldField;
        this.newField = newField;
        this.n = fieldSize;
        this.blockSize = blockSize;
        this.execCellX = x;
        this.execCellY = y;
    }

    @Override
    public void run() {
        int x_start = n / blockSize * execCellX;
        int x_end = n / blockSize * (execCellX + 1);
        int y_start = n / blockSize * execCellY;
        int y_end = n / blockSize * (execCellY + 1);

        for (int i = x_start; i < x_end; i++) {
            for (int j = y_start; j < y_end; j++) {
                int liveSum = 0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        int fieldValue = oldField[((i + k - 1) % n + n) % n][((j + l - 1) % n + n) % n];
                        if (fieldValue == 1) {
                            liveSum++;
                        }
                    }
                }
                if (oldField[i][j] == 1) {
                    if (liveSum == 2 || liveSum == 3) {
                        newField[i][j] = 1;
                    }
                    else {
                        newField[i][j] = 0;
                    }
                }
                else {
                    if (liveSum == 3) {
                        newField[i][j] = 1;
                    }
                    else {
                        newField[i][j] = 0;
                    }
                }
            }
        }
    }
}
