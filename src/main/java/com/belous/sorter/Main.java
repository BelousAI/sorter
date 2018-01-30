package com.belous.sorter;

public class Main {
    public static void main(String[] args) {
        try {
            Validator valid = new Validator(args);
            valid.check();

            boolean isStr = valid.getIsStr();
            boolean isAsc = valid.getIsAsc();
            Sorter sorter = new Sorter(isStr, isAsc);

            String fileNameIn = valid.getFileNameIn();
            String fileNameOut = valid.getFileNameOut();
            sorter.sortFileContent(fileNameIn, fileNameOut);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
