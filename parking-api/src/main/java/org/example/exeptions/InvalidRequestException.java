package org.example.exeptions;

public class InvalidRequestException extends RuntimeException {

    /**
     * Конструктор с указанием подробного сообщения.
     *
     * @param message подробное описание ошибки.
     */
    public InvalidRequestException(String message) {
        super("Некорректный запрос: " + message);
    }
}
