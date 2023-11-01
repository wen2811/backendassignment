package com.wendy.backendassignment.dtos;

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
    public Long customerId;
}
