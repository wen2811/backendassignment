package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Customer;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    public Long id;
    @NotNull
    @Min(value= 1)
    public double amount;
    @NotNull
    @FutureOrPresent
    public LocalDate invoicedate;

    public Booking booking;
    public Customer customer;


}
