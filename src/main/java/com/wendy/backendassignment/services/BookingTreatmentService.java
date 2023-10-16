package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingTreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.BookingTreatment;
import com.wendy.backendassignment.repositories.BookingTreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingTreatmentService {
    private final BookingTreatmentRepository bookingTreatmentRepository;

    public BookingTreatmentService(BookingTreatmentRepository bookingTreatmentRepository) {
        this.bookingTreatmentRepository = bookingTreatmentRepository;
    }

    //Read
    public List<BookingTreatmentDto> getAllBookingTreatment(){
        Iterable<BookingTreatment> bookingTreatments = bookingTreatmentRepository.findAll();
        List<BookingTreatmentDto> bookingTreatmentDtos = new ArrayList<>();

        for (BookingTreatment bt: bookingTreatments){
            bookingTreatmentDtos.add(transferBookingTreatmentToDto(bt));
        }
        return bookingTreatmentDtos;
    }

    public BookingTreatmentDto getBookingTreatment(Long id) {
        Optional<BookingTreatment> bookingTreatmentOptional = bookingTreatmentRepository.findById(id);

        if (bookingTreatmentOptional.isEmpty()){
            throw new RecordNotFoundException("There is no bookingtreatment found with id: " + id);
        }
        BookingTreatment bookingTreatment = bookingTreatmentOptional.get();
        return transferBookingTreatmentToDto(bookingTreatment);
    }

    //Create
    public BookingTreatmentDto addBookingTreatment(BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatment bookingTreatment = transferDtoToBookingTreatment(bookingTreatmentDto);
        bookingTreatmentRepository.save(bookingTreatment);

        return transferBookingTreatmentToDto(bookingTreatment);
    }

    //Update
    public BookingTreatmentDto updateBookingTreatment(Long id, BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        Optional<BookingTreatment> bookingTreatmentOptional = bookingTreatmentRepository.findById(id);
        if(bookingTreatmentOptional.isEmpty()){
            throw new RecordNotFoundException("Bookingtreatment is not found with this id " + id);
        }
        BookingTreatment updateBookingTreatment = transferDtoToBookingTreatment((bookingTreatmentDto));
        updateBookingTreatment.setId(id);
        bookingTreatmentRepository.save(updateBookingTreatment);

        return transferBookingTreatmentToDto(updateBookingTreatment);
    }

    //Delete
    public void deleteBookingTreatment(Long id) throws RecordNotFoundException {
        Optional<BookingTreatment> optionalBookingTreatment = bookingTreatmentRepository.findById(id);
        if(optionalBookingTreatment.isEmpty()){
            throw new RecordNotFoundException("There is no booking found with id " + id);
        }
        bookingTreatmentRepository.deleteById(id);
    }


    public BookingTreatmentDto transferBookingTreatmentToDto(BookingTreatment bookingTreatment) {
        BookingTreatmentDto bookingTreatmentDto = new BookingTreatmentDto();

        bookingTreatmentDto.id = bookingTreatment.getId();
        bookingTreatmentDto.quantity = bookingTreatment.getQuantity();
        bookingTreatmentDto.treatment = bookingTreatment.getTreatment();
        bookingTreatmentDto.booking = bookingTreatment.getBooking();
        bookingTreatmentDto.customerName = bookingTreatment.getCustomerName();
        bookingTreatmentDto.customerEmail = bookingTreatment.getCustomerEmail();
        return bookingTreatmentDto;
    }
    public BookingTreatment transferDtoToBookingTreatment(BookingTreatmentDto bookingTreatmentDto) {
        BookingTreatment bookingTreatment = new BookingTreatment();

        bookingTreatmentDto.setId(bookingTreatmentDto.id);
        bookingTreatmentDto.setQuantity(bookingTreatmentDto.quantity);
        bookingTreatmentDto.setTreatment(bookingTreatmentDto.treatment);
        bookingTreatmentDto.setBooking(bookingTreatmentDto.booking);
        bookingTreatmentDto.setCustomerName(bookingTreatmentDto.customerName);
        bookingTreatmentDto.setCustomerEmail(bookingTreatmentDto.customerEmail);
        return bookingTreatment;
    }
}
