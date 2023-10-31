package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.CalendarDto;
import com.wendy.backendassignment.services.CalendarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/calendars")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    //Read
    @GetMapping(value = "")
    public ResponseEntity<List<CalendarDto>> getAllCalendar() {
        return ResponseEntity.ok().body(calendarService.getAllCalendar());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CalendarDto> getCalendar(@PathVariable("id") Long id){
        CalendarDto calendarDto = calendarService.getCalendar(id);
        return ResponseEntity.ok().body(calendarDto);
    }

    //Create
    @PostMapping(value = "")
    public ResponseEntity<Object> addCalendar(@Valid @RequestBody CalendarDto calendarDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            CalendarDto addedCalendar = calendarService.addCalendar(calendarDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedCalendar.id)));
            return ResponseEntity.created(uri).body(addedCalendar);
        }

    }

    //Update
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCalendar(@PathVariable("id") Long id, @RequestBody CalendarDto calendarDto) {
        calendarService.updateCalendar(id, calendarDto);
        return ResponseEntity.ok(calendarDto);
    }

    //Delete
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object>deleteCalendar(@PathVariable Long id) {
        calendarService.deleteCalendar(id);
        return ResponseEntity.noContent().build();
    }

    
}

