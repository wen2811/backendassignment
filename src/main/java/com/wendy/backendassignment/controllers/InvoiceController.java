package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    //Read
    @GetMapping(value = "")
    public ResponseEntity<List<InvoiceDto>> getAllInvoice() {
        return ResponseEntity.ok().body(invoiceService.getAllInvoice());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDto> getInvoice(@PathVariable Long id) throws RecordNotFoundException {
        InvoiceDto invoiceDto = invoiceService.getInvoice(id);
        return ResponseEntity.ok().body(invoiceDto);
    }

    //Create
    @PostMapping(value = "/new")
    public ResponseEntity<Object> addInvoice(@Valid @RequestBody InvoiceDto invoiceDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            InvoiceDto addedInvoice = invoiceService.addInvoice(invoiceDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedInvoice.id)));
            return ResponseEntity.created(uri).body(addedInvoice);
        }

    }

    //Update
    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> updateInvoice(@PathVariable("id") Long id, @RequestBody InvoiceDto invoiceDto) {
       try{
           invoiceService.updateInvoice(id, invoiceDto);
           return ResponseEntity.ok(invoiceDto);
       }catch (RecordNotFoundException e){
           return ResponseEntity.badRequest().body("Invoice not Found");
       }
    }


    //Delete
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object>deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    //Relationship methods
    @GetMapping(path = "/booking/{bookingId}")
    public ResponseEntity<List<InvoiceDto>> getInvoicesForBooking(@PathVariable Long bookingId) {
        List<InvoiceDto> invoiceDtos = invoiceService.getInvoicesForBooking(bookingId);
        return ResponseEntity.ok(invoiceDtos);
    }

    @GetMapping(path = "/customer/{customerId}")
    public ResponseEntity<List<InvoiceDto>> getInvoiceForCustomer(@PathVariable Long customerId) {
        List<InvoiceDto> invoiceDtos = invoiceService.getInvoiceForCustomer(customerId);
        return ResponseEntity.ok(invoiceDtos);
    }

}
