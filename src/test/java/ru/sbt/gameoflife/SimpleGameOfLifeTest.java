package ru.sbt.gameoflife;

import org.junit.Test;
import ru.sbt.gameoflife.simple.SimpleGameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mintas on 12/10/2017.
 */
public class SimpleGameOfLifeTest {

    @Test
    public void testSimpleImplementationGameOfLife() throws Exception {
        GameOfLife gameOfLife = new SimpleGameOfLife();
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
