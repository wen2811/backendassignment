package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.BookingStatus;
import com.wendy.backendassignment.models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDto {
    public Long id;
    public LocalDate date;
    public double totalAmount;
    public User employee;
    public BookingStatus bookingStatus;

}
