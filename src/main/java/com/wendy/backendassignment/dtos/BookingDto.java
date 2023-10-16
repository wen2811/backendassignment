package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.*;
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
    public User employee;
    public BookingStatus bookingStatus;
    public Invoice invoice;
    public List<BookingTreatment> bookingTreatments;
    public Customer customer;

}
