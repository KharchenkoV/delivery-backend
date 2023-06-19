package com.example.delivery.dao.repository;

import com.example.delivery.dao.entity.Order;
import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where (o.status <> 'CLOSED') and (o.status <> 'CANCELED') and (o.user = :user) ")
    List<Order> getActiveOrdersByUser(User user);
    @Query("select o from  Order o order by o.created desc ")
    List<Order> orderHistory();
    List<Order> getOrdersByStatusOrderByCreatedAsc(OrderStatus status);
}
