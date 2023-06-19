package com.example.delivery.service.dto.order;

import com.example.delivery.dao.entity.Order;
import com.example.delivery.dao.entity.OrderDetails;
import com.example.delivery.dao.entity.Product;
import com.example.delivery.dao.enums.OrderStatus;
import com.example.delivery.dao.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm, dd MMMM yyyy")
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm, dd MMMM yyyy")
    private  LocalDateTime updated;
    private BigDecimal sum;
    private Long userId;
    private String phone;
    private String address;
    private PaymentStatus paymentStatus;
    private OrderStatus status;
    private Set<OrderDetailsDto> details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class OrderDetailsDto{
        private Long id;
        private Product product;
        private BigDecimal price;
        private Integer amount;
        public static Set<OrderDetailsDto> fromOrderDetailsList(Set<OrderDetails> details){
            return  details.stream()
                    .map(detail -> {
                        return OrderDetailsDto.builder()
                                .id(detail.getId())
                                .price(detail.getPrice())
                                .amount(detail.getAmount())
                                .product(detail.getProduct())
                                .build();
                    }).collect(Collectors.toSet());
        }
    }

    public static List<OrderDto> fromOrderList(List<Order> orders){
        return orders.stream()
                .map(order -> { return OrderDto
                        .builder()
                        .id(order.getId())
                        .userId(order.getUser().getId())
                        .address(order.getAddress())
                        .sum(order.getSum())
                        .phone(order.getUser().getPhone())
                        .created(order.getCreated())
                        .updated(order.getUpdated())
                        .paymentStatus(order.getPaymentStatus())
                        .status(order.getStatus())
                        .details(OrderDetailsDto.fromOrderDetailsList(order.getDetails()))
                        .build();
                }).collect(Collectors.toList());
    }


    public static OrderDto fromOrder(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .address(order.getAddress())
                .userId(order.getUser().getId())
                .sum(order.getSum())
                .phone(order.getUser().getPhone())
                .created(order.getCreated())
                .updated(order.getUpdated())
                .paymentStatus(order.getPaymentStatus())
                .status(order.getStatus())
                .details(OrderDetailsDto.fromOrderDetailsList(order.getDetails()))
                .build();
    }
}
