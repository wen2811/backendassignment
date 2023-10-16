package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Treatment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingTreatmentDto {
    public Long id;
    public int quantity;
    public String customerName;
    public String customerEmail;
    public Booking booking;
    public Treatment treatment;

}
