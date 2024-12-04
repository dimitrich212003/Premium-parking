package com.parking.main.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class ParkingMessageListener {
    @RabbitListener(queues = "bookingQueue")
    public void receiveBookingMessage(String message) {
        System.out.println("Получено сообщение о резервировании парковочного места: " + message);
    }

    @RabbitListener(queues = "bookingQueue")
    public void receivePaymentConfirmationMessage(String message) {
        System.out.println("Получено сообщение об успешной оплате парковочного места: " + message);
    }
}
