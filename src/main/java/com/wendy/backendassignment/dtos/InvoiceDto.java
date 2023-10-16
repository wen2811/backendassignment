package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvoiceDto {
    public Long id;
    public double amount;
    public Date invoicedate;

    public Booking booking;
    public Customer customer;

}
