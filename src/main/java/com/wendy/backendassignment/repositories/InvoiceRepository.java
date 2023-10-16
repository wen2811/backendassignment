package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
