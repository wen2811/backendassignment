package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingDto;
import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.*;
import com.wendy.backendassignment.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingTreatmentRepository bookingTreatmentRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

    private final TreatmentRepository treatmentRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BookingTreatmentRepository bookingTreatmentRepository, CustomerRepository customerRepository, UserRepository userRepository, InvoiceRepository invoiceRepository, TreatmentRepository treatmentRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingTreatmentRepository = bookingTreatmentRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
        this.treatmentRepository = treatmentRepository;
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
    public BookingDto updateBookingTreatments(Long id, List<Long> bookingTreatmentIds) throws RecordNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            throw new RecordNotFoundException("Booking not found with this id: " + id);
        }
        Booking existingBooking = bookingOptional.get();

        List<Long> newBookingTreatments = new ArrayList<>();
        for (Long bookingTreatmentId : bookingTreatmentIds) {
            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(bookingTreatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("BookingTreatment not found with id: " + bookingTreatmentId));
            newBookingTreatments.add(bookingTreatment.getId());
        }

        existingBooking.setBookingTreatments(newBookingTreatments);

        bookingRepository.save(existingBooking);

        return transferBookingToDto(existingBooking);
    }

    //create
    public BookingDto createBooking(BookingDto bookingDto) {
        UserDto userDto = bookingDto.getUser();

        User existingUser = userRepository.findById(userDto.getUsername()).orElseGet(() -> {

            User newUser = createUserFromDto(userDto);
            return userRepository.save(newUser);
        });

        Booking booking = transferDtoToBooking(bookingDto);
        // create invoice

//        List<Long> bookingTreatments = new ArrayList<>();
//        for (Long bookingTreatmentId : userDto.getBookingTreatmentIds()) {
//            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(bookingTreatmentId)
//                    .orElseThrow(() -> new RecordNotFoundException("BookingTreatment doesn't exist."));
//            bookingTreatments.add(bookingTreatment.getId());
//        }
//        booking.setBookingTreatments(bookingTreatments);
        bookingRepository.save(booking);

        return transferBookingToDto(booking);
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

        List<Long> bookingTreatments = new ArrayList<>();
        for (Long bookingTreatmentId : bookingTreatmentIds) {
            BookingTreatment bookingTreatment = bookingTreatmentRepository.findById(bookingTreatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("BookingTreatment doesn't exist."));
            bookingTreatments.add(bookingTreatment.getId());
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
        if (booking != null) {
            bookingDto.id = booking.getId();
            bookingDto.date = booking.getDate();
            bookingDto.totalAmount = booking.getTotalAmount();
            bookingDto.bookingStatus = booking.getBookingStatus();
            bookingDto.invoice = booking.getInvoiceId();
//            bookingDto.bookingTreatment = booking.getBookingTreatmentIds();
            bookingDto.customerId = booking.getCustomerId();
            // ***** user moet userDto worden *******
//            bookingDto.user = use
            bookingDto.treatmentIds = booking.getTreatments().stream()
                    .map(Treatment::getId)
                    .collect(Collectors.toList());




        }
        return bookingDto;
    }


    public Booking transferDtoToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        booking.setId(bookingDto.id);
        booking.setDate(bookingDto.date);
        booking.setTotalAmount(calculateTotalAmount(bookingDto.getTreatmentIds()));
        booking.setBookingStatus(bookingDto.bookingStatus);
        booking.setUser(userRepository.findById(bookingDto.user.getUsername()).get());
//        booking.setBookingTreatments(bookingDto.bookingTreatment);
        booking.setTreatments(treatmentRepository.findAllById(bookingDto.getTreatmentIds()));


        if (bookingDto.customerId != null) {
            Customer customer = new Customer();
            customer.setId(bookingDto.customerId);
            booking.setCustomer(customer);
        }

        if (bookingDto.invoice != null) {
            Invoice invoice = new Invoice();
            invoice.setId(bookingDto.invoice);
           booking.setInvoice(invoice);
        }

        return booking;
    }

    public double calculateTotalAmount(List<Long> treatmentIds){
        double amount = 0;
        List<Treatment> treatments = treatmentRepository.findAllById(treatmentIds);
        for (Treatment treatment: treatments){
            amount += treatment.getPrice();
        }

        return amount;
    }

}

