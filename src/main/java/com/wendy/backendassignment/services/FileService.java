package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.FileDto;
import com.wendy.backendassignment.exception.ResourceNotFoundException;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.models.File;
import com.wendy.backendassignment.repositories.CustomerRepository;
import com.wendy.backendassignment.repositories.FileRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final CustomerRepository customerRepository;

    public FileService(FileRepository fileRepository, CustomerRepository customerRepository) {
        this.fileRepository = fileRepository;
        this.customerRepository = customerRepository;
    }
    //upload
    public FileDto uploadFile(MultipartFile file, Customer customer) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file is empty.");
        }

        File uploadedFile = new File();
        uploadedFile.setFilename(file.getOriginalFilename());
        uploadedFile.setFiletype(file.getContentType());
        uploadedFile.setData(file.getBytes());
        uploadedFile.setDate(LocalDate.now());
        uploadedFile.setCustomer(customer);

        File savedFile = fileRepository.save(uploadedFile);

        return transferToFileDto(savedFile);
    }

    public FileDto storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file is empty.");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File dbFile = new File();
        dbFile.setFilename(fileName);
        dbFile.setFiletype(file.getContentType());
        dbFile.setData(file.getBytes());
        dbFile.setDate(LocalDate.now());

        File savedFile = fileRepository.save(dbFile);

        return transferToFileDto(savedFile);
    }

    public FileDto getFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId));

        return transferToFileDto(file);
    }

    public FileDto assignFileToCustomer(Long fileId, Long customerId) {
        File file = getFileIfExists(fileId);
        Customer customer = getCustomerIfExists(customerId);

        file.setCustomer(customer);
        File savedFile = fileRepository.save(file);

        return transferToFileDto(savedFile);
    }

    private Customer getCustomerIfExists(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }

    private File getFileIfExists(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + fileId));
    }

    public ResponseEntity<Resource> downloadFile(Long fileId) {
        FileDto fileDto = getFile(fileId);

        ByteArrayResource resource = new ByteArrayResource(fileDto.getData());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileDto.getFiletype()));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileDto.getFilename()).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    public void deleteFile(Long id) {fileRepository.deleteById(id); }

    public FileDto transferToFileDto(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setFilename(file.getFilename());
        fileDto.setFiletype(file.getFiletype());
        fileDto.setDate(file.getDate());
        fileDto.setData(file.getData());
        fileDto.setMimeType(file.getMimeType());
        fileDto.setDescription(file.getDescription());
        fileDto.setCustomer(file.getCustomer());

        return fileDto;
    }
}
