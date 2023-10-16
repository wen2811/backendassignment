package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Invoice;
import com.wendy.backendassignment.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    //Read
    public List<InvoiceDto> getAllInvoice() {
        Iterable<Invoice> invoice = invoiceRepository.findAll();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (Invoice inv: invoice) {
            invoiceDtos.add(transferInvoiceToDto(inv));
        }

        return invoiceDtos;
    }

    public InvoiceDto getInvoice(Long id) throws RecordNotFoundException {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);

        if(invoiceOptional.isEmpty()) {
            throw new RecordNotFoundException("There's no invoice found with this id " + id);
        }
        Invoice invoice = invoiceOptional.get();
        return transferInvoiceToDto(invoice);
    }




    public InvoiceDto transferInvoiceToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.id = invoice.getId();
        invoiceDto.amount = invoice.getAmount();
        invoiceDto.invoicedate = invoice.getInvoicedate();
        return invoiceDto;
    }

    public Invoice transferDtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();

        invoice.setId(invoiceDto.id);
        invoice.setAmount(invoiceDto.amount);
        invoice.setInvoicedate(invoiceDto.invoicedate);
        return invoice;
    }
}
