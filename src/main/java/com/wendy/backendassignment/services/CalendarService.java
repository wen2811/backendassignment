/*
package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.models.Calendar;
import com.wendy.backendassignment.repositories.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public List<CalendarDto> getAllCalendar() {
        Iterable<Calendar> calendars = calendarRepository.findAll();
        List<CalendarDto> calendarDtos = new ArrayList<>();

        for (Calendar calendar : calendars) {
            calendarDtos.add(transferCalenderDto(calendar));
        }
        return calendarDtos;
    }

   */
/* public CalendarDto createCalendarEvent(CalendarDto calendarDto) {

    }
*//*

    public CalendarDto transferCalenderDto(Calendar calendar) {
        CalendarDto calendarDto = new CalendarDto();

        calendarDto.id = calendar.getId();
        calendarDto.date = calendar.getDate();
        calendarDto.startTime = calendar.getStartTime();
        calendarDto.endTime = calendar.getEndTime();
        calendarDto.availableTime = calendar.isAvailableTime();
        return calendarDto;
    }


}

*/







