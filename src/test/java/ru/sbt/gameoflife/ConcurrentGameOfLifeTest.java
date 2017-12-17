package ru.sbt.gameoflife;

import org.junit.Test;
import ru.sbt.gameoflife.concurrent.v2.ConcurrentGameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by SBT-Ovchinnikov-DK on 13.12.2017.
 */
public class ConcurrentGameOfLifeTest {
    @Test
    public void testConcurrentImplementationGameOfLife() throws Exception {
        GameOfLife gameOfLife = new ConcurrentGameOfLife(16, 4);
        testOneGame(gameOfLife, "src/test/resources/input.txt", "src/test/resources/output.txt");
    }

    @Test
    public void testConcurrentImplementationGameOfLife100() throws Exception {
        GameOfLife gameOfLife = new ConcurrentGameOfLife(16, 4);
        testOneGame(gameOfLife, "src/test/resources/input100.txt", "src/test/resources/output100.txt");
    }

    @Test
    public void testConcurrentImplementationGameOfLife1000() throws Exception {
        GameOfLife gameOfLife = new ConcurrentGameOfLife(16, 4);
        testOneGame(gameOfLife, "src/test/resources/input1000.txt", "src/test/resources/output1000.txt");
    }

    public void testConcurrentImplementationGameOfLife10000() throws Exception {
        GameOfLife gameOfLife = new ConcurrentGameOfLife(16, 4);
        testOneGame(gameOfLife, "src/test/resources/input10000.txt", "src/test/resources/output10000.txt");
    }

    private void testOneGame(GameOfLife gameOfLife, String inputFile, String expectedOutputFile) throws FileNotFoundException {
        List<String> result = gameOfLife.play(inputFile);
        List<String> expected = readFile(expectedOutputFile);
        assertEquals(expected, result);
    }


    private static List<String> readFile(String fileName) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<String>();
        Scanner scan = new Scanner(new File(fileName));
        while (scan.hasNextLine()) {
            lines.add(scan.nextLine());
        }
        scan.close();

        return lines;
    }
}
