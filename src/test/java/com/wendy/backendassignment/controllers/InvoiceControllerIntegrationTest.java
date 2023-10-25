package com.wendy.backendassignment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wendy.backendassignment.dtos.BookingDto;
import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.models.Invoice;
import com.wendy.backendassignment.services.CustomerService;
import com.wendy.backendassignment.services.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private InvoiceService invoiceService;
    @Autowired
    private CustomerService customerService;

    Invoice invoice1;
    Invoice invoice2;
    Invoice invoice3;
    InvoiceDto invoiceDto1;
    InvoiceDto invoiceDto2;
    InvoiceDto invoiceDto3;
    InvoiceDto invalidInvoiceDto;
    CustomerDto customer;
    BookingDto bookingDto;
    CustomerDto customerDto;
    List<Invoice> invoices = new ArrayList<>();
    List<InvoiceDto> invoiceDtos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        invoice1 = new Invoice(1L, 90, (LocalDate.of(2023, 10, 31)),null,null);
        invoice2 = new Invoice(2L, 100, (LocalDate.of(2023, 11, 15)),null,null);
        invoice3 = new Invoice(3L, 75, (LocalDate.of(2023, 11, 1)),null,null);

        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        invoiceDto1 = new InvoiceDto(invoice1.getId(), 90, LocalDate.of(2023,10,31), null, null);
        invoiceDto2 = new InvoiceDto(invoice2.getId(), 100, LocalDate.of(2023,11,15), null, null);
        invoiceDto3 = new InvoiceDto(3L, 75, LocalDate.of(2023,11,1), null, null);

        invoiceDtos.add(invoiceDto1);
        invoiceDtos.add(invoiceDto2);
        invoiceDtos.add(invoiceDto3);


        invalidInvoiceDto = new InvoiceDto();
        invalidInvoiceDto.setAmount(-50);
        invalidInvoiceDto.setInvoicedate(LocalDate.of(2021, 11, 1));

// ??????
        customerDto = new CustomerDto();
        customerDto.setFirstName("Maria");
        customerDto.setLastName("Cruz");
        customerDto.setEmail("maria@gmail.com");

        invoiceDto2.customer = Customer.builder().firstName(customerDto.getFirstName())
                .email(customerDto.getEmail()).build();

        invoiceDto2.booking = Booking.builder().id(1L).build();

        customer = customerService.addCustomer(customerDto);

        customer.setInvoices(new ArrayList<>());

        customer.getInvoices().add(invoice1);
        customer.getInvoices().add(invoice2);
        customer.getInvoices().add(invoice3);


        LocalDate invoiceDate = invoiceDto1.getInvoicedate();

        bookingDto = new BookingDto();


        //customer.getBookingList().add(booking);
        customerService.updateCustomer(customer.getId(), customerDto);

    }

    @Test
    void getAllInvoice() throws Exception {

        when(invoiceService.getAllInvoice()).thenReturn(invoiceDtos);

        ResultActions result = mockMvc.perform(get("/invoices")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(invoice1.getId().toString()))
                .andExpect(jsonPath("$[0].amount").value(90))
                .andExpect(jsonPath("$[0].invoicedate").value("2023-10-31"))

                .andExpect(jsonPath("$[1].id").value(invoice2.getId().toString()))
                .andExpect(jsonPath("$[1].amount").value(100))
                .andExpect(jsonPath("$[1].invoicedate").value("2023-11-15"))
                .andExpect(jsonPath("$[1].booking.id").value(1L))
                .andExpect(jsonPath("$[1].customer.firstName").value(customerDto.firstName));
               // .andExpect(jsonPath("$[0].booking.id").value(null))
               // .andExpect(jsonPath("$[0].customer").value(null));
    }

    @Test
    void getInvoice() throws Exception {

        Long id = invoice2.getId();
        when(invoiceService.getInvoice(id)).thenReturn(invoiceDto2);

       ResultActions result =  mockMvc.perform(
               get("/invoices/" + id.toString())
               .contentType(MediaType.APPLICATION_JSON)
       );

       result.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(invoice2.getId().toString()))
                .andExpect(jsonPath("amount").value(100))
                .andExpect(jsonPath("invoicedate").value("2023-11-15"));

    }

    @Test
    void addInvoice() throws Exception{
        InvoiceDto invoiceDto = new InvoiceDto(3L, 75, (LocalDate.of(2023, 11, 1)),null,null);

        when(invoiceService.addInvoice(any(InvoiceDto.class))).thenReturn(invoiceDto);

       ResultActions result = mockMvc.perform(post("/invoices/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invoiceDto)));
                result.andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(3))
                .andExpect(jsonPath("amount").value(75))
                .andExpect(jsonPath("invoicedate").value("2023-11-01"));
    }

    @Test
    void addInvoiceWithFieldErrors() throws Exception{

        when(invoiceService.addInvoice(any(InvoiceDto.class))).thenReturn(invalidInvoiceDto);

       ResultActions result = mockMvc.perform(post("/invoices/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidInvoiceDto)));
                result.andExpect(status().isBadRequest());

    }

    @Test
    void deleteInvoice() throws Exception {
        Long id = 1L;

        doNothing().when(invoiceService).deleteInvoice(id);

        ResultActions result = mockMvc.perform(delete("/invoices/" + id.toString()));
                result.andExpect(status().isNoContent());
    }

    @Test
    void updateInvoice() throws Exception {
        Long id = invoice2.getId();
        InvoiceDto updatedinvoiceDto = new InvoiceDto(id, 75, (LocalDate.of(2023, 11, 1)),null,null);

        doNothing().when(invoiceService).updateInvoice(id, updatedinvoiceDto);

        ResultActions result = mockMvc.perform(put("/invoices/" + id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedinvoiceDto)));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(invoiceDto2.getId().toString()))
                .andExpect(jsonPath("amount").value(75))
                .andExpect(jsonPath("invoicedate").value("2023-11-01"));
    }


    @Test
    void updateInvoice_throws_RecordNotFound_Exception() throws Exception {
        Long id = 2L;
        InvoiceDto updatedinvoiceDto = InvoiceDto.builder()
                .amount(75).invoicedate(LocalDate.of(2023, 11, 1)).build();

        doThrow(RecordNotFoundException.class).when(invoiceService).updateInvoice(id, updatedinvoiceDto);

        ResultActions result = mockMvc.perform(put("/invoices/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }


    @Test
    void getInvoicesForBooking() {
    }

    @Test
    void getInvoiceForCustomer() {
    }
}