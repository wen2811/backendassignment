package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUser(username);


        return ResponseEntity.ok().body(optionalUser);

    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody UserDto userDto) {
        Long newId = userService.createUser(userDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newId).toUriString());
        return ResponseEntity.created(uri).body(newId);
    }



}
