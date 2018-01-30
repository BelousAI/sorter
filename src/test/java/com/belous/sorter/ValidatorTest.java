package com.belous.sorter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

public class ValidatorTest {
    private String[] args;
    private String fileNameIn;
    private File fileIn;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void runBefore() {
        fileNameIn = "in.txt";
        fileIn = new File(fileNameIn);
        try {
            fileIn.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void runAfter() {
        args = null;
        fileIn.delete();
        fileIn = null;
        fileNameIn = null;
    }

    @Test
    public void testCheck_ThrowsExceptionWhenNoArgs() {
        args = new String[] {};
        Validator valid = new Validator(args);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Введено неверное количество аргументов!");
        valid.check();
    }

    @Test
    public void testCheck_ThrowsExceptionWhenFileNotFound() {
        args = new String[] {"tmp.txt", "out.txt", "c", "d"};
        Validator valid = new Validator(args);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Входной файл не найден!");
        valid.check();
    }

    @Test
    public void testCheck_ThrowsExceptionWhenIncorrectThirdArg() {
        args = new String[] {fileNameIn, "out.txt", "c", "d"};
        Validator valid = new Validator(args);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Некорректный 3-й аргумент!");
        valid.check();
    }

    @Test
    public void testCheck_ThrowsExceptionWhenIncorrectFourthArg() {
        args = new String[] {fileNameIn, "out.txt", "-s", "d"};
        Validator valid = new Validator(args);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Некорректный 4-й аргумент!");
        valid.check();
    }

    @Test
    public void testCheck_ThrowsExceptionWhenIncorrectStrSortingMode() {
        args = new String[] {fileNameIn, "out.txt", "-s", "-d"};
        Validator valid = new Validator(args);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Некорректный режим сортировки строк!");
        valid.check();
    }

    @Test
    public void testCheck_DoesNotThrowException() {
        args = new String[] {fileNameIn, "out.txt", "-i", "-a"};
        Validator valid = new Validator(args);
        try {
            valid.check();
        }catch (IllegalArgumentException e) {
            fail();
        }
    }
}