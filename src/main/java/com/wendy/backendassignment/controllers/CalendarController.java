/*
package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.services.CalendarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/calendars")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public ResponseEntity<List<CalendarDto>> getAllCalendar() {
        return ResponseEntity.ok().body(calendarService.getAllCalendar());
    }

    @PostMapping("{booking_id}")
    public ResponseEntity<CalendarDto> createCalendarEvent(@Valid @RequestBody CalendarDto calendarDto) {
        CalendarDto createdCalendar = calendarService.createCalendarEvent(calendarDto);
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + createdCalendar.getId())));
        return ResponseEntity.created(uri).body(createdCalendar);
    }

    
}
*/
