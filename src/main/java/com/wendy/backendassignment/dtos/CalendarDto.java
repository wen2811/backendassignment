package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Treatment;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarDto {
    public Long id;
    @FutureOrPresent
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public boolean availableTime;
    public Treatment treatment;


}

