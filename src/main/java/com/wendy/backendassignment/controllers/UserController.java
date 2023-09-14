package com.wendy.backendassignment.controllers;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }
//Path variable gebruike je wanneer je maar een variabele kunt ontvangen - om een pad op te zoeken.
    //netter om bij een identificatie een variable een path variable te gebruiken.
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUser(username);


        return ResponseEntity.ok().body(optionalUser);

    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
            }
            Long newId = userService.createUser(userDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newId).toUriString());
            userDto.id = newId;

            return ResponseEntity.created(uri).body(newId);
        }



}
