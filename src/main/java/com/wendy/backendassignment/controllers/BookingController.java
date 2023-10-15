package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.BookingDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //Read
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBooking() {
        return ResponseEntity.ok().body(bookingService.getAllBooking());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingService.getBooking(id));
    }

    //Create
    @PostMapping(value = "")
    public ResponseEntity<Object> addBooking(@Valid @RequestBody BookingDto bookingDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            BookingDto addedBooking = bookingService.addBooking(bookingDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedBooking.id)));
            return ResponseEntity.created(uri).body(addedBooking);
        }

    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto) throws RecordNotFoundException {
        BookingDto updateBooking = bookingService.updateBooking(id, bookingDto);
        return ResponseEntity.ok().body(updateBooking);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id) throws RecordNotFoundException {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }


}