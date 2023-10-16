package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
