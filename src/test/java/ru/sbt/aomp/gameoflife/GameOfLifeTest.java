package ru.sbt.aomp.gameoflife;

import org.junit.Before;
import org.junit.Test;
import ru.sbt.aomp.gameoflife.GameOfLife;
import ru.sbt.aomp.gameoflife.simple_implementation.SimpleGameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mintas on 12/10/2017.
 */
public class GameOfLifeTest {
    GameOfLife gameOfLife;

    @Before
    public void init(){
        gameOfLife = new SimpleGameOfLife();
    }

    @Test
    public void testGame() throws Exception {
        testOneGame("input.txt", "output.txt");
    }

    private void testOneGame(String inputFile, String expectedOutputFile) throws FileNotFoundException {
        List<String> result = gameOfLife.play(inputFile);
        assertEquals(readFile(expectedOutputFile), result);
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
