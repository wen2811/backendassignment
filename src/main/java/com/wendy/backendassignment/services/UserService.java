package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
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

    /*public UserDto transferUserToDto(User user){
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        // dto.enabled = user.isEnabled();
        //dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        // dto.authorities = user.getAuthorities();

        return userDto;
    }*/


    public static UserDto fromUser(User user){

        var dto = new UserDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.firstname = user.getFirstname();
        dto.lastname = user.getLastname();
        dto.setDateofbirth(user.getDateofbirth()); //vanwege private een setter
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
        user.setFirstname(userDto.firstname);
        user.setLastname(userDto.lastname);
        user.setUsername(userDto.username);

        userRepository.save(user);

        return user.getId();
    }


}
