package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    public Long id;
    public double amount;
    public LocalDate invoicedate;

    public Booking booking;
    public Customer customer;


}
