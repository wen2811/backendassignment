package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Authority;
import com.wendy.backendassignment.models.Booking;
import com.wendy.backendassignment.models.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
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
    private LocalDate dob;
    public Boolean enabled;
    public String apikey;
    public String email;
    public Set<Authority> authorities;
    public List<Booking> bookingList;

    private UserRole userRole;


}
