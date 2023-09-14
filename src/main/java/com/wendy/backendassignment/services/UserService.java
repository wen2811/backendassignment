package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired //constructor
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

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }
        return dto;
    }
    //voor om te testen als mn create controller werkt
    public Long createUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.firstName);
        user.setLastName(userDto.lastName);
        user.setUserName(userDto.userName);

        userRepository.save(user);

        return user.getId();
    }
}
