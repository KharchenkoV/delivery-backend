package com.example.delivery.core.controller;

import com.example.delivery.service.PaymentService;
import com.example.delivery.service.dto.payment.PaymentRequestDto;
import com.example.delivery.service.dto.payment.PaymentResponseDto;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping
    public ResponseEntity<PaymentResponseDto> paymentWithCheckoutPage(@RequestBody PaymentRequestDto request) throws StripeException {
        return ResponseEntity.ok(paymentService.createCharge(request));
    }
}
