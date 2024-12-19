package org.example.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {

    private static final String ERROR_STATUS = "error";

    /**
     * Обработчик для исключения ResourceNotFoundException (ресурс не найден).
     *
     * @param e исключение
     * @return стандартизированный ответ с HTTP-статусом NOT_FOUND (404)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new StatusResponse(ERROR_STATUS, e.getMessage()));
    }

    /**
     * Обработчик для исключения InvalidRequestException (некорректный запрос).
     *
     * @param e исключение
     * @return стандартизированный ответ с HTTP-статусом BAD_REQUEST (400)
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<StatusResponse> handleInvalidRequest(InvalidRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new StatusResponse(ERROR_STATUS, e.getMessage()));
    }

    /**
     * Обработчик для исключения BookingAlreadyExistsException (бронирование уже существует).
     *
     * @param e исключение
     * @return стандартизированный ответ с HTTP-статусом CONFLICT (409)
     */
    @ExceptionHandler(BookingAlreadyExistsException.class)
    public ResponseEntity<StatusResponse> handleBookingAlreadyExists(BookingAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new StatusResponse(ERROR_STATUS, e.getMessage()));
    }

    /**
     * Обработчик для исключения PaymentFailedException (ошибка оплаты).
     *
     * @param e исключение
     * @return стандартизированный ответ с HTTP-статусом PAYMENT_REQUIRED (402)
     */
    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<StatusResponse> handlePaymentFailed(PaymentFailedException e) {
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(new StatusResponse(ERROR_STATUS, e.getMessage()));
    }

    /**
     * Обработчик для всех остальных исключений (глобальная обработка).
     *
     * @param e исключение
     * @return стандартизированный ответ с HTTP-статусом INTERNAL_SERVER_ERROR (500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusResponse> handleGlobalException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusResponse(ERROR_STATUS, "Внутренняя ошибка сервера: " + e.getMessage()));
    }
}