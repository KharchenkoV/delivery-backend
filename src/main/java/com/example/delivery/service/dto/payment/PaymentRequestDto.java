package com.example.delivery.service.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDto {
    private Long orderId;
    private String name;
    private String currency;
    private String successUrl;
    private String cancelUrl;
    private BigDecimal amount;
    private long quantity;
}
