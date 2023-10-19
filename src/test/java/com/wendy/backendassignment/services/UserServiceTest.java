package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.models.UserRole;
import com.wendy.backendassignment.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userServiceImpl;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    User user1;
    User user2;
    User user3;
    UserDto userDto1;
    UserDto userDto2;
    UserDto userDto3;


    @BeforeEach
    void setUp() {
        user1 = new User(1L, "user1", "Jeanine", "Jean", LocalDate.of(2000,2,2), "jeanine@mail.com","password", true, "testapikey", UserRole.ADMIN, null, null);
        user2 = new User(2L, "user2", "Jimmy", "Jim", LocalDate.of(1995,7,7), "jimmy@mail.com","password", true, "testapikey", UserRole.EMPLOYEE, null, null);
        user3 = new User(3L, "user3", "Mary", "Ann", LocalDate.of(2002,4,4), "mary@mail.com","user", true, "testapikey", UserRole.CUSTOMER, null, null);

        UserDto userDto1 = new UserDto();
        userDto1.setUsername("user1");
        userDto1.setFirstname("Jeanine");
        userDto1.setLastname("Jean");
        userDto1.setDob(LocalDate.of(2000, 2, 2));
        userDto1.setEnabled(true);
        userDto1.setApikey("testapikey");
        userDto1.setEmail("jeanine@mail.com");
        userDto1.setUserRole(UserRole.ADMIN);

        UserDto userDto2 = new UserDto();
        userDto2.setUsername("user2");
        userDto2.setFirstname("Jimmy");
        userDto2.setLastname("Jim");
        userDto2.setDob(LocalDate.of(1995, 7, 7));
        userDto2.setEnabled(true);
        userDto2.setApikey("testapikey");
        userDto2.setEmail("jimmy@mail.com");
        userDto2.setUserRole(UserRole.EMPLOYEE);

        UserDto userDto3 = new UserDto();
        userDto3.setUsername("user3");
        userDto3.setFirstname("Mary");
        userDto3.setLastname("Ann");
        userDto3.setDob(LocalDate.of(2002, 4, 4));
        userDto3.setEnabled(true);
        userDto3.setApikey("testapikey");
        userDto3.setEmail("mary@mail.com");
        userDto3.setUserRole(UserRole.CUSTOMER);

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
        //arrange
        String username = "user1";
        User existingUser = user1;

        when(userRepository.findById(username)).thenReturn(Optional.of(existingUser));

        //act
        boolean userExists = userRepository.findById(username).isPresent();

        //assert
        Assertions.assertTrue(userExists);
    }

    @Test
    void deleteUser() {
        //arrange
        String username = "user2";
        User expectedUser = user2;

        // Act
        userServiceImpl.deleteUser(username);

        // Assert
        verify(userRepository).deleteById(username);

    }

    @Test
    void getUsers() {
        //arrange
        List<User> currentList = new ArrayList<>();
        currentList.add(user1);
        currentList.add(user2);
        currentList.add(user3);

        when(userRepository.findAll()).thenReturn(currentList);
        //act
        List<UserDto> usersFound = userServiceImpl.getUsers();
        //assert
        assertEquals(user1.getUsername(), usersFound.get(0).getUsername());
        assertEquals(user2.getUsername(), usersFound.get(1).getUsername());
        assertEquals(user3.getUsername(), usersFound.get(2).getUsername());
    }

    @Test
    void updateUser() {
        //arrange
        String username = "existingUser";
        UserDto newUserDto = new UserDto();
        newUserDto.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setPassword("oldPassword");


        when(userRepository.existsById(username)).thenReturn(true);
        when(userRepository.findById(username)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //act
        userServiceImpl.updateUser(username, newUserDto);

        //assert
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(newUserDto.getPassword(), existingUser.getPassword());
    }



    @Test
    void updateUserThrowsExceptionTest(){
        UserDto userDto = new UserDto();

        assertThrows(RecordNotFoundException.class, () -> userServiceImpl.updateUser(userDto.getUsername(), userDto));
    }
}