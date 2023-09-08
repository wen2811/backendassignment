package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = (List<User>) userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.userName = user.getUserName();
        dto.password = user.getPassword();
       // dto.enabled = user.isEnabled();
        //dto.apikey = user.getApikey();
        dto.email = user.getEmail();
       // dto.authorities = user.getAuthorities();

        return dto;
    }
}
