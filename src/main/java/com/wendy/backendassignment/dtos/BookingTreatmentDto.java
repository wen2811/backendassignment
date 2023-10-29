package com.wendy.backendassignment.dtos;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingTreatmentDto {
    public Long id;
    public int quantity;

    //public Booking booking;
    public Long treatment;


}
