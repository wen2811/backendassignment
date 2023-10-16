package com.wendy.backendassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Booking> bookingList;

    public boolean isPasswordValid(String password) {
        return false;
    }

    public void changePassword(String newPassword) {

    }

    public void setPassword(String password) {
    }
}
