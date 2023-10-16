package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.BookingTreatment;
import com.wendy.backendassignment.models.Calendar;
import com.wendy.backendassignment.models.TreatmentType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TreatmentDto {
    public Long id;
    @NotBlank(message = "Treatment name is required")
    public String name;
    public TreatmentType type;
    public String description;
    public double duration;
    public double price;
    public Calendar calendar;
    public List<BookingTreatment> bookingTreatments;



}
