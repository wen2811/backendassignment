package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value = "")
    public List<User> getUsers() {
        User user = new User();
        user.setUserName("Wendy");
        user.setEmail("info@gmail.com");
        user.setLastName("Martina");

        List<User> list = new ArrayList<User>();
        list.add(user);
        return list;
    }
}
