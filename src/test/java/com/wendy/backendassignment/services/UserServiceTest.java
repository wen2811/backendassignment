package com.wendy.backendassignment.services;

import com.wendy.backendassignment.dtos.UserDto;
import com.wendy.backendassignment.exception.RecordNotFoundException;
import com.wendy.backendassignment.exception.UserNameNotFoundException;
import com.wendy.backendassignment.models.Authority;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.models.UserRole;
import com.wendy.backendassignment.repositories.BookingRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    BookingRepository bookingRepository;

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

        userDto1 = new UserDto();
        userDto1.setUsername("user1");
        userDto1.setFirstname("Jeanine");
        userDto1.setLastname("Jean");
        userDto1.setDob(LocalDate.of(2000, 2, 2));
        userDto1.setEnabled(true);
        userDto1.setApikey("testapikey");
        userDto1.setEmail("jeanine@mail.com");
        userDto1.setUserRole(UserRole.ADMIN);

        userDto2 = new UserDto();
        userDto2.setUsername("user2");
        userDto2.setFirstname("Jimmy");
        userDto2.setLastname("Jim");
        userDto2.setDob(LocalDate.of(1995, 7, 7));
        userDto2.setEnabled(true);
        userDto2.setApikey("testapikey");
        userDto2.setEmail("jimmy@mail.com");
        userDto2.setUserRole(UserRole.EMPLOYEE);

        userDto3 = new UserDto();
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
        UserDto existingUserDto = UserDto.builder().username("jimmy").enabled(true).build();

        Optional<User> user = Optional.ofNullable(User.builder()
                .username(existingUserDto.getUsername()).build());
        when(userRepository.findById(Mockito.any(String.class))).thenReturn(user);

        //Act
        UserDto userDto = userServiceImpl.getUser(existingUserDto.getUsername());
        //Arrange
        Assertions.assertEquals(existingUserDto.getUsername(), userDto.getUsername());
    }

    @Test
    void getUser_UserNameNotFound(){
        //arrange
        String username = "gebruikersnaam_die_niet_bestaat";

        //act
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        //assert
        assertThrows(UserNameNotFoundException.class, () -> userServiceImpl.getUser(username));
    }






    @Test
    void createUser() {
        //Arrange
        UserDto userDto = UserDto.builder().username("jimmy").enabled(true).build();
        User user = User.builder().username("jimmy").build();

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
        assertTrue(userExists);
    }

    @Test
    void userExists_UserDoesNotExist() {
        //arrange
        String username = "nonExistentUser";
        when(userRepository.existsById(username)).thenReturn(false);

        //act
        boolean userExists = userServiceImpl.userExists(username);

        //assert
        assertFalse(userExists);
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

    @Test
    void getAuthorities_UserExists() {
        //arrange
        String username = "testUser";
        Set<Authority> authorities = new HashSet<>(Collections.singletonList(new Authority("ROLE_USER")));
        User user = User.builder()
                .username(username)
                .authorities(authorities)
                .build();

        when(userRepository.existsById(username)).thenReturn(true);
        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        //act
       Set<Authority> result = userServiceImpl.getAuthorities(username);

        //assert
        verify(userRepository, times(1)).existsById(username);
        verify(userRepository, times(1)).findById(username);
        assertEquals(authorities, result);

    }

    @Test
    void getAuthorities_UserNotExists() {
        //arrange
        String username = "noUser";
        //act
        when(userRepository.existsById(username)).thenReturn(false);
        //assert
        assertThrows(RecordNotFoundException.class, ()-> userServiceImpl.getAuthorities(username));

        verify(userRepository, times(1)).existsById(username);
        verify(userRepository, never()).findById(username);
    }

    @Test
    void addAuthority() {
        //arrange
        String username = "testUser";
        String authority = "ROLE_USER";

        User user = new User();
        user.setUsername(username);

        when(userRepository.existsById(username)).thenReturn(true);
        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        //act
        userServiceImpl.addAuthority(username, authority);

        //assert
        verify(userRepository, times(1)).existsById(username);
        verify(userRepository, times(1)).findById(username);
        verify(userRepository, times(1)).save(user);

        assertTrue(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority)));
    }

    @Test
    void AddAuthority_UserNotFound() {
        //arrange
        String username = "nonUser";
        String authority = "ROLE_USER";
        //act
        when(userRepository.existsById(username)).thenReturn(false);

        //assert
        assertThrows(RecordNotFoundException.class, () -> userServiceImpl.addAuthority(username, authority));

        verify(userRepository, times(1)).existsById(username);
        verify(userRepository, never()).findById(username);
        verify(userRepository, never()).save(any(User.class));
    }



    @Test
    void removeAuthority() {
        //arrange
        String username = "testUser";
        String authorityToRemove = "ROLE_USER";

        Authority authority1 = new Authority(username, "ROLE_USER");
        Authority authority2 = new Authority(username, "ROLE_ADMIN");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority1);
        authorities.add(authority2);

        User user = User.builder()
                .username(username)
                .authorities(authorities)
                .build();

        when(userRepository.existsById(username)).thenReturn(true);
        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        //act
        userServiceImpl.removeAuthority(username, authorityToRemove);

        //assert
        verify(userRepository, times(1)).existsById(username);
        verify(userRepository, times(1)).findById(username);
        verify(userRepository, times(1)).save(user);

        assertTrue(user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(authorityToRemove)));
    }

    @Test
    void removeAuthority_UserNotFound() {
        //arrange
        String username = "nonUser";
        String authorityToRemove = "ROLE_USER";
        //act
        when(userRepository.existsById(username)).thenReturn(false);

        //assert
        assertThrows(RecordNotFoundException.class, () -> userServiceImpl.removeAuthority(username, authorityToRemove));

        verify(userRepository, times(1)).existsById(username);
        verify(userRepository, never()).findById(username);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getCustomerById_UserFound() {
        //arrange
        Long userId = 1L;
        User expectedUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        //act
        User resultUser = userServiceImpl.getCustomerById(userId);

        //assert
        assertNotNull(resultUser);
        assertEquals(expectedUser, resultUser);
    }

    @Test
    void getCustomerById_UserNotFound() {
        //arrange
        Long userId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //act
        User resultUser = userServiceImpl.getCustomerById(userId);

        //assert
        assertNull(resultUser);
    }

    @Test
    void registerUser() {
        //arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setFirstname("John");
        userDto.setLastname("Doe");
        userDto.setDob(LocalDate.of(1995,6,12));

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        User newUser = new User();
        newUser.setUserRole(UserRole.CUSTOMER);
        newUser.setUsername("testuser");
        newUser.setEmail("test@example.com");
        newUser.setPassword("password");
        newUser.setFirstname("John");
        newUser.setLastname("Doe");
        newUser.setDob(LocalDate.of(1995,6,12));

        when(userRepository.save(newUser)).thenReturn(newUser);

        //act
        User registeredUser = userServiceImpl.registerUser(userDto);
        verify(userRepository).save(newUser);

        //assert
        assertEquals(newUser, registeredUser);
    }

    @Test
    void registerUser_existingUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("updatedUser");
        userDto.setEmail("existing@example.com");
        userDto.setPassword("newPassword");
        userDto.setFirstname("Updated");
        userDto.setLastname("User");
        userDto.setDob(LocalDate.of(1980, 5, 10));

        User existingUser = User.builder()
                .id(4L)
                .username("existingUser")
                .email("existing@example.com")
                .password("oldPassword")
                .firstname("Old")
                .lastname("User")
                .dob(LocalDate.of(1990, 3, 15))
                .build();

        when(userRepository.findByEmail("existing@example.com")).thenReturn(null);
        when(userRepository.findByEmail("existing@example.com")).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User updatedUser = userServiceImpl.registerUser(userDto);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(userDto.getPassword(), existingUser.getPassword());

        verify(userRepository).save(existingUser);

        }






}





