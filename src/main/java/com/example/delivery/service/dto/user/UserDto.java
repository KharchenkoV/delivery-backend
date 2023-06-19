package com.example.delivery.service.dto.user;

import com.example.delivery.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;

    public static List<UserDto> fromUserList(List<User> users){
        return users.stream().map(user -> {
            return UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .build();
        }).collect(Collectors.toList());
    }
    public static UserDto fromUser(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }
}
