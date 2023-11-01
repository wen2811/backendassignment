package com.wendy.backendassignment.utils;

import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.dtos.TreatmentDto;

public class UpdateRequest {

    private TreatmentDto treatmentDto;
    private CalendarDto calendarDto;

    public CalendarDto getCalendarDto() {
        if (calendarDto == null) {
            return null;
        }

        CalendarDto updatedCalendarDto = new CalendarDto();
        updatedCalendarDto.setDate(calendarDto.getDate());
        updatedCalendarDto.setStartTime(calendarDto.getStartTime());
        updatedCalendarDto.setEndTime(calendarDto.getEndTime());
        updatedCalendarDto.setAvailableTime(calendarDto.isAvailableTime());
        return updatedCalendarDto;
    }

    public TreatmentDto getTreatmentDto() {
        if (treatmentDto == null) {
            return null;
        }

        TreatmentDto updatedTreatmentDto = new TreatmentDto();
        updatedTreatmentDto.setId(treatmentDto.getId());
        updatedTreatmentDto.setName(treatmentDto.getName());
        updatedTreatmentDto.setType(treatmentDto.getType());
        updatedTreatmentDto.setDescription(treatmentDto.getDescription());
        updatedTreatmentDto.setDuration(treatmentDto.getDuration());
        updatedTreatmentDto.setPrice(treatmentDto.getPrice());
        return updatedTreatmentDto;
    }
}
