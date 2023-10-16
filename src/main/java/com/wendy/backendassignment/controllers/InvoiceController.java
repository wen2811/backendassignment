package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    //Read
    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoice() {
        return ResponseEntity.ok().body(invoiceService.getAllInvoice());
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoiceDto> getInvoice(@PathVariable Long id) throws RecordNotFoundException {
        InvoiceDto invoiceDto = invoiceService.getInvoice(id);
        return ResponseEntity.ok().body(invoiceDto);
    }



}
