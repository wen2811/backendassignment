package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
