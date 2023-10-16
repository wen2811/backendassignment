package com.wendy.backendassignment.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InvoiceDto {
    public Long id;
    public double amount;
    public Date invoicedate;

}
