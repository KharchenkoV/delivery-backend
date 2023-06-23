package com.example.delivery.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingCreateDto {
    private Integer tableNumber;
    private Integer count;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
}
