package ru.sbt.gameoflife.concurrent.v2;

import ru.sbt.gameoflife.GameOfLife;
import ru.sbt.gameoflife.concurrent.CellExecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* Created by dmitry on 14.12.17
*/
public class ConcurrentGameOfLife implements GameOfLife {
    private int gridSize;
    private byte[][] firstField;
    private byte[][] secondField;
    private int n;
    private int m;
    private int step;
    private ExecutorsPool pool;

    public ConcurrentGameOfLife(int nThreads, int gridSize) {
        this.gridSize = gridSize;
        this.step = 0;
        this.pool = new ExecutorsPool(nThreads);
    }

    @Override
    public List<String> play(String inputFile) {
        firstField = readFile(inputFile);
        secondField = new byte[n][n];
        if (firstField == null) {
            return null;
        }
        byte [][] lastField = firstField;
        while (step < m) {
            if (lastField == firstField) {
                lastField = doStep(firstField, secondField);
            }
            else {
                lastField = doStep(secondField, firstField);
            }
        }
        pool.shutdown();
        return byteArrayToString(lastField);
    }

    private byte[][] doStep(byte[][] oldField, byte[][] newField){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                pool.execute(new CellExecutor(oldField, newField, n, gridSize, i, j));
            }
        }
        pool.waitAllTasks();
        step++;
        return newField;
    }


    /*
    * readFile(String fileName) - вспомогательная функция для загрузки начального поля игры из файла.
    * */
    protected byte[][] readFile(String inputFile) {
        List<String> lines = new ArrayList<>();
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
