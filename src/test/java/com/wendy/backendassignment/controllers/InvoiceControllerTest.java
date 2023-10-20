package com.wendy.backendassignment.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllInvoice() {
    }

    @Test
    void getInvoice() {
    }

    @Test
    void addInvoice() {
    }

    @Test
    void updateInvoice() {
    }

    @Test
    void deleteInvoice() {
    }

    @Test
    void getInvoicesForBooking() {
    }

    @Test
    void getInvoiceForCustomer() {
    }
}