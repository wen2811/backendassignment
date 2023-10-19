package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userServiceImpl;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUser_Returns_UserDto() {
        //Arrange
        // wat je verwacht
        UserDto existingUserDto = UserDto.builder().username("jimmy").enabled(true).build();

        // wat je service gaat uitvoeren
        Optional<User> user = Optional.ofNullable(User.builder()
                .username(existingUserDto.getUsername()).build());
        when(userRepository.findById(Mockito.any(String.class))).thenReturn(user);

        //Act
        UserDto userDto = userServiceImpl.getUser(existingUserDto.getUsername());
        //Arrange
        // kijk je als de username bestaat
        Assertions.assertEquals(existingUserDto.getUsername(), userDto.getUsername());
    }

    @Test
    void createUser() {
        //Arrange
        //dit voer je in
        UserDto userDto = UserDto.builder().username("jimmy").enabled(true).build();
        // wat je verwacht
        User user = User.builder().username("jimmy").build();

        // je moet dit moet om die user terug te krijgen
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Act
        String username = userServiceImpl.createUser(userDto);

        //Assert
        Assertions.assertEquals(username, user.getUsername());
    }

    @Test
    void userExists() {
    }

    @Test
    void deleteUser() {
    }
}