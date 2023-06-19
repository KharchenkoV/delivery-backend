package com.example.delivery.service.service;

import com.example.delivery.core.configuration.JwtService;
import com.example.delivery.dao.entity.Token;
import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.enums.Role;
import com.example.delivery.dao.enums.TokenType;
import com.example.delivery.dao.repository.TokenRepository;
import com.example.delivery.dao.repository.UserRepository;
import com.example.delivery.service.AuthenticationService;
import com.example.delivery.service.dto.auth.AuthenticationRequestDto;
import com.example.delivery.service.dto.auth.AuthenticationResponseDto;
import com.example.delivery.service.dto.auth.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponseDto register(RegisterRequestDto request) {
        if(userRepository.existsUserByEmail(request.getEmail())){
            throw new RuntimeException("User is already exist with email: " + request.getEmail());
        }
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponseDto.builder()
                .role(user.getRole().name())
                .user(user.getEmail())
                .token(jwtToken)
                .id(user.getId())
                .build();
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDto.builder()
                .role(user.getRole().name())
                .user(user.getEmail())
                .token(jwtToken)
                .id(user.getId())
                .build();
    }

    private void saveUserToken(User user, String jwtToken){
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user){
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
