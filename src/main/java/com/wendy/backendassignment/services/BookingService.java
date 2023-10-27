package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingDto;
import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.*;
import com.wendy.backendassignment.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingTreatmentRepository bookingTreatmentRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

    public BookingService(BookingRepository bookingRepository, BookingTreatmentRepository bookingTreatmentRepository, CustomerRepository customerRepository, UserRepository userRepository, InvoiceRepository invoiceRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingTreatmentRepository = bookingTreatmentRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
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

        if (bookingOptional.isEmpty()) {
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
    public BookingDto updateBooking(Long id, BookingDto bookingDto) throws RecordNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
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
        if (optionalBooking.isEmpty()) {
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
    public Booking createBooking(String username, List<Long> bookingTreatmentIds, UserDto userDto) {
        User existingUser = userRepository.findById(username).orElseGet(() -> {

            User newUser = createUserFromDto(userDto);
            return userRepository.save(newUser);
        });

        Booking booking = new Booking();
        booking.setUser(existingUser);

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

    private User createUserFromDto(UserDto userDto) {
        if (!isEmailValid(userDto.getEmail())) {
            return null;
        }
        if (!isPasswordValid(userDto.getPassword())) {
            return null;
        }

        User newUser = new User();
        newUser.setUserRole(UserRole.CUSTOMER);
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());
        newUser.setFirstname(userDto.getFirstname());
        newUser.setLastname(userDto.getLastname());
        newUser.setDob(userDto.getDob());


        return newUser;
    }

    private boolean isPasswordValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        return true;
    }


    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        return true;

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
    public BookingDto createBookingWithoutRegistration(String email, List<Long> bookingTreatmentIds, CustomerDto customerDto, UserDto userDto) {
        Booking booking = new Booking();
        Customer excistingCustomer = customerRepository.findByEmail(email);

        if (excistingCustomer == null) {
            excistingCustomer = new Customer();
            excistingCustomer.setEmail(email);
            excistingCustomer.setFirstName(customerDto.firstName);
            excistingCustomer.setLastName(customerDto.lastName);
            excistingCustomer.setPhoneNumber(customerDto.phoneNumber);

//            User newUser = new User();
//            newUser.setUserRole(userDto.getUserRole());
//            excistingCustomer.setUser(newUser);

            customerRepository.save(excistingCustomer);
        }
        booking.setCustomer(excistingCustomer);

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

    public void createBookingWithInvoice() {
        Booking booking = new Booking();
        bookingRepository.save(booking);

        Invoice invoice = new Invoice();
        invoiceRepository.save(invoice);
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

        Customer customer = new Customer();
        customer.setId(bookingDto.customer.getId());
        booking.setCustomer(customer);

        return booking;
    }
}
