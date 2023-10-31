package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.FileDto;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.services.CustomerService;
import com.wendy.backendassignment.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(value = "/files")
public class FileController {
    private final FileService fileService;
    private final CustomerService customerService;

    public FileController(FileService fileService, CustomerService customerService) {
        this.fileService = fileService;
        this.customerService = customerService;
    }

    @PostMapping(value="/upload")
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("customerId") Long customerId) throws IOException {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        FileDto uploadedFile = fileService.uploadFile(file, customer);
        return ResponseEntity.ok().body(uploadedFile);
    }

    @PostMapping(value="/store")
    public ResponseEntity<FileDto> storeFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileDto uploadedFile = fileService.storeFile(file);

        return ResponseEntity.ok(uploadedFile);
    }

    @GetMapping(path = "/get/{fileId}")
    public ResponseEntity<FileDto> getFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.getFile(fileId);
        return ResponseEntity.ok(fileDto);
    }

    @PostMapping(path = "/assign/{fileId}/to-customer/{customerId}")
    public ResponseEntity<FileDto> assignFileToCustomer(@PathVariable Long fileId, @PathVariable Long customerId) {
        FileDto assignedFile = fileService.assignFileToCustomer(fileId, customerId);
        return ResponseEntity.ok(assignedFile);
    }

    @GetMapping(path = "/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.downloadFile(fileId);

        ByteArrayResource resource = new ByteArrayResource(fileDto.getData());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileDto.getFiletype()));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileDto.getFilename()).build());

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }


}
