package com.example.delivery.dao.repository;

import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);
    List<User> findAllByRole(Role role);
}
