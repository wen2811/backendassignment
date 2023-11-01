package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
