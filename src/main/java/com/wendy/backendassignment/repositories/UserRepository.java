package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

public interface UserRepository extends CrudRepository<User, String> {
}
