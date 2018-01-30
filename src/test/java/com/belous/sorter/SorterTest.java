package com.belous.sorter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SorterTest {
    private static File fileIn;
    private static File fileOut;
    private static File expFileOut;
    private static List<String> strList;
    private static List<Integer> intList;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void runBefore() {
        fileIn = new File("in.txt");
        fileOut = new File("out.txt");
        expFileOut = new File("expOut.txt");
        strList = Arrays.asList("red", "green", "black",
                "yellow", "white", "gray", "blue");
        intList = Arrays.asList(2, 4, 3, -1, 5, -7, 6);
    }

    @After
    public void runAfter() {
        fileIn.delete();
        fileOut.delete();
        expFileOut.delete();
        fileIn = null;
        fileOut = null;
        expFileOut = null;
        strList = null;
        intList = null;
    }

    private static <T> void createAndFillFileIn(List<T> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileIn))) {
            if (fileIn.exists()) {
                fileIn.createNewFile();
            }
            list.forEach(writer::println);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> void createAndFillExpFileOut(List<T> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(expFileOut))) {
            list.forEach(writer::println);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean compareFiles(File a, File b) {
        // если размеры файлов не равны, содержимое тоже не совпадет
        if (a.length() != b.length()) {
            return false;
        }
        try(FileReader readerA = new FileReader(a);
            FileReader readerB = new FileReader(b)) {
            int byteA;
            int byteB;
            while ((byteA = readerA.read()) > 0) {
                byteB = readerB.read();
                if (byteA != byteB) {
                    return false;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Test
    public void testSort_IntegersIntoAscOrder() {
        SorterTest.createAndFillFileIn(intList);
        Collections.sort(intList);
        SorterTest.createAndFillExpFileOut(intList);

        Sorter test = new Sorter(false, true);
        test.sortFileContent(fileIn.getName(), fileOut.getName());

        assertTrue(SorterTest.compareFiles(fileOut, expFileOut));
    }

    @Test
    public void testSort_IntegersIntoDescOrder() {
        SorterTest.createAndFillFileIn(intList);
        intList.sort(Collections.reverseOrder());
        SorterTest.createAndFillExpFileOut(intList);

        Sorter test = new Sorter(false, false);
        test.sortFileContent(fileIn.getName(), fileOut.getName());

        assertTrue(SorterTest.compareFiles(fileOut, expFileOut));
    }

    @Test
    public void testSort_StringsIntoAscOrder() {
        SorterTest.createAndFillFileIn(strList);
        Collections.sort(strList);
        SorterTest.createAndFillExpFileOut(strList);

        Sorter test = new Sorter(true, true);
        test.sortFileContent(fileIn.getName(), fileOut.getName());

        assertTrue(SorterTest.compareFiles(fileOut, expFileOut));
    }
}