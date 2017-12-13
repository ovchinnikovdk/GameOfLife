package ru.sbt.aomp.gameoflife;

import org.junit.Test;
import ru.sbt.aomp.gameoflife.complex.MultithreadingGameOfLifeV1;
import ru.sbt.aomp.gameoflife.simple.SimpleGameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by SBT-Ovchinnikov-DK on 13.12.2017.
 */
public class ComplexGameOfLifeTest {
    @Test
    public void testSimpleImplementationGameOfLife() throws Exception {
        GameOfLife gameOfLife = new MultithreadingGameOfLifeV1(4);
        testOneGame(gameOfLife, "src/test/resources/input100.txt", "src/test/resources/output100.txt");
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
