package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String username);

    User findByEmail(String email);
}
