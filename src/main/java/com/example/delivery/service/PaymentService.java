package com.example.delivery.service;

import com.example.delivery.service.dto.payment.PaymentRequestDto;
import com.example.delivery.service.dto.payment.PaymentResponseDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface PaymentService {
    PaymentResponseDto createCharge(PaymentRequestDto paymentDto) throws StripeException;
}
