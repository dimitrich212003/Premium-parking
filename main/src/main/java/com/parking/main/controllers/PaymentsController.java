package com.parking.main.controllers;

import com.parking.main.services.DTO.PaymentsDTO;
import com.parking.main.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {
    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/api/payment/create")
    public ResponseEntity<EntityModel<PaymentsDTO>> createPayment (@RequestBody PaymentsDTO paymentsDTO) {
        PaymentsDTO createPayment = paymentService.createPayment(paymentsDTO);

        EntityModel<PaymentsDTO> paymentModel = null;

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentModel);
    }

    //TODO 1. доделать контроллер в формате HATEOAS
    //     2. добавить полную конфигурацию консьюмера для grpc-client и протестировать получение объекта оплаты
    //     3. Заменить все константные значения для rabbit-a на объявленные константы
}
