package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String> {
}
