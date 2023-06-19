package com.example.delivery.core.controller;

import com.example.delivery.service.OrderService;
import com.example.delivery.service.dto.order.OrderCreateDto;
import com.example.delivery.service.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createOrder(Principal principal,
                                                  @RequestBody OrderCreateDto orderCreateDto){
        orderService.makeOrder(orderCreateDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/active/for/user")
    public ResponseEntity<List<OrderDto>> getActiveOrdersForUser(Principal principal){
        return  ResponseEntity.ok(orderService.getNotClosedOrdersByEmail(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.loadOrderById(id));
    }

    @GetMapping("/formed")
    public ResponseEntity<List<OrderDto>> getNewOrders(){
        return ResponseEntity.ok(orderService.getNewOrders());
    }

    @GetMapping("/approved")
    public ResponseEntity<List<OrderDto>> getApprovedOrders(){
        return ResponseEntity.ok(orderService.getApprovedOrders());
    }

    @PutMapping ("/approve/{id}")
    public ResponseEntity<HttpStatus> approveOrder(@PathVariable Long id){
        orderService.acceptOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping ("/send/{id}")
    public ResponseEntity<HttpStatus> sendOrder(@PathVariable Long id){
        orderService.sendOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping ("/cancel/{id}")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/pay/{id}")
    public ResponseEntity<HttpStatus> payOrder(@PathVariable Long id){
        orderService.payOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
