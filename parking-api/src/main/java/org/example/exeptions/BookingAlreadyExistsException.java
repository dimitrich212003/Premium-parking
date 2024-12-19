package org.example.exeptions;

public class BookingAlreadyExistsException extends RuntimeException {

    /**
     * Конструктор с указанием подробного сообщения.
     *
     * @param userId идентификатор пользователя.
     */
    public BookingAlreadyExistsException(Object userId) {
        super("Бронирование для пользователя " + userId + " на парковочное место " + parkingSlotId + " уже существует.");
    }
}
