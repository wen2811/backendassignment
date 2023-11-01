package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.BookingDto;
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
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final TreatmentRepository treatmentRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, UserRepository userRepository, InvoiceRepository invoiceRepository, TreatmentRepository treatmentRepository) {
        this.bookingRepository = bookingRepository;
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
        Booking updateBooking = bookingOptional.get();
        updateBooking.setDate(bookingDto.getDate());
        updateBooking.setTotalAmount(bookingDto.getTotalAmount());
        updateBooking.setBookingStatus(bookingDto.getBookingStatus());
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

        List<Long> newTreatments = new ArrayList<>();
        for (Long treatmentId : treatmentIds) {
            Treatment Treatment = treatmentRepository.findById(treatmentId)
                    .orElseThrow(() -> new RecordNotFoundException("Treatment not found with id: " + treatmentIds));
            newTreatments.add(treatmentId);
        }

        existingBooking.setTreatments(newTreatments);

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

    public BookingDto transferBookingToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        if (booking != null) {
            bookingDto.id = booking.getId();
            bookingDto.date = booking.getDate();
            bookingDto.totalAmount = booking.getTotalAmount();
            bookingDto.bookingStatus = booking.getBookingStatus();
            bookingDto.invoice = booking.getInvoiceId();
            bookingDto.customerId = booking.getCustomerId();
            bookingDto.treatmentIds = booking.getTreatments().stream()
                    .map(Treatment::getId)
                    .collect(Collectors.toList());
            if (booking.getUser() != null) {
                UserDto userDto = new UserDto();
                userDto.setUsername(booking.getUser().getUsername());
                bookingDto.setUser(userDto);
            } else {
                bookingDto.setUser(null);
            }
        }
        return bookingDto;
    }

    public Booking transferDtoToBooking(BookingDto bookingDto) {
        Booking booking = new Booking();

        booking.setId(bookingDto.id);
        booking.setDate(bookingDto.date);
        booking.setTotalAmount(calculateTotalAmount(bookingDto.getTreatmentIds()));
        booking.setBookingStatus(bookingDto.bookingStatus);

        if(bookingDto.getUser() != null) {
            User user = new User();
            user.setUsername(bookingDto.getUser().getUsername());
            user.setFirstname(bookingDto.getUser().getFirstname());
            user.setLastname(bookingDto.getUser().getLastname());
            user.setEmail(bookingDto.getUser().getEmail());

            booking.setUser(user);
        } else {
            booking.setUser(null);
        };

        if(booking.getTreatments() != null){
            booking.setTreatments(bookingDto.getTreatmentIds());
        }

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

    public double calculateTotalAmount(List<Long> treatmentIds) {
        double amount = 0;
        List<Treatment> treatments = treatmentRepository.findAllById(treatmentIds);
        for (Treatment treatment : treatments) {
            Double price = treatment.getPrice();
            if (price != null) {
                amount += price;
            }
        }
        return amount;
    }
}



