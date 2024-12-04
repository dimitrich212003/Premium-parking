package com.parking.main.mappers.impl;

import com.parking.main.mappers.Mapper;
import com.parking.main.models.Bookings;
import com.parking.main.models.Payment;
import com.parking.main.repositories.BookingsRepository;
import com.parking.main.services.DTO.PaymentsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper implements Mapper<Payment, PaymentsDTO> {

    private final ModelMapper modelMapper;

    private final BookingsRepository bookingsRepository;

    @Autowired
    public PaymentMapper(ModelMapper modelMapper, BookingsRepository bookingsRepository) {
        this.modelMapper = modelMapper;
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public Payment toModel(PaymentsDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        if(dto.getBooking() != null) {
            Bookings booking = bookingsRepository.findById(dto.getBooking()).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id"));
            payment.setBooking(booking);
        }
        return payment;
    }

    @Override
    public PaymentsDTO toDTO(Payment payment) {
        PaymentsDTO dto = modelMapper.map(payment, PaymentsDTO.class);
        if (payment.getBooking() != null) {
            dto.setBooking(payment.getBooking().getId());
        }
        return dto;
    }
}
