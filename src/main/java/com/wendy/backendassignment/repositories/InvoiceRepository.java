package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByBookingId(Long bookingId);
    List<Invoice> findByCustomerId(Long customerId);
}
