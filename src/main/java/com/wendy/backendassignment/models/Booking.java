package com.wendy.backendassignment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @OneToOne(mappedBy = "booking")
    private Invoice invoice;

   @OneToMany(mappedBy = "booking")
   private List<BookingTreatment> bookingTreatments;

    @ManyToOne
    private Customer customer;


    //@ManyToOne
   // private User user;

    public Booking(LocalDate date, double totalAmount ){
        this.date = date;
        this.totalAmount = totalAmount;
    }
}
