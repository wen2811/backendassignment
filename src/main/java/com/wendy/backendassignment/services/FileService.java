package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.FileDto;
import com.wendy.backendassignment.exception.ResourceNotFoundException;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.models.File;
import com.wendy.backendassignment.repositories.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
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

    public FileDto getFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId));

        return transferToFileDto(file);
    }

    public FileDto downloadFile(Long fileId) {
         return getFile(fileId);
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
        fileDto.setCustomerId(file.getCustomer().getId());

        return fileDto;
    }
}
