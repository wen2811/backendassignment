package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.BookingTreatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingTreatmentRepository extends JpaRepository<BookingTreatment, Long> {
}
