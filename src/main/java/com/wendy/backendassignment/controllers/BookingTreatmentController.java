package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.BookingTreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.services.BookingTreatmentService;
import com.wendy.backendassignment.services.TreatmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookingtreatments")
public class BookingTreatmentController {
    private final BookingTreatmentService bookingTreatmentService;
    private final TreatmentService treatmentService;

    public BookingTreatmentController(BookingTreatmentService bookingTreatmentService, TreatmentService treatmentService) {
        this.bookingTreatmentService = bookingTreatmentService;
        this.treatmentService = treatmentService;
    }

    //Read
    @GetMapping
    public ResponseEntity<List<BookingTreatmentDto>> getAllBookingTreatment() {
        return ResponseEntity.ok().body(bookingTreatmentService.getAllBookingTreatment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingTreatmentDto> getBookingTreatment(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingTreatmentService.getBookingTreatment(id));
    }

    //Create
    @PostMapping
    public ResponseEntity<Object> addBookingTreatment(@Valid @RequestBody BookingTreatmentDto bookingTreatmentDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            BookingTreatmentDto addedBookingTreatment = bookingTreatmentService.addBookingTreatment(bookingTreatmentDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedBookingTreatment.id)));
            return ResponseEntity.created(uri).body(addedBookingTreatment);
        }
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBookingTreatment(@PathVariable Long id, @RequestBody BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        BookingTreatmentDto updateBookingTreatment = bookingTreatmentService.updateBookingTreatment(id, bookingTreatmentDto);
        return ResponseEntity.ok().body(updateBookingTreatment);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBookingTreatment(@PathVariable Long id) throws RecordNotFoundException {
        bookingTreatmentService.deleteBookingTreatment(id);
        return ResponseEntity.noContent().build();
    }

    //Relationships methods
  @PostMapping("/bookingtreatments/add")
  public ResponseEntity<BookingTreatmentDto> addTreatmentToBookingTreatment(@RequestParam Long treatmentId, @RequestBody BookingTreatmentDto bookingTreatmentDto) {
      try {
          BookingTreatmentDto addedBookingTreatment = bookingTreatmentService.addTreatmentToBookingTreatment(treatmentId, bookingTreatmentDto);
          return new ResponseEntity<>(addedBookingTreatment, HttpStatus.CREATED);
      } catch (RecordNotFoundException e) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
  }


}
