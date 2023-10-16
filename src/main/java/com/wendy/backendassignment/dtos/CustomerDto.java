package com.wendy.backendassignment.dtos;

import com.wendy.backendassignment.models.Booking;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDto {
    public Long id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public List<Booking> bookingList;
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
