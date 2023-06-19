package com.example.delivery.service.service;

import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.enums.Role;
import com.example.delivery.dao.repository.UserRepository;
import com.example.delivery.service.UserService;
import com.example.delivery.service.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.fromUser(user);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAllByRole(Role.USER);
        return UserDto.fromUserList(users);
    }
}
