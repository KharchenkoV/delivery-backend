package com.example.delivery.service.service;

import com.example.delivery.dao.entity.Order;
import com.example.delivery.dao.entity.OrderDetails;
import com.example.delivery.dao.entity.Product;
import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.enums.OrderStatus;
import com.example.delivery.dao.enums.PaymentStatus;
import com.example.delivery.dao.repository.OrderDetailsRepository;
import com.example.delivery.dao.repository.OrderRepository;
import com.example.delivery.dao.repository.ProductRepository;
import com.example.delivery.dao.repository.UserRepository;
import com.example.delivery.service.BucketService;
import com.example.delivery.service.OrderService;
import com.example.delivery.service.UserService;
import com.example.delivery.service.dto.order.OrderCreateDto;
import com.example.delivery.service.dto.order.OrderDto;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final BucketService bucketService;
    private final OrderDetailsRepository orderDetailsRepository;
    @Override
    @Transactional
    public void makeOrder(OrderCreateDto orderCreateDto, String email) {
        User user = userService.loadUserByEmail(email);
        if (orderCreateDto.getOrderDetailsDtoList().isEmpty()){
            throw new RuntimeException("Bucket is empty");
        }
        AtomicReference<BigDecimal> sum = new AtomicReference<>(BigDecimal.ZERO);
        orderCreateDto.getOrderDetailsDtoList().forEach(orderDetailsDto -> {
            Product product = productRepository.findById(orderDetailsDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product is not found with id:" + orderDetailsDto.getProductId()));
            sum.set(sum.get().add(product.getPrice().multiply(BigDecimal.valueOf(orderDetailsDto.getProductAmount()))));
        });
        Order order = Order.builder()
                .user(user)
                .address(orderCreateDto.getAddress())
                .sum(sum.get())
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .paymentStatus(PaymentStatus.UNPAID)
                .status(OrderStatus.NEW)
                .build();
        orderRepository.save(order);

        orderCreateDto.getOrderDetailsDtoList().forEach(orderDetailsDto -> {
            Product product = productRepository.findById(orderDetailsDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product is not found with id:" + orderDetailsDto.getProductId()));
            OrderDetails orderDetails = OrderDetails.builder()
                    .product(product)
                    .order(order)
                    .amount(orderDetailsDto.getProductAmount())
                    .price(product.getPrice())
                    .build();
            orderDetailsRepository.save(orderDetails);
        });
        bucketService.cleanBucketByEmail(email);
    }

    private Order getOrderById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order is not found with id: " + orderId));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.orderHistory();
        return OrderDto.fromOrderList(orders);
    }

    @Override
    public List<OrderDto> getNotClosedOrdersByEmail(String email) {
        User user = userService.loadUserByEmail(email);
        List<Order> orders = orderRepository.getActiveOrdersByUser(user);
        return OrderDto.fromOrderList(orders);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.CANCELED);
        order.setUpdated(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public void acceptOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.APPROVED);
        order.setUpdated(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public void sendOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.ON_THE_WAY);
        order.setUpdated(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public void finishOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.CLOSED);
        order.setUpdated(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public void payOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setPaymentStatus(PaymentStatus.PAID);
        orderRepository.save(order);
    }

    @Override
    public OrderDto loadOrderById(Long orderId) {
        Order order = getOrderById(orderId);
        return OrderDto.fromOrder(order);
    }

    @Override
    public List<OrderDto> getNewOrders() {
        List<Order> orders = orderRepository.getOrdersByStatusOrderByCreatedAsc(OrderStatus.NEW);
        return OrderDto.fromOrderList(orders);
    }

    @Override
    public List<OrderDto> getApprovedOrders() {
        List<Order> orders = orderRepository.getOrdersByStatusOrderByCreatedAsc(OrderStatus.APPROVED);
        return OrderDto.fromOrderList(orders);
    }

}
