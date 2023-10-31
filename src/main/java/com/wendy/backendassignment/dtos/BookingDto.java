package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookingDto {
    public Long id;
    public LocalDate date;
    public double totalAmount;
    public BookingStatus bookingStatus;

    public Long invoice;

    // dit moet weg geen bookingtreatment
    public List<Long> bookingTreatment;

    public List<Long> treatmentIds;

    public Long customerId;

    // omdat misschien user bestaat niet gebruik ik de data om een nieuwe aan ta maken
    public UserDto user;

}
