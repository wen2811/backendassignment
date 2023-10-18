package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingDto;
import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.BookingTreatment;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.repositories.BookingRepository;
import com.wendy.backendassignment.repositories.BookingTreatmentRepository;
import com.wendy.backendassignment.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingTreatmentRepository bookingTreatmentRepository;
    private final CustomerRepository customerRepository;

    public BookingService(BookingRepository bookingRepository, BookingTreatmentRepository bookingTreatmentRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingTreatmentRepository = bookingTreatmentRepository;
        this.customerRepository = customerRepository;
    }

    //Read
    public List<BookingDto> getAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(transferBookingToDto(booking));
        }
        return bookingDtos;
    }

    public BookingDto getBooking(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);

        if (bookingOptional.isEmpty()){
            throw new RecordNotFoundException("There is no booking found with id: " + id);
        }
        Booking booking = bookingOptional.get();
        return transferBookingToDto(booking);
    }

    //create
    public BookingDto addBooking(BookingDto bookingDto) {
        Booking booking = transferDtoToBooking(bookingDto);
        bookingRepository.save(booking);

        return transferBookingToDto(booking);
    }

    //Update
    public BookingDto updateBooking(Long id, BookingDto bookingDto)throws RecordNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if(bookingOptional.isEmpty()) {
            throw new RecordNotFoundException("Booking did not find with this id: " + id);
        }
        Booking updateBooking = transferDtoToBooking(bookingDto);
        updateBooking.setId(id);
        bookingRepository.save(updateBooking);
        return transferBookingToDto(updateBooking);
    }

    //Delete
    public void deleteBooking(Long id) throws RecordNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if(optionalBooking.isEmpty()){
            throw new RecordNotFoundException("There is no booking found with id " + id);
        }
        bookingRepository.deleteById(id);
    }

    //Relationship methods
    //Update
    public BookingDto updateBookingTreatments(Long id, List<Long> treatmentIds) throws RecordNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            throw new RecordNotFoundException("Booking not found with this id: " + id);
        }
        Booking existingBooking = bookingOptional.get();

        List<BookingTreatment> newBookingTreatments = new ArrayList<>();
        for (Long treatmentId : treatmentIds) {
            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(treatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("Treatment not found with id: " + treatmentId));
            newBookingTreatments.add(bookingTreatment);
        }

        existingBooking.setBookingTreatments(newBookingTreatments);

        bookingRepository.save(existingBooking);

        return transferBookingToDto(existingBooking);
    }

    //create
    public Booking createBooking(Long customerId, List<Long> bookingTreatmentIds, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseGet(() -> {
                    Customer newCustomer = new Customer() {
                        @Override
                        public boolean isPasswordValid(String password) {
                            return false;
                        }
                        @Override
                        public void changePassword(String newPassword) {
                        }
                    };
                    newCustomer.setEmail(customerDto.getEmail());
                    newCustomer.setFirstName(customerDto.getFirstName());
                    newCustomer.setLastName(customerDto.getLastName());

                    customerRepository.save(newCustomer);

                    return newCustomer;
                });

        Booking booking = new Booking();
        booking.setCustomer(existingCustomer);

        List<BookingTreatment> bookingTreatments = new ArrayList<>();
        for (Long bookingTreatmentId : bookingTreatmentIds) {
            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(bookingTreatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("BookingTreatment doesn't exist."));
            bookingTreatments.add(bookingTreatment);
        }
        booking.setBookingTreatments(bookingTreatments);
        bookingRepository.save(booking);
        return booking;
    }

    //Read
    public List<BookingDto> getBookingsForCustomer(Long customerId) throws RecordNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new RecordNotFoundException("Customer not found with ID: " + customerId);
        }
        Customer customer = customerOptional.get();
        List<Booking> bookings = bookingRepository.findByCustomer(customer);
        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingDto bookingDto = transferBookingToDto(booking);
            bookingDtos.add(bookingDto);
        }
        return bookingDtos;
    }

    //create without registration
    public BookingDto createBookingWithoutRegistration(Long customerId, List<Long> bookingTreatmentIds, CustomerDto customerDto) {
        Booking booking = new Booking();

        if (customerDto != null) {
            Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            if (optionalCustomer.isEmpty()) {
                throw new RecordNotFoundException("Customer doesn't exist.");
            }
            Customer existingCustomer = optionalCustomer.get();
            booking.setCustomer(existingCustomer);
        }
        List<BookingTreatment> bookingTreatments = new ArrayList<>();
        for (Long bookingTreatmentId : bookingTreatmentIds) {
            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(bookingTreatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("BookingTreatment doesn't exist."));
            bookingTreatments.add(bookingTreatment);
        }
        booking.setBookingTreatments(bookingTreatments);
        bookingRepository.save(booking);

        return transferBookingToDto(booking);
    }


    public BookingDto transferBookingToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();

        bookingDto.id = booking.getId();
        bookingDto.date = booking.getDate();
        bookingDto.totalAmount = booking.getTotalAmount();
        bookingDto.bookingStatus = booking.getBookingStatus();
        bookingDto.invoice = booking.getInvoice();
        bookingDto.bookingTreatments = booking.getBookingTreatments();
        bookingDto.customer =booking.getCustomer();
        return bookingDto;
    }


    public Booking transferDtoToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        bookingDto.setId(bookingDto.id);
        bookingDto.setDate(bookingDto.date);
        bookingDto.setTotalAmount(bookingDto.totalAmount);
        bookingDto.setBookingStatus(bookingDto.bookingStatus);
        bookingDto.setInvoice(bookingDto.invoice);
        bookingDto.setBookingTreatments(bookingDto.bookingTreatments);
        bookingDto.setCustomer(bookingDto.customer);
        return booking;
    }
}
