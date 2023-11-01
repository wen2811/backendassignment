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

    @OneToOne(mappedBy = "booking", orphanRemoval = true)
    @JsonIgnoreProperties("booking")
    private Invoice invoice;

    @OneToMany(mappedBy = "booking", orphanRemoval = true)
    @JsonIgnoreProperties("booking")
    private List<Treatment> treatments;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Booking(LocalDate date, Customer customer, List<Treatment> treatments ){
        this.date = date;
        this.customer = customer;
        this.treatments = treatments;
    }

    public List<Treatment> getTreatments() {
        return treatments != null ? treatments : Collections.emptyList();
    }


    public List<Long> getTreatmentIds() {
        if (treatments == null) {
            return Collections.emptyList();
        }
        return treatments.stream()
                .map(Treatment::getId)
                .collect(Collectors.toList());
    }




    public Long getCustomerId() {
        return (customer != null) ? customer.getId() : null;
    }

    public Long getInvoiceId() {
        return (invoice != null) ? invoice.getId() : null;
    }


    public void setTreatments(List<Long> treatmentIds) {
    }
}
