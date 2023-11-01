package com.wendy.backendassignment.repositories;

import com.wendy.backendassignment.models.Authority;
import com.wendy.backendassignment.models.User;
import com.wendy.backendassignment.models.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findById_ReturnsTrue() {
        //Arrange
        User user = User.builder().username("wendy@gmail.com").build();

        Authority authority = new Authority(user.getUsername(), UserRole.ADMIN.toString());
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);

        userRepository.save(user);
        //Act
        boolean userResponse = userRepository.findById(user.getUsername()).isPresent();

        //Assert
        Assertions.assertTrue(userResponse);
    }

    @Test
    void findById_ReturnsFalse() {
        //Arrange
        User user = User.builder().username("wendy@gmail.com").build();
        User user2 = User.builder().username("jimmy@gmail.com").build();


        Authority authority = new Authority(user.getUsername(), UserRole.ADMIN.toString());
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);

        userRepository.save(user);
        //Act
        boolean userResponse = userRepository.findById(user2.getUsername()).isPresent();

        //Assert
        Assertions.assertFalse(userResponse);
    }

    @Test
    void findByEmail_ReturnsNotNull() {
        //Arrange
        User user = User.builder().email("wendy@gmail.com")
                .username("wendy@gmail.com").build();

        Authority authority = new Authority(user.getUsername(), UserRole.ADMIN.toString());
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        user.setAuthorities(authorities);
        //Act
        userRepository.save(user);

        User userResponse = userRepository.findByEmail(user.getEmail());

        //Assert
        Assertions.assertNotNull(userResponse);
    }
}