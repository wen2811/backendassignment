package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingTreatmentDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.BookingTreatment;
import com.wendy.backendassignment.models.Treatment;
import com.wendy.backendassignment.repositories.BookingTreatmentRepository;
import com.wendy.backendassignment.repositories.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingTreatmentService {
    private final BookingTreatmentRepository bookingTreatmentRepository;
    private final TreatmentRepository treatmentRepository;

    public BookingTreatmentService(BookingTreatmentRepository bookingTreatmentRepository, TreatmentRepository treatmentRepository) {
        this.bookingTreatmentRepository = bookingTreatmentRepository;
        this.treatmentRepository = treatmentRepository;
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
        BookingTreatment updateBookingTreatment = bookingTreatmentOptional.get();
        updateBookingTreatment.setQuantity(bookingTreatmentDto.getQuantity());
        updateBookingTreatment.setTreatment(bookingTreatmentDto.getTreatment());
        updateBookingTreatment.setBooking(bookingTreatmentDto.getBooking());
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


    //relationships methods
    public BookingTreatmentDto addTreatmentToBookingTreatment(Long treatmentId, BookingTreatmentDto bookingTreatmentDto) throws RecordNotFoundException {
        Optional<BookingTreatment> bookingTreatmentOptional = bookingTreatmentRepository.findById(bookingTreatmentDto.getId());

        if (bookingTreatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("BookingTreatment not found with id: " + bookingTreatmentDto.getId());
        }

        BookingTreatment bookingTreatment = bookingTreatmentOptional.get();

        Optional<Treatment> treatmentOptional = treatmentRepository.findById(treatmentId);
        if (treatmentOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no treatment found with this ID: " + treatmentId);
        }

        Treatment treatment = treatmentOptional.get();
        bookingTreatment.setTreatment(treatment);
        bookingTreatmentRepository.save(bookingTreatment);

        return transferBookingTreatmentToDto(bookingTreatment);
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
