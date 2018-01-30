package com.belous.sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* Объект данного класса собирает ошибки при каждом проваленном
 * акте валидации.*/
class Notification {
    private List<Error> errors = new ArrayList<>();

    void addError(String message) {
        addError(message, null);
    }

    void addError(String message, Exception e) {
        errors.add(new Error(message, e));
    }

    String errorMessage() {
        return errors.stream()
                .map(e -> e.message)
                .collect(Collectors.joining());
    }

    boolean hasErrors() {
        return !errors.isEmpty();
    }

    private static class Error {
        String message;
        Exception cause;

        private Error(String message, Exception cause) {
            this.message = message;
            this.cause = cause;
        }
    }
}
