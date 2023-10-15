package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}
