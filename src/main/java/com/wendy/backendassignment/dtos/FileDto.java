package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Customer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FileDto {
    public Long id;
    public String filename;
    public String filetype;
    public LocalDate date;
    public byte[] data;
    public String mimeType;
    public String description;
    public Customer customer;
}
