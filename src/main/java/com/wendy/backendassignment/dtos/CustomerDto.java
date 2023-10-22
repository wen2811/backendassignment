package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.File;
import com.wendy.backendassignment.models.Invoice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDto {
    public Long id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public List<Booking> bookingList;

    public List<Invoice> invoices;
    public List<File> fileList;


}
