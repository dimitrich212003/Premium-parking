package com.parking.main.controllers;

import com.parking.main.mappers.impl.PaymentsReMapper;
import com.parking.main.services.DTO.PaymentsDTO;
import com.parking.main.services.PaymentService;
import org.example.controllers.PaymentsApi;
import org.example.dtos.request.PaymentsRequest;
import org.example.dtos.response.PaymentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PaymentsController implements PaymentsApi {
    private final PaymentService paymentService;
    private final PaymentsReMapper paymentsReMapper;

    @Autowired
    public PaymentsController(PaymentService paymentService, PaymentsReMapper paymentsReMapper) {
        this.paymentService = paymentService;
        this.paymentsReMapper = paymentsReMapper;
    }

    @Override
    public ResponseEntity<CollectionModel<PaymentsResponse>> getAllPayments() {
        List<PaymentsDTO> payments = paymentService.getAllPayments();

        List<PaymentsResponse> paymentResponses = payments.stream()
                .map(paymentsReMapper::toResponse)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(PaymentsController.class).getAllPayments()).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(paymentResponses, selfLink));
    }


    @Override
    public ResponseEntity<EntityModel<PaymentsResponse>> getPaymentById(@PathVariable UUID id) {
        PaymentsDTO payment = paymentService.getPaymentById(id);
        PaymentsResponse response = paymentsReMapper.toResponse(payment);

        EntityModel<PaymentsResponse> paymentModel = EntityModel.of(response,
                linkTo(methodOn(PaymentsController.class).getPaymentById(id)).withSelfRel(),
                linkTo(methodOn(PaymentsController.class).getAllPayments()).withRel("allPayments"),
                linkTo(methodOn(PaymentsController.class).deletePayment(id)).withRel("deletePaymentById")
        );

        return ResponseEntity.ok(paymentModel);
    }

    @Override
    public ResponseEntity<EntityModel<PaymentsResponse>> createPayment(@RequestBody PaymentsRequest paymentsRequest) {
        PaymentsDTO createdPayment = paymentService.createPayment(paymentsReMapper.toDto(paymentsRequest));
        PaymentsResponse response = paymentsReMapper.toResponse(createdPayment);

        EntityModel<PaymentsResponse> paymentModel = EntityModel.of(response,
                linkTo(methodOn(PaymentsController.class).getPaymentById(createdPayment.getId())).withRel("paymentById"),
                linkTo(methodOn(PaymentsController.class).getAllPayments()).withRel("allPayments"),
                linkTo(methodOn(PaymentsController.class).deletePayment(createdPayment.getId())).withRel("deletePaymentById")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentModel);
    }

    @Override
    public ResponseEntity<EntityModel<PaymentsResponse>> updatePayment(@PathVariable UUID id, @RequestBody PaymentsRequest paymentsRequest) {
        PaymentsDTO updatedPayment = paymentService.updatePayment(id, paymentsReMapper.toDto(paymentsRequest));
        PaymentsResponse response = paymentsReMapper.toResponse(updatedPayment);

        EntityModel<PaymentsResponse> paymentModel = EntityModel.of(response,
                linkTo(methodOn(PaymentsController.class).getPaymentById(updatedPayment.getId())).withRel("paymentById"),
                linkTo(methodOn(PaymentsController.class).getAllPayments()).withRel("allPayments"),
                linkTo(methodOn(PaymentsController.class).deletePayment(updatedPayment.getId())).withRel("deletePaymentById")
        );

        return ResponseEntity.ok(paymentModel);
    }

    @Override
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}


