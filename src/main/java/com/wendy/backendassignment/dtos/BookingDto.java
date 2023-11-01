package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingDto {
    public Long id;
    public LocalDate date;
    public double totalAmount;
    public BookingStatus bookingStatus;

    public Long invoice;
    public List<Long> treatmentIds;

    public Long customerId;

    public UserDto user;

}
