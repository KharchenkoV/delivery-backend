package com.example.delivery.service;

import com.example.delivery.service.dto.auth.AuthenticationRequestDto;
import com.example.delivery.service.dto.auth.AuthenticationResponseDto;
import com.example.delivery.service.dto.auth.RegisterRequestDto;

public interface AuthenticationService {
    public AuthenticationResponseDto register(RegisterRequestDto request);
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
}
