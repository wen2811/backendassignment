package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(Customer customer);
}
