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
public abstract class Customer {
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

    public abstract boolean isPasswordValid(String password);

    public abstract void changePassword(String newPassword);

    public void setPassword(String password) {
    }
}
