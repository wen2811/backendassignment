package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Invoice;
import com.wendy.backendassignment.repositories.BookingRepository;
import com.wendy.backendassignment.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final BookingRepository bookingRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, BookingRepository bookingRepository) {
        this.invoiceRepository = invoiceRepository;
        this.bookingRepository = bookingRepository;
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

    //Create
    public InvoiceDto addInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = transferDtoToInvoice(invoiceDto);
        invoiceRepository.save(invoice);

        return transferInvoiceToDto(invoice);
    }

    public void updateInvoice(Long id, InvoiceDto invoiceDto) {
        if (!invoiceRepository.existsById(id)) {
            throw new RecordNotFoundException("There's no Invoice found");
        }
        Invoice updatedInvoice = invoiceRepository.findById(id).orElse(null);
        updatedInvoice.setId(invoiceDto.getId());
        updatedInvoice.setAmount(invoiceDto.getAmount());
        updatedInvoice.setInvoicedate(invoiceDto.getInvoicedate());
        invoiceRepository.save(updatedInvoice);
    }

    //Delete
    public void deleteInvoice(Long id) {invoiceRepository.deleteById(id);
    }

    public List<InvoiceDto> getInvoicesForBooking(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Booking booking = bookingOptional.get();
        List<Invoice> invoices = invoiceRepository.findByBookingId(bookingId);
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (Invoice invoice : invoices) {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setId(invoice.getId());
            invoiceDto.setAmount(invoice.getAmount());

            invoiceDtos.add(invoiceDto);
        }
        return invoiceDtos;
    }



    public InvoiceDto transferInvoiceToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.id = invoice.getId();
        invoiceDto.amount = invoice.getAmount();
        invoiceDto.invoicedate = invoice.getInvoicedate();
        invoiceDto.booking = invoice.getBooking();
        return invoiceDto;
    }

    public Invoice transferDtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();

        invoice.setId(invoiceDto.id);
        invoice.setAmount(invoiceDto.amount);
        invoice.setInvoicedate(invoiceDto.invoicedate);
        invoice.setBooking(invoiceDto.booking);
        return invoice;
    }
}
