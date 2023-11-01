package com.wendy.backendassignment.dtos;

import jakarta.validation.constraints.Min;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDto {
    public Long id;
    @NotNull
    @Min(value= 1)
    public double amount;

    public LocalDate invoicedate;
    public Long bookingId;
    public Long customerId;
    public String customer;

}
