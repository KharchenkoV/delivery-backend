package com.example.delivery.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Booking {
    @Id
    @GeneratedValue
    private Long id;
    private Integer tableNumber;
    private Integer count;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
