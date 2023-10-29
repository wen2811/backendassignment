package com.wendy.backendassignment.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {
    public Long id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    //public List<Long> bookingList;

    public List<Long> invoices;
    public List<Long> fileList;


}


