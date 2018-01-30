package com.belous.sorter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Sorter {
    private boolean isStr;
    private boolean isAsc;
    static final StringBuilder INFO = new StringBuilder();

    static {
        Sorter.INFO.append("\nВведите через пробел 4 аргумента:\n")
                .append("1. путь к входному файлу;\n")
                .append("2. путь к выходному файлу;\n")
                .append("3. тип содержимого файла: -i (целые числа), -s (строки);\n")
                .append("4. режим сортировки: -а (по возрастанию), -d (по убыванию), " +
                        "для строк только -a;\n")
                .append("Внимание!!! Строки сортируются только в " +
                        "лексикографическом порядке (по кодам символов).\n");
    }

    Sorter(boolean isStr, boolean isAsc) {
        this.isStr = isStr;
        this.isAsc = isAsc;
    }

    void sortFileContent(String fileNameIn, String fileNameOut) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileNameIn));
            PrintWriter writer = new PrintWriter(new FileWriter(fileNameOut))) {

            List<?> list = isStr ? getSortedStrList(reader) : getSortedIntList(reader);
            writeToFile(list, writer);
        } catch (IOException e) {
            System.err.println("Ошибка!\nВозможные причины:\n" +
                    "- Входной файл не доступен для чтения.\n" +
                    "- Выходной файл защищен от записи.\n" + Sorter.INFO);
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка!\n- Входной файл содержит строки!\n" +
                    "Выбран тип содержимого файла: -i (целые числа).\n" + Sorter.INFO);
            System.exit(1);
        }
    }

    private List<Integer> getSortedIntList(BufferedReader br) throws IOException {
        ArrayList<Integer> intList = new ArrayList<>(100);
        String line;
        while ((line = br.readLine()) != null) {
            int num = Integer.parseInt(line);
            intList.add(num);
        }
        intList.trimToSize();

        Comparator<Integer> intComp = Integer::compare;
        if (isAsc) {
            sort(intList, intComp);
        } else {
            sort(intList, intComp.reversed());
        }
        return intList;
    }

    private List<String> getSortedStrList(BufferedReader br) throws IOException {
        ArrayList<String> strList = new ArrayList<>(100);
        String line;
        while ((line = br.readLine()) != null) {
            strList.add(line);
        }
        strList.trimToSize();

        Comparator<String> strComp = String::compareTo;
        sort(strList, strComp);
        return strList;
    }

    //Сортирует список методом вставок(Insertion sort)
    private <T> void sort(List<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(list.get(j - 1), list.get(j)) > 0) {
                    //Collections.swap(list, (j - 1), j);
                    T temp = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }

    private <T> void writeToFile(List<T> list, PrintWriter writer) {
        list.forEach(writer::println);
        System.out.println("Done.");
    }
}
