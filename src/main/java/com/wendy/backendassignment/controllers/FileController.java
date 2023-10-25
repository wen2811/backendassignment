package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.FileDto;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.services.CustomerService;
import com.wendy.backendassignment.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;
    private final CustomerService customerService;

    public FileController(FileService fileService, CustomerService customerService) {
        this.fileService = fileService;
        this.customerService = customerService;
    }

    @PostMapping(value="/upload")
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("customerId") Long customerId) throws IOException {
        Customer customer = (Customer) customerService.getCustomerById(customerId);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        FileDto uploadedFile = fileService.uploadFile(file, customer);
        return ResponseEntity.ok(uploadedFile);
    }

    @PostMapping(value="/store")
    public ResponseEntity<FileDto> storeFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileDto uploadedFile = fileService.storeFile(file);

        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping("/get/{fileId}")
    public ResponseEntity<FileDto> getFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.getFile(fileId);
        return ResponseEntity.ok(fileDto);
    }

    @PostMapping("/assign/{fileId}/to-customer/{customerId}")
    public ResponseEntity<FileDto> assignFileToCustomer(@PathVariable Long fileId, @PathVariable Long customerId) {
        FileDto assignedFile = fileService.assignFileToCustomer(fileId, customerId);
        return ResponseEntity.ok(assignedFile);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        ResponseEntity<Resource> fileResponse = fileService.downloadFile(fileId);
        return fileResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }


}
