package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Calendar;
import com.wendy.backendassignment.repositories.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

       //Read
    public List<CalendarDto> getAllCalendar() {
        List<Calendar> calendars = calendarRepository.findAll();
        List<CalendarDto> calendarDtos = new ArrayList<>();

        for (Calendar calendar : calendars) {
            calendarDtos.add(transferCalenderToDto(calendar));
        }
        return calendarDtos;
    }

    public CalendarDto getCalendar(Long id) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);
        if (calendarOptional.isPresent()){
            Calendar calendar = calendarOptional.get();
            return transferCalenderToDto(calendar);
        } else {
            throw new RecordNotFoundException("There's no Calendar found with id " + id);
        }
    }

    //Create
    public CalendarDto addCalendar(CalendarDto calendarDto) {
        Calendar calendar = transferDtoToCalendar(calendarDto);
        calendarRepository.save(calendar);

        return transferCalenderToDto(calendar);
    }

    //update
    public void updateCalendar(Long id, CalendarDto calendarDto) {
        if(!calendarRepository.existsById(id)) {
            throw new RecordNotFoundException("There's no Calendar-timeslot found");
        }
        Calendar updatedCalendar = calendarRepository.findById(id).orElse(null);
        updatedCalendar.setId(calendarDto.getId());
        updatedCalendar.setDate(calendarDto.getDate());
        updatedCalendar.setStartTime(calendarDto.getStartTime());
        updatedCalendar.setEndTime(calendarDto.getEndTime());
        updatedCalendar.setAvailableTime(calendarDto.isAvailableTime());
        calendarRepository.save(updatedCalendar);

    }

    //Delete
    public void deleteCalendar(Long id) {
        calendarRepository.deleteById(id);
    }


    public CalendarDto transferCalenderToDto(Calendar calendar) {
        CalendarDto calendarDto = new CalendarDto();

        calendarDto.id = calendar.getId();
        calendarDto.date = calendar.getDate();
        calendarDto.startTime = calendar.getStartTime();
        calendarDto.endTime = calendar.getEndTime();
        calendarDto.availableTime = calendar.isAvailableTime();
        return calendarDto;
    }

    public Calendar transferDtoToCalendar(CalendarDto calendarDto) {
        Calendar calendar = new Calendar();
        calendar.setDate(calendarDto.date);
        calendar.setStartTime(calendarDto.startTime);
        calendar.setEndTime(calendarDto.endTime);
        calendar.setAvailableTime(calendarDto.availableTime);
        return calendar;
    }

}









