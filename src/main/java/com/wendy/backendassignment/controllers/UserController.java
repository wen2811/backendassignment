package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


   /* @GetMapping(value = "")
    public List<User> getUsers() {
        User user = new User();
        user.setUserName("Wendy");
        user.setEmail("info@gmail.com");
        user.setLastName("Martina");

        List<User> list = new ArrayList<User>();
        list.add(user);
        return list;
    }*/

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

}
