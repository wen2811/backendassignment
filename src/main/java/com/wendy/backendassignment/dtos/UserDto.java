package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Authority;
import com.wendy.backendassignment.models.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    public String username;

    public String password;

    public String firstname;
    public String lastname;
    public LocalDate dob;
    public Boolean enabled;
    public String apikey;

    public String email;

    public Set<Authority> authorities;
    private UserRole userRole;
}
