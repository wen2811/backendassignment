package com.wendy.backendassignment.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.models.Invoice;
import com.wendy.backendassignment.repositories.BookingRepository;
import com.wendy.backendassignment.repositories.CustomerRepository;
import com.wendy.backendassignment.repositories.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc()
class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    Invoice invoice1;
    Invoice invoice2;
    Invoice invoice3;

    InvoiceDto invoiceDto1;
    InvoiceDto invoiceDto2;
    InvoiceDto invoiceDto3;
    CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        if (invoiceRepository.count() > 0){
            invoiceRepository.deleteAll();
        }
        if(bookingRepository.count() > 0){
            bookingRepository.deleteAll();
        }
        if(customerRepository.count() > 0){
            customerRepository.deleteAll();
        }

       Customer customer = Customer.builder()
               .firstName("Maria")
               .email("maria@gmail.com")
               .build();

        customerRepository.save(customer);

        customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setEmail(customer.getEmail());


        Booking booking = Booking.builder().totalAmount(90)
                .date(LocalDate.of(2023, 11, 1)).customer(customer).build();

        Booking booking2 = Booking.builder().totalAmount(125)
                .date(LocalDate.of(2023, 11, 15)).customer(customer).build();

        booking = bookingRepository.save(booking);
        booking2 = bookingRepository.save(booking2);

        invoice1 = new Invoice(1L, booking.getTotalAmount(), booking.getDate(), booking, customer);
        invoice2 = new Invoice(2L, booking2.getTotalAmount(), booking2.getDate(), booking2, customer);

        // save invoices
        invoice1 = invoiceRepository.save(invoice1);
        invoice2 = invoiceRepository.save(invoice2);

        invoiceDto1 = InvoiceDto.builder()
                .id(invoice1.getId())
                .bookingId(booking.getId())
                .customerId(customer.getId())
                .build();

        invoiceDto2 = InvoiceDto.builder()
                .id(invoice2.getId())
                .bookingId(booking2.getId())
                .customerId(customer.getId())
                .build();

        // Invoice used to test adding new Invoice
        Booking booking3 = Booking.builder().totalAmount(80)
                .date(LocalDate.of(2023, 11, 8)).customer(customer).build();

        bookingRepository.save(booking3);

        invoice3 = new Invoice(3L, booking3.getTotalAmount(), booking3.getDate(), booking3, customer);
        invoiceDto3 = new InvoiceDto(invoice3.getId(), invoice3.getAmount(),invoice3.getInvoicedate(), booking3.getId(), customer.getId(), customer.getEmail());

    }

    @Test
    void getAllInvoice() throws Exception {

       mockMvc.perform(get("/invoices"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(invoiceDto1.getId().toString()))
                .andExpect(jsonPath("$[0].amount").value(90))
                .andExpect(jsonPath("$[0].invoicedate").value("2023-11-01"))
                .andExpect(jsonPath("$[0].bookingId").value(invoiceDto1.getBookingId()))
                .andExpect(jsonPath("$[0].customerId").value(invoiceDto1.getCustomerId()))
                .andExpect(jsonPath("$[0].customer").value("maria@gmail.com"))

                .andExpect(jsonPath("$[1].id").value(invoiceDto2.getId().toString()))
                .andExpect(jsonPath("$[1].amount").value(125.0))
                .andExpect(jsonPath("$[1].invoicedate").value("2023-11-15"))
                .andExpect(jsonPath("$[1].bookingId").value(invoiceDto2.getBookingId()))
                .andExpect(jsonPath("$[1].customerId").value(invoiceDto2.getCustomerId()))
                .andExpect(jsonPath("$[1].customer").value("maria@gmail.com"));

    }

    @Test
    void getInvoice() throws Exception {
       mockMvc.perform(get("/invoices/" + invoiceDto2.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(invoiceDto2.getId().toString()))
                .andExpect(jsonPath("amount").value(125))
                .andExpect(jsonPath("invoicedate").value("2023-11-15"));

    }

    @Test
    void addInvoice() throws Exception{
           mockMvc.perform(post("/invoices/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invoiceDto3)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(3L))
                .andExpect(jsonPath("amount").value(80))
                .andExpect(jsonPath("invoicedate").value("2023-11-08"));
    }


    private static String asJsonString(final InvoiceDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}