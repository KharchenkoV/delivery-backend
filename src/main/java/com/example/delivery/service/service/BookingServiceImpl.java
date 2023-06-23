package com.example.delivery.service.service;

import com.example.delivery.dao.entity.Booking;
import com.example.delivery.dao.entity.User;
import com.example.delivery.dao.repository.BookingRepository;
import com.example.delivery.service.BookingService;
import com.example.delivery.service.UserService;
import com.example.delivery.service.dto.BookingCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserService userService;
    private final BookingRepository bookingRepository;
    @Override
    public void createBooking(BookingCreateDto createDto, String email) {
        if (createDto.getStartDate().isAfter(createDto.getFinishDate())){
            throw new RuntimeException("Finish date is before start date");
        }
        User user = userService.loadUserByEmail(email);
        Booking booking = Booking.builder()
                .count(createDto.getCount())
                .tableNumber(createDto.getTableNumber())
                .startDate(createDto.getStartDate())
                .finishDate(createDto.getFinishDate())
                .user(user)
                .build();
        bookingRepository.save(booking);
    }
}
