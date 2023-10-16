package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.CustomerDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Read
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomer() {
        return ResponseEntity.ok().body(customerService.getAllCustomer());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> getACustomerById(@PathVariable Long id) throws RecordNotFoundException {
        CustomerDto customerDto = customerService.getACustomerById(id);
        return ResponseEntity.ok().body(customerDto);
    }

    //Create
    @PostMapping("/customers")
    public ResponseEntity<Object> addCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult br) {
        if(br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            CustomerDto addedCustomer = customerService.addCustomer(customerDto);
            URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedCustomer.id)));
            return ResponseEntity.created(uri).body(addedCustomer);
        }
    }

    //Update
    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(customerDto);
    }


    //Delete
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) throws RecordNotFoundException  {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
