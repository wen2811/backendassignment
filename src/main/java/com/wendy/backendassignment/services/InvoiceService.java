package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.InvoiceDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Customer;
import com.wendy.backendassignment.models.Invoice;
import com.wendy.backendassignment.repositories.BookingRepository;
import com.wendy.backendassignment.repositories.CustomerRepository;
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
    private final CustomerRepository customerRepository;


    public InvoiceService(InvoiceRepository invoiceRepository, BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
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
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("There's no Invoice found with id " + id));

        invoiceDto.setId(existingInvoice.getId());

        existingInvoice.setAmount(invoiceDto.getAmount());
        existingInvoice.setInvoicedate(invoiceDto.getInvoicedate());
        Booking booking = bookingRepository.findById(invoiceDto.getBookingId())
                .orElseThrow(() -> new RecordNotFoundException("There's no Booking found with id " + invoiceDto.getBookingId()));
        existingInvoice.setBooking(booking);

        Customer customer = customerRepository.findById(invoiceDto.getCustomerId())
                .orElseThrow(() -> new RecordNotFoundException("There's no Customer found with id " + invoiceDto.getCustomerId()));
        existingInvoice.setCustomer(customer);

        invoiceRepository.save(existingInvoice);
    }


    //Delete
    public void deleteInvoice(Long id) {invoiceRepository.deleteById(id);
    }

    //Relations methods

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

    public List<InvoiceDto> getInvoiceForCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Customer customer = customerOptional.get();
        List<Invoice> invoices = customer.getInvoices();
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
        invoiceDto.bookingId= invoice.getBooking().getId();
        invoiceDto.customerId = invoice.getCustomer().getId();
        invoiceDto.customer = invoice.getCustomer().getEmail();
        return invoiceDto;
    }

    public Invoice transferDtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();

        invoice.setId(invoiceDto.id);
        invoice.setAmount(invoiceDto.amount);
        invoice.setInvoicedate(invoiceDto.invoicedate);
        Booking booking = bookingRepository.findById(invoiceDto.bookingId)
                .orElseThrow(() -> new RecordNotFoundException("There's no Booking found with id " + invoiceDto.bookingId));
        invoice.setBooking(booking);

        Customer customer = customerRepository.findById(invoiceDto.customerId)
                .orElseThrow(() -> new RecordNotFoundException("There's no Customer found with id " + invoiceDto.customerId));
        invoice.setCustomer(customer);
        return invoice;
    }
}
