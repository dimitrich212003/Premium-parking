package org.example.exeptions;

public class PaymentFailedException extends RuntimeException {

    /**
     * Конструктор с указанием подробного сообщения.
     *
     * @param reason причина сбоя оплаты.
     */
    public PaymentFailedException(String reason) {
        super("Ошибка при обработке оплаты: " + reason);
    }
}
