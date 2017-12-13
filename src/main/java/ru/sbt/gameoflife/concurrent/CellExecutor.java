package ru.sbt.gameoflife.concurrent;

/**
 * Created by SBT-Ovchinnikov-DK on 13.12.2017.
 */
public class CellExecutor implements Runnable {
    private byte[][] oldField;
    private byte[][] newField;
    private int n;
    private int blockNum;
    private int execCellX;
    private int execCellY;


    public CellExecutor(byte[][] oldField, byte[][] newField, int fieldSize, int blockNum, int x, int y) {
        this.oldField = oldField;
        this.newField = newField;
        this.n = fieldSize;
        this.blockNum = blockNum;
        this.execCellX = x;
        this.execCellY = y;
    }

    @Override
    public void run() {
        int x_start = n / blockNum * execCellX;
        int x_end = n / blockNum * (execCellX + 1) + (n % blockNum > 0 && (execCellX + 1) == blockNum ? n % blockNum : 0);
        int y_start = n / blockNum * execCellY;
        int y_end = n / blockNum * (execCellY + 1) + (n % blockNum > 0 && (execCellY + 1) == blockNum ? n % blockNum : 0);
        for (int i = x_start; i < x_end; i++) {
            for (int j = y_start; j < y_end; j++) {
                int liveSum = 0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (k != 1 || l != 1) {
                            int fieldValue = oldField[((i + k - 1) % n + n) % n][((j + l - 1) % n + n) % n];
                            if (fieldValue == 1) {
                                liveSum++;
                            }
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
