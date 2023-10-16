package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.BookingTreatmentDto;
import com.wendy.backendassignment.dtos.TreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.TreatmentType;
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
@RequestMapping("/treatments")
public class TreatmentController {
    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    //Read
    @GetMapping("/")
    public ResponseEntity<List<TreatmentDto>> getAllTreatments() {
        return ResponseEntity.ok().body(treatmentService.getAllTreatments());
    }

    @GetMapping("/{type}")
    public ResponseEntity<TreatmentDto> getTreatmentsByType(@PathVariable TreatmentType type) throws RecordNotFoundException {
        return ResponseEntity.ok().body(treatmentService.getTreatmentsByType(type));
    }

    //Create
    @PostMapping("")
    public ResponseEntity<Object> addTreatment(@Valid @RequestBody TreatmentDto treatmentDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            TreatmentDto addedTreatment = treatmentService.addTreatment(treatmentDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedTreatment.id)));
            return ResponseEntity.created(uri).body(addedTreatment);
        }
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTreatment(@PathVariable("id") Long id, @RequestBody TreatmentDto treatmentDto) {
        treatmentService.updateTreatment(id, treatmentDto);
        return ResponseEntity.ok(treatmentDto);
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTreatment(@PathVariable Long id) throws RecordNotFoundException  {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }

    //Methods for relation
    @PostMapping("/{treatmentId}/bookingtreatments")
    public ResponseEntity<BookingTreatmentDto> addBookingTreatmentToTreatment(@PathVariable Long treatmentId, @RequestBody BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatmentDto addedBookingTreatment = treatmentService.addBookingTreatmentToTreatment(treatmentId, bookingTreatmentDto);
        return new ResponseEntity<>(addedBookingTreatment, HttpStatus.CREATED);
    }

    @PutMapping("/update-booking-treatment/{id}")
    public ResponseEntity<BookingTreatmentDto> updateBookingTreatment(@PathVariable Long id, @RequestBody BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        BookingTreatmentDto updatedBookingTreatment = treatmentService.updateBookingTreatment(id, bookingTreatmentDto);
        return ResponseEntity.ok(updatedBookingTreatment);
    }

    @GetMapping("/bookingtreatments/{id}")
    public ResponseEntity<BookingTreatmentDto> getBookingTreatmentById(@PathVariable Long id) throws RecordNotFoundException {
        BookingTreatmentDto bookingTreatment = treatmentService.getBookingTreatmentById(id);
        return ResponseEntity.ok(bookingTreatment);
    }



}
