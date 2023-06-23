package com.example.delivery.core.controller;

import com.example.delivery.service.BookingService;
import com.example.delivery.service.dto.BookingCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/booking")
public class BookingController {
    private final BookingService bookingService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createBooking(@RequestBody BookingCreateDto bookingCreateDto, Principal principal){
        bookingService.createBooking(bookingCreateDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
