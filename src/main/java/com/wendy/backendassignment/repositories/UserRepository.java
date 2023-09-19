package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
}
