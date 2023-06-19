package com.example.delivery.service;

import com.example.delivery.dao.entity.User;
import com.example.delivery.service.dto.user.UserDto;

import java.util.List;

public interface UserService {
    User loadUserByEmail(String email);
    UserDto getUserById(Long id);
    List<UserDto> getUsers();
}
