package com.example.delivery.service;

import com.example.delivery.dao.entity.Order;
import com.example.delivery.service.dto.bucket.BucketDto;
import com.example.delivery.service.dto.order.OrderCreateDto;
import com.example.delivery.service.dto.order.OrderDto;

import java.util.List;

public interface OrderService {
    void makeOrder(OrderCreateDto orderCreateDto, String email);
    List<OrderDto> getAllOrders();
    List<OrderDto> getNotClosedOrdersByEmail(String email);
    void cancelOrder(Long orderId);
    void acceptOrder(Long orderId);
    void sendOrder(Long orderId);
    void payOrder(Long orderId);
    OrderDto loadOrderById(Long orderId);
    List<OrderDto> getNewOrders();
    List<OrderDto> getApprovedOrders();
}
