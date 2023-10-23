package com.wendy.backendassignment.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wendy.backendassignment.dtos.BookingDto;
import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Invoice;
import com.wendy.backendassignment.repositories.BookingRepository;
import com.wendy.backendassignment.repositories.CustomerRepository;
import com.wendy.backendassignment.repositories.InvoiceRepository;
import com.wendy.backendassignment.services.BookingService;
import com.wendy.backendassignment.services.CustomerService;
import com.wendy.backendassignment.services.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    Invoice invoice1;
    Invoice invoice2;
    Invoice invoice3;
    InvoiceDto invoiceDto1;
    InvoiceDto invoiceDto2;
    InvoiceDto invoiceDto3;
    InvoiceDto invalidInvoiceDto;
    Booking booking;
    CustomerDto customer;
    BookingDto bookingDto;
    CustomerDto customerDto;
    List<Invoice> invoices = new ArrayList<>();

    @BeforeEach
    void setUp() {
        invoiceRepository.deleteAll();
        bookingRepository.deleteAll();
        customerRepository.deleteAll();

        invoice1 = new Invoice(1L, 90, (LocalDate.of(2023, 10, 31)),null,null);
        invoice2 = new Invoice(2L, 100, (LocalDate.of(2023, 11, 15)),null,null);
        invoice3 = new Invoice(3L, 75, (LocalDate.of(2023, 11, 1)),null,null);

        invoiceRepository.save(invoice1);
        invoiceRepository.save(invoice2);
        invoiceRepository.save(invoice3);

        invoiceDto1 = new InvoiceDto(invoice1.getId(), 90, LocalDate.of(2023,10,31), null, null);
        invoiceDto2 = new InvoiceDto(invoice2.getId(), 100, LocalDate.of(2023,11,15), null, null);
        invoiceDto3 = new InvoiceDto(3L, 75, LocalDate.of(2023,11,1), null, null);


        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        invalidInvoiceDto = new InvoiceDto();
        invalidInvoiceDto.setAmount(-50);
        invalidInvoiceDto.setInvoicedate(LocalDate.of(2021, 11, 1));


        customerDto = new CustomerDto();
        customerDto.setFirstName("Maria");
        customerDto.setLastName("Cruz");
        customer = customerService.addCustomer(customerDto);

        customer.setInvoices(new ArrayList<>());

        customer.getInvoices().add(invoice1);
        customer.getInvoices().add(invoice2);
        customer.getInvoices().add(invoice3);




        LocalDate invoiceDate = invoiceDto1.getInvoicedate();

        bookingDto = new BookingDto();
        customerDto = new CustomerDto();


        //customer.getBookingList().add(booking);
        customerService.updateCustomer(customer.getId(), customerDto);

    }

    @Test
    void getAllInvoice() throws Exception {
        mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(invoice1.getId().toString()))
                .andExpect(jsonPath("$[0].amount").value(90))
                .andExpect(jsonPath("$[0].invoicedate").value("2023-10-31"))
               // .andExpect(jsonPath("$[0].booking.id").value(null))
                //.andExpect(jsonPath("$[0].customer").value(null))
                .andExpect(jsonPath("$[1].id").value(invoice2.getId().toString()))
                .andExpect(jsonPath("$[1].amount").value(100))
                .andExpect(jsonPath("$[1].invoicedate").value("2023-11-15"));
               // .andExpect(jsonPath("$[0].booking.id").value(null))
               // .andExpect(jsonPath("$[0].customer").value(null));
    }

    @Test
    void getInvoice() throws Exception {
        mockMvc.perform(get("/invoices/" + invoice2.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(invoice2.getId().toString()))
                .andExpect(jsonPath("amount").value(100))
                .andExpect(jsonPath("invoicedate").value("2023-11-15"));

    }

    @Test
    void addInvoice() throws Exception{
        mockMvc.perform(post("/invoices/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invoiceDto3)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(3))
                .andExpect(jsonPath("amount").value(75))
                .andExpect(jsonPath("invoicedate").value("2023-11-01"));
    }

    @Test
    void addInvoiceWithFieldErrors() throws Exception{
        mockMvc.perform(post("/invoices/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidInvoiceDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteInvoice() throws Exception {
        mockMvc.perform(delete("/invoices/" + invoice1.getId().toString()))
                .andExpect(status().isNoContent());
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

    @Test
    void updateInvoice() throws Exception {
        mockMvc.perform(put("/invoices/" + invoice1.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invoiceDto3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(invoice1.getId().toString()))
                .andExpect(jsonPath("amount").value(75))
                .andExpect(jsonPath("invoicedate").value("2023-11-01"));
    }


    @Test
    void getInvoicesForBooking() {
    }

    @Test
    void getInvoiceForCustomer() {
    }
}