package com.example.delivery.service.service;

import com.example.delivery.service.OrderService;
import com.example.delivery.service.PaymentService;
import com.example.delivery.service.dto.payment.PaymentRequestDto;
import com.example.delivery.service.dto.payment.PaymentResponseDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderService orderService;
    @Value("${stripe.apiKey}")
    private String apiKey;
    @Override
    @Transactional
    public PaymentResponseDto createCharge(PaymentRequestDto paymentDto) throws StripeException {
        Stripe.apiKey = apiKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://hocalhost:8080/")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(paymentDto.getQuantity())
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(paymentDto.getCurrency())
                                .setUnitAmountDecimal(paymentDto.getAmount())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(paymentDto.getName())
                                        .build())
                                .build())
                        .build())
                .build();
        orderService.payOrder(paymentDto.getOrderId());
        Session session = Session.create(params);
        return new PaymentResponseDto(session.getId());
    }
}
