package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.BookingDto;
import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.services.BookingService;
import com.wendy.backendassignment.services.CustomerService;
import com.wendy.backendassignment.services.UserService;
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
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final CustomerService customerService;
    private final UserService userService;

    public BookingController(BookingService bookingService, CustomerService customerService, UserService userService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.userService = userService;
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

    //Relationships methods
    //update
    @PutMapping("/updateTreatments/{id}")
    public ResponseEntity<BookingDto> updateBookingTreatments(@PathVariable Long id, @RequestBody List<Long> treatmentIds) throws RecordNotFoundException {
        BookingDto updatedBookingDto = bookingService.updateBookingTreatments(id, treatmentIds);
        return new ResponseEntity<>(updatedBookingDto, HttpStatus.OK);
    }

    @PostMapping("/registerbookings/")
    public ResponseEntity<Object> createBooking(@RequestParam Long userId, @RequestParam List<Long> bookingTreatmentIds, @RequestBody UserDto userDto) {
        User existingUser = userService.getCustomerById(userId);

        if (existingUser == null) {
            existingUser = userService.registerUser(userDto);
        }
        Booking booking = bookingService.createBooking(userId, bookingTreatmentIds, userDto);

        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    //Read
    @GetMapping("/{customerId}/bookings")
    public ResponseEntity<List<BookingDto>> getBookingsForCustomer(@PathVariable Long customerId) throws RecordNotFoundException {
        List<BookingDto> bookingDtos = bookingService.getBookingsForCustomer(customerId);
        return ResponseEntity.ok().body(bookingDtos);
    }

    //create
    @PostMapping("/createWithoutRegistration")
    public ResponseEntity<BookingDto> createBookingWithoutRegistration(@RequestParam(required = false)String email, @RequestParam List<Long> bookingTreatmentIds,@RequestBody(required = false) CustomerDto customerDto, UserDto userDto) {
        BookingDto createdBooking = bookingService.createBookingWithoutRegistration(email, bookingTreatmentIds, customerDto, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }
}


