package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Treatment;
import com.wendy.backendassignment.models.TreatmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    List<Treatment> findTreatmentsByType(TreatmentType type);
}
