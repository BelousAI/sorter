package com.belous.sorter;

import java.io.File;

/* Использование данного класса позволяет произвести валидацию параметров
 * командной строки с возможностью:
 * 1. замены выброса искючений на создание уведомлений(Notification);
 * 2. выведения пользователю сразу всех ошибок, чтобы тот исправил их за одну
 * операцию.
 */
class Validator {
    private String[] args;
    private String fileNameIn;
    private String fileNameOut;
    private boolean isStr;
    private boolean isAsc;

    Validator(String[] args) {
        this.args = args;
    }

    void check() {
        if (validateAll().hasErrors()) {
            throw new IllegalArgumentException("Ошибка!\n" + validateAll().errorMessage()
                    + Sorter.INFO);
        }
    }

    //Аккумулирует все выявленные ошибки
    private Notification validateAll() {
        Notification note = new Notification();
        validateCorrectNumOfArgs(note);
        validateFileIn(note);
        validateFileOut(note);
        validateIsStr(note);
        validateIsAsc(note);
        validateStrSortingMode(note);
        return note;
    }
    private void validateCorrectNumOfArgs(Notification note) {
        if (args.length != 4) {
            note.addError("Введено неверное количество аргументов!\n");
        }
    }

    private void validateFileIn(Notification note) {
        try {
            File file = new File(args[0]);
            fileNameIn = file.getAbsolutePath();
            if (!file.isFile()) {
                note.addError("Входной файл не найден!\n");
            }
        } catch (IndexOutOfBoundsException e) {
            note.addError("Не введен 1-й аргумент!\n", e);
        }
    }

    private void validateFileOut(Notification note) {
        try {
            File file = new File(args[1]);
            fileNameOut = file.getAbsolutePath();
        } catch (IndexOutOfBoundsException e) {
            note.addError("Не введен 2-й аргумент!\n", e);
        }
    }

    private void validateIsStr(Notification note) {
        try {
            switch (args[2]) {
                case "-s":
                    isStr = true;
                    break;
                case "-i":
                    isStr = false;
                    break;
                default:
                    note.addError("Некорректный 3-й аргумент!\n");
            }
        } catch (IndexOutOfBoundsException e) {
            note.addError("Не введен 3-й аргумент!\n", e);
        }
    }

    private void validateIsAsc(Notification note) {
        try {
            switch (args[3]) {
                case "-a":
                    isAsc = true;
                    break;
                case "-d":
                    isAsc = false;
                    break;
                default:
                    note.addError("Некорректный 4-й аргумент!\n");
            }
        } catch (IndexOutOfBoundsException e) {
            note.addError("Не введен 4-й аргумент!\n", e);
        }
    }

    private void validateStrSortingMode(Notification note) {
        if (isStr && !isAsc) {
            note.addError("Некорректный режим сортировки строк!\n");
        }
    }

    String getFileNameIn() {
        return fileNameIn;
    }

    String getFileNameOut() {
        return fileNameOut;
    }

    boolean getIsStr() {
        return isStr;
    }

    boolean getIsAsc() {
        return isAsc;
    }
}
