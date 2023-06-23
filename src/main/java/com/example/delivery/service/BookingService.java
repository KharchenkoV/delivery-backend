package com.example.delivery.service;

import com.example.delivery.service.dto.BookingCreateDto;

public interface BookingService {
    void createBooking(BookingCreateDto createDto, String email);
}
