package com.wendy.backendassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoice;

    @OneToMany(mappedBy = "customer")
    private List<File> fileList;

   /* public boolean isPasswordValid(String password) {
        return false;
    }

    public void changePassword(String newPassword) {

    }

    public void setPassword(String password) {
    }*/

    public List<Invoice> getInvoice() {
        return invoice;
    }

    public void setUser(User newUser) {
    }
}
