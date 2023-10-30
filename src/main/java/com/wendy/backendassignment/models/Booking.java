package com.wendy.backendassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonIgnoreProperties("booking")
    private Invoice invoice;

   @OneToMany(mappedBy = "booking")
   @JsonIgnoreProperties("booking")
   private List<BookingTreatment> bookingTreatments;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Booking(LocalDate date, double totalAmount, Customer customer ){
        this.date = date;
        this.totalAmount = totalAmount;
        this.customer = customer;
    }

    public List<Long> getBookingTreatmentIds() {
        if (bookingTreatments == null) {
            return Collections.emptyList();
        }
        return bookingTreatments.stream()
                .map(BookingTreatment::getId)
                .collect(Collectors.toList());
    }

    public Long getCustomerId() {
        return (customer != null) ? customer.getId() : null;
    }

    public Long getInvoiceId() {
        return (invoice != null) ? invoice.getId() : null;
    }




}
