package com.parking.grpcclient.util;

import com.parking.grpcclient.model.Payment;
import com.premium.grpcclient.PaymentCheck;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPayment(PaymentCheck.Payment payment);

    PaymentCheck.Payment toProtoPayment(Payment payment);
}
